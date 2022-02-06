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
                "Desired line width (default is 80).");
        Option helpOption = new Option("h", "help", false, "Show help message.");
        Options options = new Options();
        options.addOption(widthOption);
        options.addOption(helpOption);

        try {
            // parse the arguments
            CommandLine commandLine = new DefaultParser().parse(options, args);

            if (commandLine.hasOption(helpOption)){ // the user wants to view help msg
                showHelp(options);
            } else { // the user wants to use the program

                // figure out the width to use
                int width = Integer.parseInt(commandLine.getOptionValue(widthOption, "80"));
                if (width <= 0){
                    throw new IllegalArgumentException("width must be positive.");
                }

                // get the path to the file
                List<String> otherArgs = commandLine.getArgList();
                if (otherArgs.size() < 1){
                    throw new MissingArgumentException("A file must be provided");
                }
                Path path = Path.of(otherArgs.get(0));

                // perform the conversion
                DocxFile docxFile = new DocxFile(path);
                FoldingTool foldingTool = new FoldingTool(width);
                String outputText = docxFile.getText();

                // output the result
                System.out.println(foldingTool.fold(outputText));

            }

        } catch (ParseException e) {
            System.err.println("Error: " + e.getLocalizedMessage());
            showHelp(options);
        }
    }
}
