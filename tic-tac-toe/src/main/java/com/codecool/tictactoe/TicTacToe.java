package com.codecool.tictactoe;

import java.util.*;

public class TicTacToe {
    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu() {
        ticTacToe("HUMAN-HUMAN");
    }

    public static void ticTacToe(String mode) {
        String currentPlayer = "X";
        List<String> validList = new ArrayList<>();
        Collections.addAll(validList, "A1", "A2", "A3", "B1", "B2", "B3", "C1", "C2", "C3");

        List<String> PlayerOMoves = new ArrayList<>();
        List<String> PlayerXMoves = new ArrayList<>();
        initBoard();
        while (!(currentPlayer.equals("exit"))) {
            currentPlayer = mark(validList, PlayerOMoves, PlayerXMoves, getHumanMove(validList, currentPlayer), currentPlayer);
        }
    }

    // Returns an empty 3-by-3 board (with ".").
    public static void initBoard() {
        System.out.println("   1   2   3\n" + "A  . | . | .\n" + "  ---+---+---\n" + "B  . | . | .\n" + "  ---+---+---\n" + "C  . | . | .");
    }

//     This function places the currentPlayer mark in the selected location, removes that location from the valid input list and adds it to that player's move log
//     Then it replaces the still valid inputs (empty spaces) with a dot and fills the board with the player move logs
//     After this, it calls the board printing function which actually does the printing
//     And it checks if the game has ended by the board being full or reaching a win condition
    public static String mark(List<String> validList, List<String> PlayerOMoves, List<String> PlayerXMoves, String location, String currentPlayer) {
        if (Objects.equals(location, "QUIT")) {
            currentPlayer = "exit";
            return currentPlayer;
        }
        String[][] board2 = {{"A1", "A2", "A3"}, {"B1", "B2", "B3"}, {"C1", "C2", "C3"}};
        for (int i = 0; i < board2.length; i++) {
            for (int j = 0; j < board2[i].length; j++) {
                if (Objects.equals(board2[i][j], location)) {
                    if (Objects.equals(currentPlayer, "X")) {
                        PlayerXMoves.add(location);
                        validList.remove(location);
                    } else {
                        PlayerOMoves.add(location);
                        validList.remove(location);
                    }
                }
                if (PlayerOMoves.contains(board2[i][j])) {
                    board2[i][j] = "O";
                } else if (PlayerXMoves.contains(board2[i][j])) {
                    board2[i][j] = "X";
                } else {
                    board2[i][j] = ".";
                }
            }
        }
        printBoard(board2);
        if (!hasWon(currentPlayer, PlayerXMoves, PlayerOMoves)) {
            currentPlayer = Objects.equals(currentPlayer, "X") ? "O" : "X";
            if (isFull(validList)) {
                System.out.println("Game board is full. It's a tie.");
                currentPlayer = "exit";
            }
        } else {
            currentPlayer = "exit";
        }
        return currentPlayer;
    }

    // Returns the coordinates of a valid move for player on board.
    public static String getHumanMove(List<String> validList, String currentPlayer) {
        System.out.println("Next move by " + currentPlayer + ": ");
        Scanner input = new Scanner(System.in);
        String location = input.next().toUpperCase();
        while (!(validList.contains(location))) {
            if (location.equals("QUIT")) {
                return location;
            }
            System.out.println("invalid input");
            location = input.next().toUpperCase();
        }
        return location;
    }

    // Returns True if board is full.
    public static boolean isFull(List<String> validList) {
        if (validList.size() == 0) {
            return true;
        }
        return false;
    }

    public static void printBoard(String[][] board2) {
        System.out.println("   1   2   3\n" + "A  " + board2[0][0] + " | " + board2[0][1] + " | " + board2[0][2] + "\n" + "  ---+---+---\n" + "B  " + board2[1][0] + " | " + board2[1][1] + " | " + board2[1][2] + "\n" + "  ---+---+---\n" + "C  " + board2[2][0] + " | " + board2[2][1] + " | " + board2[2][2] + "");
    }

    // Returns True if player has won the game.
    public static boolean hasWon(String currentPlayer, List<String> PlayerXMoves, List<String> PlayerOMoves) {
        String[][] winCondition = {{"A1", "A2", "A3"}, {"B1", "B2", "B3"}, {"C1", "C2", "C3"}, {"A1", "B1", "C1"}, {"A2", "B2", "C2"}, {"A3", "B3", "C3"}, {"A1", "B2", "C3"}, {"A3", "B2", "C1"}};
        for (int i = 0; i < winCondition.length; i++) {
            if (Objects.equals(currentPlayer, "X") && (new HashSet<>(PlayerXMoves).containsAll(List.of(winCondition[i])))) {
                System.out.println("Player " + currentPlayer + " won the game");
                return true;
            }
            if (Objects.equals(currentPlayer, "O") && (new HashSet<>(PlayerOMoves).containsAll(List.of(winCondition[i])))) {
                System.out.println("Player " + currentPlayer + " won the game");
                return true;
            }
        }
        return false;
    }
}
