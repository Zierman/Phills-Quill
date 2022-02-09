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
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class PreferencesFrame extends JFrame {

    Controller controller;
    JPanel rowsPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    List<PreferenceRow<?>> rows = new ArrayList<>();
    PreferenceRow<JTextField> outputWidthRow;
    PreferenceRow<JCheckBox> shouldAutoConvertRow;
    JButton cancelButton = new JButton("Cancel");
    JButton saveChangesButton = new JButton("Save Changes");

    public PreferencesFrame(Controller controller) {
        this.controller = controller;
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
        outputWidthRow = new PreferenceRow<>("Default Output Width: ") {

            @Override
            protected void tryToSave() {
                String text = inputComponent.getText().strip();
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
                var textField = new JTextField(String.valueOf(ApplicationPreferences.getOutputWidth()), 5);
                var doc = textField.getDocument();
                if (doc instanceof PlainDocument) {
                    ((PlainDocument) doc).setDocumentFilter(new NonNegativeIntegerInputFilter());
                }
                return textField;
            }
        };
        rows.add(outputWidthRow);

        shouldAutoConvertRow = new PreferenceRow<>("Auto-Convert: ") {
            @Override
            protected void tryToSave() {
                boolean newValue = inputComponent.isSelected();
                if (newValue != controller.getShouldAutoConvert()) {
                    ApplicationPreferences.setShouldAutoConvert(newValue);
                    controller.setShouldAutoConvert(newValue);
                }
            }

            @Override
            protected JCheckBox makeComponent() {
                var checkbox = new JCheckBox();
                checkbox.setSelected(ApplicationPreferences.getShouldAutoConvert());
                return checkbox;
            }
        };
        rows.add(shouldAutoConvertRow);

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

    public abstract class PreferenceRow<T extends Component> {
        JPanel panel;
        JLabel label;
        T inputComponent;

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


        protected abstract void tryToSave();

        protected abstract T makeComponent();
    }

}
