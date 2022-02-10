/**
 * Copyright 2022 Joshua Zierman
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package us.zierman.joshua.phillsquill.gui;

import us.zierman.joshua.phillsquill.pref.ApplicationPreferences;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PreferencesFrame extends JFrame {

    Controller controller;
    JPanel rowsPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    List<PreferenceRow<?>> rows = new ArrayList<>();
    PreferenceRow<JTextField> outputWidthRow;
    PreferenceRow<JCheckBox> shouldAutoWrapRow;
    PreferenceRow<DirectoryInputComponent> defaultSaveDirectoryRow;
    PreferenceRow<DirectoryInputComponent> defaultOpenDirectoryRow;
    JButton cancelButton = new JButton("Cancel");
    JButton saveChangesButton = new JButton("Save Changes");

    public PreferencesFrame(Controller controller) {
        this.controller = controller;
        this.setTitle("Preferences");
        rowsPanel.setLayout(new BoxLayout(rowsPanel, BoxLayout.Y_AXIS));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        add(rowsPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.PAGE_END);
        bottomPanel.add(cancelButton);
        bottomPanel.add(saveChangesButton);
        cancelButton.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        saveChangesButton.addActionListener(e -> {

            // try to process each row
            boolean foundFailure = false;
            for (PreferenceRow<?> row : rows) {
                if (!row.saveChanges()) {
                    foundFailure = true;
                }
            }

            // Close the frame if nothing failed
            if (!foundFailure) {
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }
        });
        outputWidthRow = new PreferenceRow<JTextField>("Default Output Width: ") {

            @Override
            protected void tryToSave() {
                String text = inputComponent.getText().trim();
                int newWidth;
                try {
                    newWidth = Integer.parseInt(text);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("\"" + text + "\" cannot be used as a default output width. please enter a positive integer.");
                }
                try {
                    if (newWidth != ApplicationPreferences.getOutputWidth()) {
                        ApplicationPreferences.setOutputWidth(newWidth);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Unable to save default output width preference.", e);
                }
            }

            @Override
            protected JTextField makeComponent() {
                JTextField textField = new JTextField(String.valueOf(ApplicationPreferences.getOutputWidth()), 5);
                Document doc = textField.getDocument();
                if (doc instanceof PlainDocument) {
                    ((PlainDocument) doc).setDocumentFilter(new NonNegativeIntegerInputFilter());
                }
                return textField;
            }
        };
        rows.add(outputWidthRow);

        shouldAutoWrapRow = new PreferenceRow<JCheckBox>("Auto-Wrap: ") {
            @Override
            protected void tryToSave() {
                boolean newValue = inputComponent.isSelected();
                if (newValue != controller.getShouldAutoWrap()) {
                    ApplicationPreferences.setShouldAutoWrapKey(newValue);
                    controller.setShouldAutoWrap(newValue);
                }
            }

            @Override
            protected JCheckBox makeComponent() {
                JCheckBox checkbox = new JCheckBox();
                checkbox.setSelected(ApplicationPreferences.getShouldAutoWrapKey());
                return checkbox;
            }
        };
        rows.add(shouldAutoWrapRow);

        defaultOpenDirectoryRow = new PreferenceRow<DirectoryInputComponent>("Default Open Directory: ") {
            @Override
            protected void tryToSave() {
                Path newValue;
                try {
                    newValue = Paths.get(inputComponent.textField.getText().trim());
                } catch (InvalidPathException e) {
                    throw new RuntimeException("Provided Default Open Directory value \"" +
                                               inputComponent.textField.getText().trim() +
                                               "\" is not a valid path.", e);
                }
                if(newValue != ApplicationPreferences.getDefaultOpenDirectory()){
                    ApplicationPreferences.setDefaultOpenDirectory(newValue);
                }
            }

            @Override
            protected DirectoryInputComponent makeComponent() {
                return new DirectoryInputComponent(ApplicationPreferences.getRawDefaultOpenDirectory());
            }
        };
        rows.add(defaultOpenDirectoryRow);

        defaultSaveDirectoryRow = new PreferenceRow<DirectoryInputComponent>("Default Save Directory: ") {
            @Override
            protected void tryToSave() {
                Path newValue;
                try {
                    newValue = Paths.get(inputComponent.textField.getText().trim());
                } catch (InvalidPathException e) {
                    throw new RuntimeException("Provided Default Save Directory value \"" +
                                               inputComponent.textField.getText().trim() +
                                               "\" is not a valid path.", e);
                }
                if(newValue != ApplicationPreferences.getDefaultSaveDirectory()){
                    ApplicationPreferences.setDefaultSaveDirectory(newValue);
                }
            }

            @Override
            protected DirectoryInputComponent makeComponent() {
                return new DirectoryInputComponent(ApplicationPreferences.getRawDefaultSaveDirectory());
            }
        };
        rows.add(defaultSaveDirectoryRow);

        for (PreferenceRow<?> row : rows) {
            rowsPanel.add(row.panel);
        }

        pack();
    }

    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
        //noinspection unused
        MainView view = new MainView(controller);
        JFrame frame = new PreferencesFrame(controller);
        frame.setVisible(true);
    }

    /**
     * A row that labels a preference and provides the user an input component to modify the preference.
     * @param <T> the type of input component that will allow the user to modify the preference.
     */
    public abstract class PreferenceRow<T extends Component> {

        /**
         * a panel that contains all the other components.
         */
        JPanel panel;

        /**
         * a label that indicates what preference is associated with the row.
         */
        JLabel label;

        /**
         * The component that allows the user to modify the preference.
         */
        T inputComponent;

        /**
         * Creates a preference row.
         *
         * @param labelText The text that will be used for the label to indicate the preference associated with the row.
         */
        public PreferenceRow(String labelText) {
            this.panel = new JPanel();
            this.label = new JLabel(labelText);
            this.inputComponent = makeComponent();

            panel.add(label, BorderLayout.LINE_START);
            panel.add(inputComponent, BorderLayout.CENTER);
        }

        /**
         * Saves the changes.
         *
         * @return true if save was successful, else false
         */
        public boolean saveChanges() {

            try {
                tryToSave();
                return true;
            } catch (Exception e) {
                // show message about the error.
                JOptionPane.showMessageDialog(
                        PreferencesFrame.this,
                        e.getLocalizedMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        /**
         * Tries to save the preference in the row, throwing any exception if failing.
         *
         * Failing message should be meaningful to the user as it will be shown to the user as a dialog message.
         */
        protected abstract void tryToSave();

        /**
         * Makes the component that will be stored in the component field.
         * @return a component that the user can interact with to change the preference.
         */
        protected abstract T makeComponent();
    }

}
