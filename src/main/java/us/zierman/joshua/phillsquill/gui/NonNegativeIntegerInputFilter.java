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
package us.zierman.joshua.phillsquill.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NonNegativeIntegerInputFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (passes(string)) {
            super.insertString(fb, offset, string.trim(), attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (passes(text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    private boolean passes(String string) {

        if (string == null || string.trim().isEmpty()) {
            return true;
        } else if (string.contains("-") || string.contains("+")) {
            return false;
        } else {
            try {
                Integer.parseInt(string.trim());
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}
