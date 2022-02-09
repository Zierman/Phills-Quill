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
package us.zierman.joshua.phillsquill;

import org.apache.commons.text.WordUtils;

import java.util.List;

/**
 * A tool that is used to fold text by limiting the width of the text, creating new lines as needed.
 */
public class FoldingTool {
    private final int width;

    /**
     * Creates an instance of a folding tool.
     * @param width The number of characters allowed on a line.
     */
    public FoldingTool(int width) {
        this.width = width;
    }

    /**
     * Folds text to fit within the desired width.
     *
     * @param inStr The text to fold.
     * @return The folded text as a String.
     */
    public String fold(String inStr) {

        StringBuilder stringBuilder = new StringBuilder();

        // split the provided string into lines
        List<String> lines = List.of(inStr.split("\n"));

        // iterate through the lines, wrapping each line
        boolean isFirstLine = true;
        for (String line : lines) {
            if (isFirstLine) {
                isFirstLine = false;
            } else {
                stringBuilder.append(System.lineSeparator());
            }
            stringBuilder.append(WordUtils.wrap(line, width, null, false));
        }

        return stringBuilder.toString();
    }
}
