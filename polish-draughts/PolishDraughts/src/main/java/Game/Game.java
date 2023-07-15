package Game;

import Model.Board;
import Model.Color;
import Model.Coordinates;
import Model.Pawn;
import View.TerminalView;

public class Game {

    public static void startGame() {

        boolean isRunning = true;
        State currentState = State.MENU;
        Board board = null;
        Color playerColor = Color.BLACK;

        TerminalView.printMenu();

        while (isRunning) {
            switch (currentState) {
                case MENU -> {

                    String menuChoice = TerminalView.getInput("Choice");
                    if (menuChoice.equals("play")) {
                        currentState = State.INIT;
                    } else if (menuChoice.equals("exit")) {
                        currentState = State.END;
                    } else {
                        TerminalView.printErrorMessage("Not a valid option");
                    }
                }

                case INIT -> {

                    int tableSize = TerminalView.getBoardSize();
                    board = new Board(tableSize);
                    currentState = State.PRINT;
                }

                case PRINT -> {

                    TerminalView.printBoard(board);
                    boolean isWinner = board.checkIfWon();
                    if (isWinner) {
                        TerminalView.printMessage("Dear " + playerColor + " Player! You won!");
                        currentState = State.END;
                    } else if (playerColor == Color.WHITE) {
                        playerColor = Color.BLACK;
                        TerminalView.printMessage("Current Player is " + playerColor);
                        currentState = State.MOVE;
                    } else {
                        playerColor = Color.WHITE;
                        TerminalView.printMessage("Current Player is " + playerColor);
                        currentState = State.MOVE;
                    }
                }

                case MOVE -> {

                    Coordinates startingCoordinates = TerminalView.getStartingCoordinates();
                    Pawn selectedPawn = board.getSelectedPawn(startingCoordinates);
                    Coordinates targetCoordinates = TerminalView.getTargetCoordinates();

                    if (selectedPawn.isCanMove(targetCoordinates) && board.getSelectedPawn(targetCoordinates) == null) {
                        board.movePawn(startingCoordinates, targetCoordinates);
                        selectedPawn.setWhereCanMove(targetCoordinates);
                        selectedPawn.setWhereCanCapture(targetCoordinates);
                        currentState = State.PRINT;
                    } else if (selectedPawn.isCanCapture(targetCoordinates) && board.getSelectedPawn(targetCoordinates) == null) {
                        board.movePawn(startingCoordinates, targetCoordinates);
                        Coordinates coordinatesToRemove = board.getCoordinatesToRemove(startingCoordinates, targetCoordinates);
                        board.removePawn(coordinatesToRemove);
                        selectedPawn.setWhereCanMove(targetCoordinates);
                        selectedPawn.setWhereCanCapture(targetCoordinates);
                        currentState = State.PRINT;
                    } else {
                        TerminalView.printErrorMessage("Invalid Move");
                    }
                }

                case END -> {

                    TerminalView.printMessage("Thank you for playing");
                    isRunning = false;
                }
            }
        }
    }
}