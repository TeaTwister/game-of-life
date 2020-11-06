package com.gol;

import java.util.Scanner;

public class Simulation {
    int width;
    int height;

    int[][] board;

    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        board = new int[width][height];
    }

    public static void main(String[] args) {
        Simulation s = new Simulation(10, 5);
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        while (input != 0) {
            for (int i = 0; i < input; i++) {
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
}
