/**
 *    Copyright 2022 Joshua Zierman
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package us.zierman.joshua.phillsquill.gui;

import us.zierman.joshua.phillsquill.ApplicationConstants;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MainView {

    static final int MENU_SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
    static final FileNameExtensionFilter PLAIN_TEXT_FILTER = new FileNameExtensionFilter(
            ".txt",
            "txt");
    static final FileNameExtensionFilter DOCX_FILTER = new FileNameExtensionFilter(
            ".docx",
            "docx");

    JFrame mainFrame;
    JFileChooser fileChooser;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem open;
    JMenuItem save;
    JMenu editMenu;
    JMenuItem selectAll;
    JMenuItem copy;
    JMenuItem copyAll;
    JMenuItem preferences;
    JMenuItem about;
    JPanel bottomPanel;
    JLabel widthFieldLabel;
    JTextField widthField;
    JButton convertButton;
    JButton resetButton;
    JTextArea outputTextArea;
    private final Controller controller;

    public MainView(Controller controller) throws HeadlessException {
        this.controller = controller;
        controller.setView(this);
        mainFrame = new JFrame();
        mainFrame.setTitle("Phill's Quill");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = mainFrame.getContentPane();

        // set up the menu bar
        menuBar = new JMenuBar();
        contentPane.add(menuBar, BorderLayout.PAGE_START);

        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        open = new JMenuItem("Open...", KeyEvent.VK_O);
        open.addActionListener(this.controller.getMenuItemListener());
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, MENU_SHORTCUT_MASK));
        fileMenu.add(open);

        save = new JMenuItem("Save...", KeyEvent.VK_S);
        save.addActionListener(this.controller.getMenuItemListener());
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, MENU_SHORTCUT_MASK));
        fileMenu.add(save);


        editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        selectAll = new JMenuItem("Select All");
        selectAll.addActionListener(this.controller.getMenuItemListener());
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, MENU_SHORTCUT_MASK));
        editMenu.add(selectAll);

        copy = new JMenuItem("Copy");
        copy.addActionListener(this.controller.getMenuItemListener());
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, MENU_SHORTCUT_MASK));
        editMenu.add(copy);

        copyAll = new JMenuItem("Copy All");
        copyAll.addActionListener(this.controller.getMenuItemListener());
        copyAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, MENU_SHORTCUT_MASK | InputEvent.SHIFT_DOWN_MASK));
        editMenu.add(copyAll);

        preferences = new JMenuItem("Preferences...");
        preferences.addActionListener(e -> {
            var preferencesFrame = new PreferencesFrame(controller);
            preferencesFrame.setVisible(true);
        });
        editMenu.add(new JSeparator());
        editMenu.add(preferences);

        about = new JMenuItem("About");
        about.addActionListener(e -> {
            JOptionPane.showMessageDialog(mainFrame, ApplicationConstants.ABOUT_MESSAGE_TEXT_AREA, "About Phill's Quill", JOptionPane.PLAIN_MESSAGE);
        });
        menuBar.add(about);

        // set up the bottom panel
        bottomPanel = new JPanel();
        contentPane.add(bottomPanel, BorderLayout.PAGE_END);

        widthFieldLabel = new JLabel("Output Wdith: ");
        bottomPanel.add(widthFieldLabel, BorderLayout.LINE_START);

        widthField = new JTextField();
        widthField.setColumns(5);
        widthField.setText(String.valueOf(this.controller.getOutputWidth()));
        bottomPanel.add(widthField, BorderLayout.CENTER);

        convertButton = new JButton("Convert");
        convertButton.addActionListener(this.controller.getConvertButtonListener());
        bottomPanel.add(convertButton, BorderLayout.LINE_END);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(this.controller.getResetButtonListener());
        bottomPanel.add(resetButton, BorderLayout.LINE_END);

        // set up the output area
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setLineWrap(false);
        outputTextArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane outputTextScrollPane = new JScrollPane(outputTextArea);
        outputTextScrollPane.setPreferredSize(new Dimension(1000, 500));
        outputTextScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outputTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(outputTextScrollPane, BorderLayout.CENTER);


    }
}
