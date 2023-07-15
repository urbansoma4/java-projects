import java.util.ArrayList;
import java.util.Scanner;

public class Util {

    public static int get_int_UserInput() {

        int userInput = 1;

        do {
            if (userInput < 0 || userInput > 3) {
                System.out.println("Please choose a difficulty level between 1 and 3");}

            Scanner scanner = new Scanner(System.in);
            userInput = scanner.nextInt();

        } while (userInput < 1 || userInput > 3);

        return  userInput;
    }

    public static String get_String_UserInput() {
        String guess = "";
        do {
            Scanner userInput = new Scanner(System.in);
            System.out.print("Please enter a letter or type quit!");
            guess = userInput.nextLine();
        }while(guess.equals(""));
        return guess;
    }


    public static boolean isUserInputQuit(String userInput){

        boolean boolQuit;

        if (userInput.equals("quit")) {
            boolQuit = true;
        } else {
            boolQuit = false;
        }
        return boolQuit;
    }

    public static boolean isValidLetter(String userChar){

        String hunLetters = "AaÁáBbCcDdEeÉéFfGgHhIiÍíJjKkLlMmNnOoÓóÖöŐőPpQqRrSsTtUuÚúÜüŰűVvWwXxYyZz ";
        boolean boolIsTheCharacterIsArrayInitial;

        if (hunLetters.indexOf(userChar)>-1) {
            boolIsTheCharacterIsArrayInitial = true;
        } else {
            boolIsTheCharacterIsArrayInitial = false;
        }
        return boolIsTheCharacterIsArrayInitial;
    }

    public static String returnValidUserInput() {

        String validUserInput = "";

        boolean validInput = false;
        while ( !validInput ) {

            validUserInput = get_String_UserInput();

            if(isUserInputQuit(validUserInput)){ break;}

            if(validUserInput.length() > 1){
                System.out.println("Please type:  quit  if you want to leave the game");
                continue;
            }
            if(!Util.isValidLetter(validUserInput)){
                System.out.println("That's not a letter!");
                continue;
            }

            validInput = true;
        }
        return validUserInput;
    }


    public static String returnNewUserInput(ArrayList<String> userArray, ArrayList<String> badLettersArray) {

        String newUserInput = "";

        boolean newInput = false;
        while ( !newInput ) {

            newUserInput = returnValidUserInput();

            if (GameArrays.checkArrayForLetter(userArray, newUserInput)) {
                System.out.println("The letter has already been guessed and it is in the word.");
                continue;
            }

            if (GameArrays.checkArrayForLetter(badLettersArray, newUserInput)) {
                System.out.println("The letter has already been guessed and it is NOT in the word.  List of wrong letters\t" + badLettersArray);
                continue;
            }

            newInput = true;
        }
        return newUserInput;
    }

    public static void showGameStatistics(int Lives, ArrayList<String> userArray, ArrayList<String> livesArray) {

        String wordLives = (Lives > 1) ? "Lives" : "Live";

        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tYou have " + Lives + " " + wordLives + ".\t" + livesArray.toString().replace(",",""));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tThe word: " + userArray.toString().replace(",",""));
    }
}
