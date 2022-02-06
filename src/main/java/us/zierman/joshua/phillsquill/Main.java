package us.zierman.joshua.phillsquill;

import org.apache.commons.cli.*;

import java.nio.file.Path;
import java.util.List;

public class Main {

    private static void showHelp(Options options) {

        String description = "This program allows the user to convert a .docx file into \n" +
                "plain text folded at a specified width.";
        String header = "\nDescription:\n" +
                description + "\n\n" +
                "Options:";
        new HelpFormatter().printHelp("phillsquill [Options] <File>", header, options, "");
    }

    public static void main(String[] args) {

        // define options
        Option widthOption = new Option(
                "w",
                "width",
                true,
                "Desired line width (default is " + ApplicationDefaults.DEFAULT_OUTPUT_WIDTH + ").");
        Option helpOption = new Option("h", "help", false, "Show help message.");
        Option guiOption = new Option("g", "gui", false, "launch gui app.");
        Option autoConvertOption = new Option("a", "auto-convert", false, "Automatically converts when running gui.");

        Options options = new Options();
        options.addOption(widthOption);
        options.addOption(helpOption);
        options.addOption(guiOption);
        options.addOption(autoConvertOption);

        try {
            // parse the arguments
            CommandLine commandLine = new DefaultParser().parse(options, args);

            if (commandLine.hasOption(helpOption)) { // the user wants to view help msg
                showHelp(options);
            } else { // the user wants to use the program

                // figure out the width to use
                int width = Integer.parseInt(commandLine.getOptionValue(widthOption, String.valueOf(ApplicationDefaults.DEFAULT_OUTPUT_WIDTH)));
                if (width <= 0) {
                    throw new IllegalArgumentException("width must be positive.");
                }

                // check for auto-convert flag
                boolean autoConvertIsSet = commandLine.hasOption(autoConvertOption);

                // get the path to the file
                List<String> otherArgs = commandLine.getArgList();
                if (otherArgs.size() < 1) {
                    // since no file was provided we'll try launching the gui.
                    new GUI(width, autoConvertIsSet).run();
                } else if (commandLine.hasOption(guiOption)) { // user wants to run in GUI and provided a path
                    Path path = Path.of(otherArgs.get(0));
                    new GUI(width, path, autoConvertIsSet).run();
                } else{ // user did not want to run with gui and did provide path

                    Path path = Path.of(otherArgs.get(0));

                    // perform the conversion
                    DocxFile docxFile = new DocxFile(path);
                    FoldingTool foldingTool = new FoldingTool(width);
                    String outputText = docxFile.getText();

                    // output the result
                    System.out.println(foldingTool.fold(outputText));

                }


            }

        } catch (ParseException e) {
            System.err.println("Error: " + e.getLocalizedMessage());
            showHelp(options);
        }
    }
}
