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
