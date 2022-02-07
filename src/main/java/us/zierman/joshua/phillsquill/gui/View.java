package us.zierman.joshua.phillsquill.gui;

import us.zierman.joshua.phillsquill.ApplicationDefaults;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class View {

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
    JPanel bottomPanel;
    JLabel widthFieldLabel;
    JTextField widthField;
    JButton convertButton;
    JTextArea outputTextArea;
    private final Controller controller;

    public View(Controller controller) throws HeadlessException {
        this.controller = controller;
        controller.setView(this);
        mainFrame = new JFrame();
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
