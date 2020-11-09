package com.gol;
import java.util.Scanner;

public class Simulation {
    int width;
    int height;

    int[][] board;
    int[][] nextBoard;

    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        board = new int[width][height];
    }

    public static void main(String[] args) {
        Simulation s = new Simulation(90, 5);
        s.setAlive(s.board, 1, 1);
        s.setAlive(s.board, 2, 1);
        s.setAlive(s.board, 3, 1);
        s.setAlive(s.board, 3, 2);
        s.setAlive(s.board, 2, 3);

        s.printBoard();

        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        while (input != 0) {
            for (int i = 0; i < input; i++) {
                s.tick();
                s.printBoard();
            }
            input = scanner.nextInt();
        }
    }

    public void printBoard() {
        System.out.println("-".repeat(width + 2));
        for (int j = 0; j < height; j++) {
            StringBuilder sb = new StringBuilder(width + 2);
            sb.append("|");
            for (int i = 0; i < width; i++) {
                sb.append(board[i][j] == 0 ? "." : "*");
            }
            System.out.println(sb.append("|"));
        }
        System.out.println("-".repeat(width + 2));
    }

    public void setAlive(int[][] board, int x, int y) {
        board[wrapWidth(x)][wrapHeight(y)] = 1;
    }

    public void setDead(int[][] board, int x, int y) {
        board[wrapWidth(x)][wrapHeight(y)] = 0;
    }

    public int wrapWidth(int x) {
        return Math.floorMod(x, width);
    }

    public int wrapHeight(int y) {
        return Math.floorMod(y, height);
    }

    public void tick() {
        nextBoard = new int[width][height];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                process(i, j);
            }
        }
        board = nextBoard;
    }

    public void process(int x, int y) {
        int count = countAliveNeighbours(x, y);
        if (isAlive(x, y)) {
            if (count < 2 | count > 3) setDead(nextBoard, x, y);
            else setAlive(nextBoard, x, y);
        } else if (count == 3) setAlive(nextBoard, x, y);
    }

    public int countAliveNeighbours(int x, int y) {
        int count = 0;
        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++) {
                if (i == 0 && j == 0) continue;
                if (isAlive(x + i, y + j)) count++;
            }
        }
        return count;
    }

    public boolean isAlive(int x, int y) {
        return board[wrapWidth(x)][wrapHeight(y)] != 0;
    }
}
