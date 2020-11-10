package com.gol;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar extends ToolBar {
    private final MainWindow window;

    public Toolbar(MainWindow mainWindow) {
        this.window = mainWindow;

        Button drawMode = new Button("Draw");
        drawMode.setOnAction(e -> window.setDrawAlive(true));

        Button eraseMode = new Button("Erase");
        eraseMode.setOnAction(e -> window.setDrawAlive(false));

        Button tick = new Button("Tick");
        tick.setOnAction(e -> window.tick());

        Button clear = new Button("Clear");
        clear.setOnAction(e -> window.clear());

        Button play = new Button("Play");
        play.setOnAction(e -> window.play());

        Button stop = new Button("Stop");
        stop.setOnAction(e -> window.stop());

        getItems().addAll(tick, drawMode, eraseMode, clear, play, stop);
    }
}
