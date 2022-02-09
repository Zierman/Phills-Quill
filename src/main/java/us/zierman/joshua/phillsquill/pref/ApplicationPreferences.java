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
package us.zierman.joshua.phillsquill.pref;

import java.util.prefs.Preferences;

public class ApplicationPreferences {
    private static final Preferences preferences = Preferences.userNodeForPackage(ApplicationPreferences.class);
    private static final int DEFAULT_OUTPUT_WIDTH = 80;
    private static final String OUTPUT_WIDTH_KEY = "OUTPUT_WIDTH";
    private static final String SHOULD_AUTO_CONVERT = "SHOULD_AUTO_CONVERT";

    public static int getOutputWidth() {
        return preferences.getInt(OUTPUT_WIDTH_KEY, DEFAULT_OUTPUT_WIDTH);
    }

    public static void setOutputWidth(int width) {
        preferences.putInt(OUTPUT_WIDTH_KEY, width);
    }

    public static boolean getShouldAutoConvert() {
        return preferences.getBoolean(SHOULD_AUTO_CONVERT, false);
    }

    public static void setShouldAutoConvert(boolean newValue) {
        preferences.putBoolean(SHOULD_AUTO_CONVERT, newValue);
    }
}
