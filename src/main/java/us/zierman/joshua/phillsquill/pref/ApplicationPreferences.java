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

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

public class ApplicationPreferences {
    private static final Preferences preferences = Preferences.userNodeForPackage(ApplicationPreferences.class);
    private static final int DEFAULT_OUTPUT_WIDTH = 80;
    private static final String OUTPUT_WIDTH_KEY = "OUTPUT_WIDTH";
    private static final String SHOULD_AUTO_CONVERT_KEY = "SHOULD_AUTO_CONVERT";
    private static final boolean DEFAULT_SHOULD_AUTO_CONVERT = false;
    private static final String SAVE_DIRECTORY_KEY = "SAVE_DIRECTORY";
    private static final String DEFAULT_SAVE_DIRECTORY = new JFileChooser().getCurrentDirectory().toString();
    private static final String OPEN_DIRECTORY_KEY = "OPEN_DIRECTORY";
    private static final String DEFAULT_OPEN_DIRECTORY = new JFileChooser().getCurrentDirectory().toString();

    public static int getOutputWidth() {
        return preferences.getInt(OUTPUT_WIDTH_KEY, DEFAULT_OUTPUT_WIDTH);
    }

    public static void setOutputWidth(int width) {
        preferences.putInt(OUTPUT_WIDTH_KEY, width);
    }

    public static boolean getShouldAutoConvertKey() {
        return preferences.getBoolean(SHOULD_AUTO_CONVERT_KEY, DEFAULT_SHOULD_AUTO_CONVERT);
    }

    public static void setShouldAutoConvertKey(boolean newValue) {
        preferences.putBoolean(SHOULD_AUTO_CONVERT_KEY, newValue);
    }

    public static void setDefaultSaveDirectory(Path path) {
        preferences.put(SAVE_DIRECTORY_KEY, path.toString().trim());
    }

    public static Path getDefaultSaveDirectory() {
        String result = preferences.get(SAVE_DIRECTORY_KEY, DEFAULT_SAVE_DIRECTORY);
        if(result.isEmpty()) {
            return Paths.get(DEFAULT_SAVE_DIRECTORY);
        }else {
            File file = null;
            Path path = null;
            try {
                path = Paths.get(result);
                file = path.toFile();
            } catch (UnsupportedOperationException e) {
                return Paths.get(DEFAULT_SAVE_DIRECTORY);
            }

            if (file.isDirectory()) {
                return path;
            } else {
                return Paths.get(DEFAULT_SAVE_DIRECTORY);
            }
        }
    }


    public static void setDefaultOpenDirectory(Path path) {
        preferences.put(OPEN_DIRECTORY_KEY, path.toString().trim());
    }

    public static Path getDefaultOpenDirectory() {
        String result = preferences.get(OPEN_DIRECTORY_KEY, DEFAULT_OPEN_DIRECTORY);
        if(result.isEmpty()) {
            return Paths.get(DEFAULT_OPEN_DIRECTORY);
        }else {
            File file = null;
            Path path = null;
            try {
                path = Paths.get(result);
                file = path.toFile();
            } catch (UnsupportedOperationException e) {
                return Paths.get(DEFAULT_OPEN_DIRECTORY);
            }

            if (file.isDirectory()) {
                return path;
            } else {
                return Paths.get(DEFAULT_OPEN_DIRECTORY);
            }
        }
    }
}
