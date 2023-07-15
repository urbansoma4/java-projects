package com.codecool.roguelike;

import com.codecool.roguelike.ui.GameInputReader;
import com.codecool.roguelike.ui.GameUI;
import com.codecool.roguelike.ui.console.ConsoleGameInputReader;
import com.codecool.roguelike.ui.console.ConsoleUI;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        final int boardWidth = 40;
        final int boardHeight = 20;
        final char wallIcon = '#';
        final int numberOfGates = 1;
        final char gateIconHorizontal = '=';
        final char gateIconVertical = '"';
        final int playerStartX = 3;
        final int playerStartY = 3;
        final char playerIcon = '@';

        Player player = new Player();
        char[][] board = Engine.createBoard(boardWidth, boardHeight, wallIcon, numberOfGates, gateIconHorizontal,
                gateIconVertical);

        Util.clearScreen();

        GameUI ui = new ConsoleUI();
        GameInputReader reader = new ConsoleGameInputReader();

        boolean isRunning = true;

        while (isRunning) {
            Engine.putPlayerOnBoard(board, player);
            ui.displayBoard(board);
            char key = reader.getInputChar();

            if (key == 'q') {
                isRunning = false;
            } else {
                // Implement moving on board below

            }
        }
    }

}
