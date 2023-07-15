package Model;

public class Board {
    private String[][] board;
    private Pawn[][] pawnsBoard;

    public Board(int size) {
        if (10 <= size && size <= 20) {
            this.board = initializeBoard(size);
            this.pawnsBoard = initializePawnsBoard(size);
        }
    }

    public String[][] getBoard() {
        return board;
    }

    public Pawn[][] getPawnsBoard() {
        return pawnsBoard;
    }

    public int getSize() {
        return board.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (int i = 0; i < getBoard().length; i++) {
            sb.append(String.format("%3s", i + 1));
        }

        sb.append("\n");

        for (int x = 0; x < getBoard().length; x++) {
            sb.append((char) ('A' + x));
            for (int y = 0; y < getBoard()[x].length; y++) {
                if (getPawnsBoard()[x][y] != null) {
                    getBoard()[x][y] = String.valueOf(getPawnsBoard()[x][y].getPawnColor().getLetter());
                } else if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) {
                    getBoard()[x][y] = "#";
                } else {
                    getBoard()[x][y] = " ";
                }
                sb.append("  ").append(getBoard()[x][y]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String[][] initializeBoard(int n) {
        board = new String[n][n];

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if ((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0)) {
                    board[x][y] = "#";
                } else {
                    board[x][y] = " ";
                }
            }
        }
        return board;
    }

    public Pawn[][] initializePawnsBoard(int n) {
        pawnsBoard = new Pawn[n][n];
        final int numberOfLines = 4;
        for (int x = 0; x < numberOfLines; x++) {
            for (int y = 0; y < pawnsBoard[0].length; y++) {
                if (!((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0))) {
                    pawnsBoard[x][y] = new Pawn(Color.BLACK, new Coordinates(x, y));
                }
            }
        }
        for (int x = board.length - numberOfLines; x < board.length; x++) {
            for (int y = 0; y < pawnsBoard[0].length; y++) {
                if (!((x % 2 == 0 && y % 2 == 0) || (x % 2 != 0 && y % 2 != 0))) {
                    pawnsBoard[x][y] = new Pawn(Color.WHITE, new Coordinates(x, y));
                }
            }
        }
        return pawnsBoard;
    }

    public void removePawn(Coordinates toRemovePosition) {
        this.pawnsBoard[toRemovePosition.getX()][toRemovePosition.getY()] = null;
    }

    public void movePawn(Coordinates oldPosition, Coordinates newPosition) {
        this.pawnsBoard[newPosition.getX()][newPosition.getY()] = this.pawnsBoard[oldPosition.getX()][oldPosition.getY()];
        this.removePawn(oldPosition);
        this.pawnsBoard[newPosition.getX()][newPosition.getY()].setPosition(newPosition);
    }


    public Pawn getSelectedPawn(Coordinates coordinates) {
        Pawn[][] board = getPawnsBoard();
        Pawn targetedPawn = board[coordinates.getX()][coordinates.getY()];
        return targetedPawn;
    }

    public Coordinates getCoordinatesToRemove(Coordinates startingCoordinates, Coordinates targetCoordinates) {
        int coordinatesToRemoveX = (startingCoordinates.getX() + targetCoordinates.getX()) / 2;
        int coordinatesToRemoveY = (startingCoordinates.getY() + targetCoordinates.getY()) / 2;
        return new Coordinates(coordinatesToRemoveX, coordinatesToRemoveY);
    }

    public boolean checkIfWon() {
        boolean haveWon = true;
        Pawn[][] board = getPawnsBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == null) {
                    haveWon = true;
                } else if (board[i][j].getPawnColor().equals(Color.WHITE) || board[i][j].getPawnColor().equals(Color.BLACK)) {
                    haveWon = false;
                    break;
                } else {
                    haveWon = true;
                }
            }
        }
        return haveWon;
    }
}