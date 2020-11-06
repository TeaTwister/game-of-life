package com.gol;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Affine;

public class MainWindow extends VBox {
    private final int width;
    private final int height;
    private final int size;
    private final int xRes;
    private final int yRes;
    private final Button tick;
    private final Canvas canvas;
    private final Affine affine;
    private final GraphicsContext gc;
    private Simulation sim;


    public MainWindow() {
        width = 400;
        height = 400;
        size = 40;
        canvas = new Canvas(width, height);

        xRes = width / size;
        yRes = height / size;
        affine = new Affine();
        affine.appendScale(((float) width) / xRes, ((float) height) / yRes);

        gc = canvas.getGraphicsContext2D();
        gc.setTransform(affine);

        tick = new Button("Tick");
        tick.setOnAction(e -> {
            sim.tick();
            redraw();
        });

        sim = new Simulation(xRes, yRes);
        sim.setAlive(sim.board, 1, 1);
        sim.setAlive(sim.board, 2, 1);
        sim.setAlive(sim.board, 3, 1);
        sim.setAlive(sim.board, 3, 2);
        sim.setAlive(sim.board, 2, 3);

        this.getChildren().addAll(tick, canvas);
        redraw();
    }

    private void redraw() {
        drawBackground(Color.rgb(50, 50, 50));
        drawCells(Color.LIGHTGREY);
        drawLines(Color.BLACK);
    }

    private void drawBackground(Paint paint) {
        gc.setFill(paint);
        gc.fillRect(0, 0, xRes, yRes);
    }

    private void drawCells(Paint paint) {
        gc.setFill(paint);
        for (int x = 0; x < xRes; x++) {
            for (int y = 0; y < yRes; y++) {
                if (sim.isAlive(x, y)) gc.fillRect(x, y, 1, 1);
            }
        }
    }

    private void drawLines(Paint paint) {
        gc.setStroke(paint);
        gc.setLineWidth(0.1);
        for (int x = 1; x < xRes; x++) {
            gc.strokeLine(x, 0, x, yRes);
        }
        for (int y = 1; y < yRes; y++) {
            gc.strokeLine(0, y, xRes, y);
        }
    }
}
