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

import static org.junit.jupiter.api.Assertions.*;

class FoldingToolTest {

    @Test
    @DisplayName("Empty string should fold to empty string")
    void foldTest0() {
        assertEquals("", new FoldingTool(1).fold(""));
        assertEquals("", new FoldingTool(100).fold(""));
    }

    @Test
    @DisplayName("\"abcd\" should fold to \"abcd\" when width is 3")
    void foldTest1() {
        assertEquals("abcd", new FoldingTool(1).fold("abcd"));
    }


    @Test
    @DisplayName("\"abcd\" should fold to \"abcd\" when width is 9")
    void foldTest2() {
        assertEquals("abcd", new FoldingTool(9).fold("abcd"));
    }

    @Test
    @DisplayName("\"abcd efg\" should fold to \"abcd\nefg\" when width is 7")
    void foldTest3() {
        assertEquals("abcd\nefg", new FoldingTool(7).fold("abcd efg"));
    }

    @Test
    @DisplayName("\"abcd efg\" should fold to \"abcd efg\" when width is 8")
    void foldTest4() {
        assertEquals("abcd efg", new FoldingTool(8).fold("abcd efg"));
    }

    @Test
    @DisplayName("\"\tabcd\" should fold to \"\tabcd\" when width is 2")
    void foldTest5() {
        assertEquals("\tabcd", new FoldingTool(2).fold("\tabcd"));
    }
}