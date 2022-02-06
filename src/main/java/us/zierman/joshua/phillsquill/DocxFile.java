package us.zierman.joshua.phillsquill;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * An object that represents a .docx formatted file.
 */
public class DocxFile {

    private final Path pathToDocxFile;

    /**
     * Constructs an instance of a DocxFile from a path.
     *
     * @param pathToDocxFile the path to the file.
     */
    public DocxFile(Path pathToDocxFile) {
        this.pathToDocxFile = pathToDocxFile;
    }

    /**
     * Gets all the text of the file.
     *
     * @return A string containing all text in the file.
     */
    public String getText() {
        try (
                FileInputStream fis = new FileInputStream(pathToDocxFile.toFile().getAbsolutePath());
                XWPFDocument doc = new XWPFDocument(fis);
                XWPFWordExtractor extractor = new XWPFWordExtractor(doc)
        ) {
            return extractor.getText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "DocxFile{" +
                "pathToDocxFile=\"" + pathToDocxFile + "\"" +
                '}';
    }
}
