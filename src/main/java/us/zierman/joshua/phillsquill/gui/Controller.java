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

import us.zierman.joshua.phillsquill.DocxFile;
import us.zierman.joshua.phillsquill.FoldingTool;
import us.zierman.joshua.phillsquill.pref.ApplicationPreferences;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;

public class Controller {
    static final Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();
    private final ConvertButtonListener convertButtonListener;
    private final ResetButtonListener resetButtonListener;
    private final MenuItemListener menuItemListener;
    private MainView view;
    private final Model model;

    public Controller(Model model) {
        this.model = model;
        convertButtonListener = new ConvertButtonListener();
        resetButtonListener = new ResetButtonListener();
        menuItemListener = new MenuItemListener();
    }

    public void setView(MainView view) {
        this.view = view;
    }


    private void saveFile(File file) {
        saveFile(file.toPath());
    }

    private void saveFile(Path path) {
        saveFile(path.toString());
    }

    private void saveFile(String path) {
        try (
                FileOutputStream fos = new FileOutputStream(path);
                OutputStreamWriter writer = new OutputStreamWriter(fos)
        ) {
            writer.write(view.outputTextArea.getText());
            JOptionPane.showMessageDialog(view.mainFrame,
                    "Save complete.",
                    "",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view.mainFrame,
                    "Unable to save.\n\nError message: " + e.getLocalizedMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    void loadFile(File file) {
        loadFile(file.toPath());
    }

    void loadFile(Path path) {
        // convert the file content to hard-wrapped plain text
        DocxFile docxFile = new DocxFile(path);
        model.originalText = docxFile.getText();

        // put that text into the text area
        view.outputTextArea.setText(model.originalText);

        if (model.shouldAutoConvert) {
            view.convertButton.doClick();
        }
    }

    private void updateOutputWidth() {
        // try to update the model with the output width entered
        try {
            model.outputWidth = Integer.parseInt(view.widthField.getText().trim());

        } catch (NumberFormatException e) {
            // if the value in the view was invalid, show an error message and keep the old value the same
            JOptionPane.showMessageDialog(view.mainFrame,
                    "\"" + view.widthField.getText().trim() + "\" cannot be parsed to an integer, using width of " + model.outputWidth + ".",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // update the output text
        FoldingTool foldingTool = new FoldingTool(model.outputWidth);
        model.outputText = foldingTool.fold(model.originalText);

        // update the view's output text
        view.outputTextArea.setText(model.outputText);
    }

    public ActionListener getConvertButtonListener() {
        return convertButtonListener;
    }

    public ActionListener getResetButtonListener() {
        return resetButtonListener;
    }

    public ActionListener getMenuItemListener() {
        return menuItemListener;
    }

    public int getOutputWidth() {
        return model.outputWidth;
    }

    public void setOutputWidth(int width) {
        if (width <= 0) {
            throw new IllegalArgumentException("Output width must be positive");
        }
        model.outputWidth = width;
        view.widthField.setText(String.valueOf(width));

        if (model.shouldAutoConvert) {
            view.convertButton.doClick();
        }
    }

    public boolean getShouldAutoConvert() {
        return model.shouldAutoConvert;
    }

    public void setShouldAutoConvert(boolean newValue) {
        model.shouldAutoConvert = newValue;
    }

    private class ConvertButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateOutputWidth();
        }

    }

    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setOutputWidth(ApplicationPreferences.getOutputWidth());
        }

    }

    private class MenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object eventSource = e.getSource();
            if (eventSource == view.open) {

                // let user pick a file
                view.fileChooser = new JFileChooser();
                view.fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
                view.fileChooser.addChoosableFileFilter(MainView.DOCX_FILTER);
                view.fileChooser.setFileFilter(MainView.DOCX_FILTER);
                int result = view.fileChooser.showOpenDialog(view.mainFrame);
                if (result == JFileChooser.APPROVE_OPTION) { // if the user chooses something

                    File file = view.fileChooser.getSelectedFile();
                    loadFile(file);
                }
            } else if (eventSource == view.save) {

                // let user pick a file
                view.fileChooser = new JFileChooser();
                view.fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
                view.fileChooser.addChoosableFileFilter(MainView.PLAIN_TEXT_FILTER);
                view.fileChooser.setFileFilter(MainView.PLAIN_TEXT_FILTER);
                int result = view.fileChooser.showSaveDialog(view.mainFrame);
                if (result == JFileChooser.APPROVE_OPTION) { // if the user chooses something

                    File file = view.fileChooser.getSelectedFile();
                    saveFile(file);
                }
            } else if (eventSource == view.selectAll) {
                view.outputTextArea.selectAll();
            } else if (eventSource == view.copy) {
                StringSelection selection = new StringSelection(view.outputTextArea.getSelectedText());
                CLIPBOARD.setContents(selection, null);
            } else if (eventSource == view.copyAll) {
                StringSelection selection = new StringSelection(view.outputTextArea.getText());
                CLIPBOARD.setContents(selection, null);
            }
        }
    }
}
