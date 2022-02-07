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
