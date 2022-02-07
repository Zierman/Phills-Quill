package us.zierman.joshua.phillsquill.gui;

import us.zierman.joshua.phillsquill.DocxFile;
import us.zierman.joshua.phillsquill.FoldingTool;

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
    private View view;
    private Model model;
    private final ConvertButtonListener convertButtonListener;
    private final MenuItemListener menuItemListener;

    public Controller(Model model) {
        this.model = model;
        convertButtonListener = new ConvertButtonListener();
        menuItemListener = new MenuItemListener();
    }

    public void setView(View view) {
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

        if (model.autoConvert) {
            view.convertButton.doClick();
        }
    }

    private void updateOutputWidth() {
        // try to update the model with the output width entered in the view
        try {
            model.outputWidth = Integer.parseInt(view.widthField.getText());

        } catch (NumberFormatException e) {
            // if the value in the view was invalid, show an error message and keep the old value the same
            JOptionPane.showMessageDialog(view.mainFrame,
                    "\"" + view.widthField.getText() + "\" cannot be parsed to an integer, using width of " + model.outputWidth + ".",
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

    public ActionListener getMenuItemListener() {
        return menuItemListener;
    }

    public int getOutputWidth() {
        return model.outputWidth;
    }

    private class ConvertButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateOutputWidth();
        }

    }

    private class MenuItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            var eventSource = e.getSource();
            if (eventSource == view.open) {

                // let user pick a file
                view.fileChooser = new JFileChooser();
                view.fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
                view.fileChooser.addChoosableFileFilter(View.DOCX_FILTER);
                view.fileChooser.setFileFilter(View.DOCX_FILTER);
                int result = view.fileChooser.showOpenDialog(view.mainFrame);
                if (result == JFileChooser.APPROVE_OPTION) { // if the user chooses something

                    File file = view.fileChooser.getSelectedFile();
                    loadFile(file);
                }
            } else if (eventSource == view.save) {

                // let user pick a file
                view.fileChooser = new JFileChooser();
                view.fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
                view.fileChooser.addChoosableFileFilter(View.PLAIN_TEXT_FILTER);
                view.fileChooser.setFileFilter(View.PLAIN_TEXT_FILTER);
                int result = view.fileChooser.showSaveDialog(view.mainFrame);
                if (result == JFileChooser.APPROVE_OPTION) { // if the user chooses something

                    File file = view.fileChooser.getSelectedFile();
                    saveFile(file);
                }
            } else if (eventSource == view.selectAll) {
                view.outputTextArea.selectAll();
            } else if (eventSource == view.copy) {
                var selection = new StringSelection(view.outputTextArea.getSelectedText());
                CLIPBOARD.setContents(selection, null);
            } else if (eventSource == view.copyAll) {
                var selection = new StringSelection(view.outputTextArea.getText());
                CLIPBOARD.setContents(selection, null);
            }
        }
    }
}
