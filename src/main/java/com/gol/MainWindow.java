package com.gol;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Affine;

public class MainWindow extends VBox {
    private boolean drawAlive = true;
    private final int WIDTH;
    private final int HEIGHT;
    private final int SIZE;
    private final int X_RES;
    private final int Y_RES;
    private final double X_SCALE;
    private final double Y_SCALE;
    private final Button tick;
    private final Canvas canvas;
    private final Affine affine;
    private final GraphicsContext gc;
    private Simulation sim;


    public MainWindow() {
        WIDTH = 450;
        HEIGHT = 400;
        SIZE = 30;
        X_RES = WIDTH / SIZE;
        Y_RES = HEIGHT / SIZE;
        X_SCALE = ((float) WIDTH) / X_RES;
        Y_SCALE = ((float) HEIGHT) / Y_RES;

        canvas = new Canvas(WIDTH, HEIGHT);
        canvas.setOnMousePressed(this::editPress);
        canvas.setOnMouseDragged(this::editDrag);

        affine = new Affine();
        affine.appendScale(X_SCALE, Y_SCALE);

        gc = canvas.getGraphicsContext2D();
        gc.setTransform(affine);

        tick = new Button("Tick");
        tick.setOnAction(e -> {
            sim.tick();
            redraw();
        });

        sim = new Simulation(X_RES, Y_RES);

        this.getChildren().addAll(tick, canvas);
        this.setOnKeyPressed(this::onKeyPressed);
        redraw();
    }

    private void redraw() {
        drawBackground(Color.rgb(213, 106, 160));
        drawCells(Color.rgb(252, 240, 204));
        drawLines(Color.rgb(134, 22, 87));
    }

    private void drawBackground(Paint paint) {
        gc.setFill(paint);
        gc.fillRect(0, 0, X_RES, Y_RES);
    }

    private void drawCells(Paint paint) {
        gc.setFill(paint);
        for (int x = 0; x < X_RES; x++) {
            for (int y = 0; y < Y_RES; y++) {
                if (sim.isAlive(x, y)) gc.fillRect(x, y, 1, 1);
            }
        }
    }

    private void drawLines(Paint paint) {
        gc.setStroke(paint);
        gc.setLineWidth(0.05);
        for (int x = 1; x < X_RES; x++) {
            gc.strokeLine(x, 0, x, Y_RES);
        }
        for (int y = 1; y < Y_RES; y++) {
            gc.strokeLine(0, y, X_RES, y);
        }
    }

    private void editPress(MouseEvent e) {
        int x = (int) (e.getX() / X_SCALE);
        int y = (int) (e.getY() / Y_SCALE);
        if (!sim.isAlive(x, y)) sim.setAlive(sim.board, x, y);
        else sim.setDead(sim.board, x, y);
        redraw();
    }

    private void editDrag(MouseEvent e) {
        int x = (int) (e.getX() / X_SCALE);
        int y = (int) (e.getY() / Y_SCALE);
        if (drawAlive && !sim.isAlive(x, y)) {
            sim.setAlive(sim.board, x, y);
            redraw();
        } else if (!drawAlive && sim.isAlive(x, y)) {
            sim.setDead(sim.board, x, y);
            redraw();
        }
    }

    private void onKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.D) drawAlive = true;
        else if (e.getCode() == KeyCode.E) drawAlive = false;
    }
}
