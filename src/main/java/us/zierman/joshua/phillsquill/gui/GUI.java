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
package us.zierman.joshua.phillsquill.gui;

import java.nio.file.Path;

public class GUI implements Runnable {
    private final Controller controller;
    private final Model model;
    private final View view;

    public GUI(int width, Path path, boolean autoConvert) {
        this.model = new Model();
        this.model.outputWidth = width;
        this.model.autoConvert = autoConvert;
        this.controller = new Controller(model);
        this.view = new View(controller);

        if(path != null){
            this.controller.loadFile(path);
        }
    }



    @Override
    public void run() {
        view.mainFrame.pack();
        view.mainFrame.setVisible(true);
    }
}
