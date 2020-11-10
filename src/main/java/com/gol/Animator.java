package com.gol;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class Animator {
    private final Timeline timeline;
    private final MainWindow window;

    public Animator(MainWindow mainWindow) {
        window = mainWindow;
        timeline = new Timeline(new KeyFrame(Duration.millis(100), this::tick));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void tick(ActionEvent actionEvent) {
        window.tick();
    }

    public void play() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }
}
