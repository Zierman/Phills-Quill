package us.zierman.joshua.phillsquill;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;

public class GUI implements Runnable{
    private boolean autoConvert;
    private String originalText;
    private int outputWidth;
    private JFrame mainFrame;
    private JFileChooser fileChooser;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem open;
    private JPanel bottomPanel;
    private JLabel widthFieldLabel;
    private JTextField widthField;
    private JButton convertButton;
    private JTextArea outputTextArea;
    private final ActionListener menuItemListener;

    public GUI(int width, @Nullable Path pathToDocxFile, boolean autoConvert) throws HeadlessException {
        this.autoConvert = autoConvert;
        mainFrame = new JFrame();
        Container contentPane = mainFrame.getContentPane();
//        mainFrame.setLayout(new BorderLayout());
        menuItemListener = new MenuItemListener();
        outputWidth = ApplicationDefaults.DEFAULT_OUTPUT_WIDTH;
        originalText = "";

        // set up the menu bar
        menuBar = new JMenuBar();
        contentPane.add(menuBar, BorderLayout.PAGE_START);

        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        open = new JMenuItem("Open...");
        open.addActionListener(menuItemListener);
        fileMenu.add(open);

        // set up the bottom panel
        bottomPanel = new JPanel();
        contentPane.add(bottomPanel, BorderLayout.PAGE_END);

        widthFieldLabel = new JLabel("Output Wdith: ");
        bottomPanel.add(widthFieldLabel, BorderLayout.LINE_START);

        widthField = new JTextField();
        widthField.setColumns(5);
        widthField.setText(String.valueOf(width));
        bottomPanel.add(widthField, BorderLayout.CENTER);

        convertButton = new JButton("Convert");
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FoldingTool foldingTool = new FoldingTool(GUI.this.getUpdatedDesiredWidth());
                outputTextArea.setText(foldingTool.fold(originalText));
            }
        });
        bottomPanel.add(convertButton, BorderLayout.LINE_END);

        // set up the output area
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        outputTextArea.setLineWrap(false);
        outputTextArea.setMargin(new Insets(10,10,10,10));
        JScrollPane outputTextScrollPane = new JScrollPane(outputTextArea);
        outputTextScrollPane.setPreferredSize(new Dimension(1000, 500));
        outputTextScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outputTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(outputTextScrollPane, BorderLayout.CENTER);

        if(pathToDocxFile != null){
            loadFile(pathToDocxFile);
        }

    }

    public GUI() {
        this(ApplicationDefaults.DEFAULT_OUTPUT_WIDTH, null, false);
    }

    public GUI(boolean autoConvert) {
        this(ApplicationDefaults.DEFAULT_OUTPUT_WIDTH, null, autoConvert);
    }

    public GUI(Path pathToDocxFile, boolean autoConvert) {
        this(ApplicationDefaults.DEFAULT_OUTPUT_WIDTH, pathToDocxFile, autoConvert);
    }

    public GUI(int width, boolean autoConvert) {
        this(width, null, autoConvert);
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.run();
    }

    @Override
    public void run() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    class MenuItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            var eventSource = e.getSource();
            if (eventSource == open) {

                // let user pick a file
                fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(mainFrame);
                if (result == JFileChooser.APPROVE_OPTION) { // if the user chooses something

                    File file = fileChooser.getSelectedFile();
                    loadFile(file);
                }
            }
        }
    }

    private void loadFile(File file) {
        loadFile(file.toPath());
    }

    private void loadFile(Path path) {
        // convert the file content to hard-wrapped plain text
        DocxFile docxFile = new DocxFile(path);
        originalText = docxFile.getText();

        // put that text into the text area
        outputTextArea.setText(originalText);

        if (autoConvert){
            convertButton.doClick();
        }
    }


    private int getUpdatedDesiredWidth() {
        try {
            outputWidth = Integer.parseInt(widthField.getText());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame,
                    "\"" + widthField.getText() + "\" cannot be parsed to an integer, using width of " + outputWidth + ".");
        }

        return outputWidth;
    }
}
