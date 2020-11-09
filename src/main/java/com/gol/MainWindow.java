package com.gol;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class MainWindow extends VBox {
    private boolean drawAlive = true;
    private final int WIDTH;
    private final int HEIGHT;
    private final int SIZE;
    private final int X_RES;
    private final int Y_RES;
    private final double X_SCALE;
    private final double Y_SCALE;
    private final Canvas canvas;
    private final Affine affine;
    private final Toolbar toolbar;
    private final InfoBar info;
    private final Pane spacer;
    private final GraphicsContext gc;
    private final Simulation sim;


    public MainWindow() {
        WIDTH = 450;
        HEIGHT = 400;
        SIZE = 20;
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

        toolbar = new Toolbar(this);

        info = new InfoBar();
        canvas.setOnMouseMoved(e -> info.setPositionLabel(getScaledPosition(e)));
        info.setModeLabel(drawAlive);

        spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sim = new Simulation(X_RES, Y_RES);

        getChildren().addAll(toolbar, canvas, spacer, info);
        setOnKeyPressed(this::onKeyPressed);
        redraw();
    }

    private void redraw() {
        drawBackground(Color.rgb(213, 106, 160));
        drawLiveCells(Color.rgb(252, 240, 204));
        drawLines(Color.rgb(134, 22, 87));
    }

    private void drawBackground(Paint paint) {
        gc.setFill(paint);
        gc.fillRect(0, 0, X_RES, Y_RES);
    }

    private void drawLiveCells(Paint paint) {
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
        int[] pos = getScaledPosition(e);
        int x = pos[0];
        int y = pos[1];
        if (!sim.isAlive(x, y)) sim.setAlive(sim.board, x, y);
        else sim.setDead(sim.board, x, y);
        redraw();
    }

    private void editDrag(MouseEvent e) {
        int[] pos = getScaledPosition(e);
        int x = pos[0];
        int y = pos[1];
        if (drawAlive && !sim.isAlive(x, y)) {
            sim.setAlive(sim.board, x, y);
            redraw();
        } else if (!drawAlive && sim.isAlive(x, y)) {
            sim.setDead(sim.board, x, y);
            redraw();
        }
    }

    private int[] getScaledPosition(MouseEvent e) {
        int[] pos = new int[2];
        try {
            Point2D p2d = affine.inverseTransform(e.getX(), e.getY());
            pos[0] = (int) p2d.getX();
            pos[1] = (int) p2d.getY();
        } catch (NonInvertibleTransformException nonInvertibleTransformException) {
            nonInvertibleTransformException.printStackTrace();
        }
        return pos;
    }

    private void onKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.D) setDrawAlive(true);
        else if (e.getCode() == KeyCode.E) setDrawAlive(false);
    }

    public void setDrawAlive(boolean drawAlive) {
        this.drawAlive = drawAlive;
        info.setModeLabel(drawAlive);
    }

    public void tick() {
        sim.tick();
        redraw();
    }
}
