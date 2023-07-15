package com.codecool.roguelike;

public class Engine {

    /**
     * Creates a new game board based on input parameters
     * @param width The width of the board
     * @param height The height of the board
     * @param wallIcon The icon for the wall
     * @param numberOfGates Amount of gates present on the map
     * @param gateIconHorizontal Horizontal gate icon
     * @param gateIconVertical Vertical gate icon
     */
    public static char[][] createBoard(int width, int height, char wallIcon, int numberOfGates,
                                       char gateIconHorizontal, char gateIconVertical) {
        throw new RuntimeException("method createBoard not implemented");
    }

    /**
     * Modifies the game board by placing the player icon at its coordinates
     * @param board The game board
     * @param player The player information containing the icon and coordinates
     */
    public static void putPlayerOnBoard(char[][] board, Player player) {
        throw new RuntimeException("method putPlayerOnBoard not implemented");
    }

    /**
     * Modifies the game board by removing the player icon from its coordinates
     * @param board The game board
     * @param player The player information containing the coordinates
     */
    public static void removePlayerFromBoard(char[][] board, Player player) {
        throw new RuntimeException("method removePlayerFromBoard not implemented");
    }
}
