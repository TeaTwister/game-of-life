package com.gol;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class InfoBar extends HBox {

    private final Label position;
    private final Label mode;

    public InfoBar() {
        position = new Label("0, 0 ");
        mode = new Label();
        getChildren().addAll(position, mode);
    }

    public void setModeLabel(boolean mode) {
        this.mode.setText(mode ? "Draw" : "Erase");
    }

    public void setPositionLabel(int[] pos) {
        position.setText(String.format("%d, %d ", pos[0], pos[1]));
    }
}
