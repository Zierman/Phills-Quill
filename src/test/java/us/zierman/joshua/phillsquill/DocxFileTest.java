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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DocxFileTest {

    @Test
    @DisplayName("toString")
    void toStringTest() {
        Path pathToTestDoc = Path.of("src", "test", "resources", "tmp.docx");
        DocxFile docxFile = new DocxFile(pathToTestDoc);
        @SuppressWarnings("UnnecessaryToStringCall") String expected = "DocxFile{pathToDocxFile=\"" + pathToTestDoc.toString() + "\"}";
        assertEquals(expected, docxFile.toString());
    }

    @Test
    void getText() {
        Path pathToTestDoc = Path.of("src", "test", "resources", "tmp.docx");
        DocxFile docxFile = new DocxFile(pathToTestDoc);
        @SuppressWarnings("SpellCheckingInspection") String expectedContent = """
                Lorem Ipsum Dolor


                Simple Home Styling
                Easy Decorating

                L
                orem ipsum dolor sit amet, ligula suspendisse nulla pretium, rhoncus tempor fermentum, enim integer ad vestibulum volutpat. Nisl rhoncus turpis est, vel elit, congue wisi enim nunc ultricies sit, magna tincidunt. Maecenas aliquam maecenas ligula nostra, accumsan taciti. Sociis mauris in integer, a dolor netus non dui aliquet, sagittis felis sodales, dolor sociis mauris, vel eu libero cras.

                Ac dolor ac adipiscing amet bibendum nullam, lacus molestie ut libero nec, diam et, pharetra sodales, feugiat ullamcorper id tempor ac vitae. Mauris pretium aliquet, lectus tincidunt. Porttitor mollis imperdiet libero senectus pulvinar.

                Donec Quis Nunc
                Consectetuer arcu ipsum ornare pellentesque vehicula, in vehicula diam, ornare magna erat felis wisi a risus. Justo fermentum id. Malesuada eleifend, tortor molestie, a a vel et. Mauris at suspendisse, neque aliquam faucibus adipiscing, vivamus in. Wisi mattis leo suscipit nec amet, nisl fermentum tempor ac a, augue in eleifend in venenatis, cras sit id in vestibulum felis in, sed ligula. In sodales suspendisse mauris quam etiam erat, quia tellus convallis eros rhoncus diam orci, porta lectus esse adipiscing posuere et, nisl arcu vitae laoreet.
                Morbi integer molestie, amet suspendisse morbi, amet maecenas, a maecenas mauris neque proin nisl mollis. Suscipit nec ligula ipsum orci nulla, in posuere ut quis ultrices, lectus primis vehicula velit hasellus lectus, vestibulum orci laoreet inceptos vitae, at consectetuer amet et consectetuer. Congue porta scelerisque praesent at, lacus vestibulum et at dignissim cras urna, ante convallis turpis duis lectus sed aliquet, at et ultricies. Eros sociis nec hamenaeos dignissimos imperdiet, luctus ac eros sed vestibulum, lobortis adipiscing praesent. Nec eros eu ridiculus libero felis. Donec arcu risus diam amet sit. Congue tortor risus vestibulum commodo nisl, luctus augue amet quis aenean maecenas sit, donec velit iusto, morbi felis elit et nibh. Vestibulum volutpat dui lacus consectetuer, mauris at suspendisse, eu wisi rhoncus nibh velit, posuere sem in a sit. Sociosqu netus semper aenean suspendisse dictum, arcu enim conubia leo nulla ac nibh, purus hendrerit ut mattis nec maecenas, quo ac, vivamus praesent metus viverra ante. Natoque sed sit hendrerit, dapibus velit molestiae leo a, ut lorem sit et lacus aliquam. Sodales nulla ante auctor excepturi wisi.
                Faucibus semper id vivamus justo vel aliquam. Egestas curabitur sit justo, elit risus velit orci vitae velit, orci curabitur amet recusandae ullamcorper. Quam nascetur fringilla quisque adipiscing porta, in nullam pharetra suspendisse, tincidunt dictumst varius. Quisque vitae lorem, tristique proin ut tincidunt id, ipsum cras bibendum eu arcu faucibus. Pellentesque soluta, mauris nulla erat imperdiet tincidunt est, purus aliquam sociis ac quis, amet lobortis dui amet. Amet quis habitasse vestibulum ipsum a suscipit, donec lectus turpis hendrerit integer laoreet. Feugiat dolor elit pede et wisi, posuere vel class fringilla.
                Curabitur labore. Ac augue donec, sed a dolor luctus, congue arcu id diam praesent, pretium ac, ullamcorper non hac in quisque hac. Magna amet libero maecenas justo. Nam at wisi donec amet nam, quis nulla euismod neque in enim, libero curabitur libero, arcu egestas molestie pede lorem eu. Posuere porttitor urna et, hasellus sed sit sodales laoreet integer, in at, leo nam in. Vitae et, nunc hasellus hasellus, donec dolor, id elit donec hasellus ac pede, quam amet.
                """;

        assertEquals(expectedContent, docxFile.getText());
    }
}