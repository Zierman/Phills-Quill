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
package us.zierman.joshua.phillsquill;

import org.apache.commons.cli.*;
import us.zierman.joshua.phillsquill.gui.GUI;
import us.zierman.joshua.phillsquill.pref.ApplicationPreferences;

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
                "Desired line width (default is " + ApplicationPreferences.getOutputWidth() + ").");
        Option helpOption = new Option("h", "help", false, "Show help message.");
        Option guiOption = new Option("g", "gui", false, "launch gui app.");

        Options options = new Options();
        options.addOption(widthOption);
        options.addOption(helpOption);
        options.addOption(guiOption);

        try {
            // parse the arguments
            CommandLine commandLine = new DefaultParser().parse(options, args);

            if (commandLine.hasOption(helpOption)) { // the user wants to view help msg
                showHelp(options);
            } else { // the user wants to use the program

                // figure out the width to use
                int width = Integer.parseInt(commandLine.getOptionValue(widthOption, String.valueOf(ApplicationPreferences.getOutputWidth())));
                if (width <= 0) {
                    throw new IllegalArgumentException("width must be positive.");
                }

                // get the path to the file
                List<String> otherArgs = commandLine.getArgList();
                if (otherArgs.size() < 1) {
                    // since no file was provided we'll try launching the gui.
                    new GUI(width, null).run();
                } else if (commandLine.hasOption(guiOption)) { // user wants to run in GUI and provided a path
                    Path path = Path.of(otherArgs.get(0));
                    new GUI(width, path).run();
                } else { // user did not want to run with gui and did provide path

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
