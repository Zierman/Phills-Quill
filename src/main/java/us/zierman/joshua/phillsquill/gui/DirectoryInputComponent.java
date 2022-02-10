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

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DirectoryInputComponent extends JPanel {
    private static final int TEXT_FIELD_WIDTH = 40;
    JButton button;
    JTextField textField;


    public DirectoryInputComponent(String initialTextFieldText) {
        super();
        textField = new JTextField(initialTextFieldText, TEXT_FIELD_WIDTH);
        button = new JButton("Browse");
        this.add(textField, BorderLayout.CENTER);
        this.add(button, BorderLayout.LINE_END);


        button.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showDialog(getParent(), "Choose");
            if (result == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                textField.setText(file.toPath().toString());
            }
        });
    }
}
