package View;

import Model.Board;
import Model.Coordinates;
import java.util.Scanner;

public class TerminalView {

    public static void printMessage (String message) {
        System.out.println(message);
    }

    public static void printErrorMessage (String message) {
        System.out.println("! " + message + " !");
    }

    public static void printMenu () {
        System.out.println("Welcome To Polish Draughts!");
        System.out.println("Please choose an option");
        System.out.println("play: Play a game!");
        System.out.println("exit: Exit game");
    }

    public static void printBoard (Board board) {
        TerminalView.printMessage(board.toString());
    }

    public static String getInput (String label) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(label + ": ");
        return scanner.nextLine();
    }

    public static int getBoardSize(){
        System.out.println("Please type the size of the board! ");
        Scanner scanner = new Scanner(System.in);
        String sizeAdded = scanner.nextLine();
        while (Integer.parseInt(sizeAdded) < 10 || Integer.parseInt(sizeAdded) > 20){
            System.out.println("The size must be between 10 and 20.");
            System.out.println("Please type the size again!");
            sizeAdded = scanner.nextLine();
        }
        return Integer.parseInt(sizeAdded);
    }
    public static Coordinates getStartingCoordinates(){
        System.out.println("Please choose a Pawn!");
        Scanner scanner = new Scanner(System.in);
        String pawnCoordinates = scanner.nextLine();
        int x = Character.getNumericValue(pawnCoordinates.charAt(0))-10;
        int y = Integer.valueOf(pawnCoordinates.substring(1))-1;
        Coordinates validatedCoordinates = new Coordinates(x,y);


        return validatedCoordinates;
    }

    public static Coordinates getTargetCoordinates () {
        System.out.println("Please choose a location!");
        Scanner scanner = new Scanner(System.in);
        String pawnCoordinates = scanner.nextLine();
        int x = Character.getNumericValue(pawnCoordinates.charAt(0))-10;
        int y = Integer.valueOf(pawnCoordinates.substring(1))-1;
        Coordinates validatedCoordinates = new Coordinates(x,y);

        return validatedCoordinates;
    }
}
