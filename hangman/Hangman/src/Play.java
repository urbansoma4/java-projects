import java.util.ArrayList;

public class Play {

    public static void play() {

        GameMenu.showMenu();
        int userChoice = Util.get_int_UserInput();
        int Lives = setLives(userChoice);
        ArrayList<ArrayList<String>> gameArrays = GameArrays.returnGameArrays(userChoice, Lives);
        String gameStatus = "playing";
        gameStatus = runGame(gameStatus, Lives, gameArrays);
        endGame(gameStatus, gameArrays.get(0));
    }

    public static int setLives(int userChoice) {

        switch (userChoice) {
            case 1: {                       // easy
                return 10;
            }
            case 2: {                       // medium
                return 7;
            }
            case 3: {                       // hard
                return 3;
            }
            default: {
                return 0;
            }
        }
    }

    public static String runGame(String gameStatus, int Lives, ArrayList<ArrayList<String>> gameArrays) {

        ArrayList<String> initialArray = gameArrays.get(0),
                          userArray = gameArrays.get(1),
                          badLettersArray = gameArrays.get(2),
                          livesArray = gameArrays.get(3);

        while ( gameStatus == "playing" ) {

            Util.showGameStatistics(Lives, userArray,livesArray);
            String newUserInput = Util.returnNewUserInput(userArray, badLettersArray);

            if (newUserInput.equals("quit")) {
                return "quit";
            }
            if (!GameArrays.checkArrayForLetter( initialArray, newUserInput)) {
                if (--Lives < 1) {return "lost";}
                badLettersArray.add(newUserInput);
                livesArray.remove(livesArray.size()-1);
                System.out.println("The letter is not in the word.\t\t\t\t\t\t\t\t\tList of wrong letters\t" + badLettersArray + "\n");
                continue;
            }
            GameArrays.showLettersInUserArray(initialArray, userArray, newUserInput);
            if (initialArray.equals(userArray)){
                return "won";
            }
        }
        return "";
    }

    public static void endGame(String gameStatus, ArrayList<String> initialArray) {

        switch (gameStatus) {
            case "won": {
                System.out.println("Congratulations, you have won! :-)");
                System.out.println("The solution of the game is: " + initialArray);
            }
            break;
            case "lost": {
                System.out.println("You have Lost, don't you worry just play again. Good Luck!");
                System.out.println("The solution of the game is: " + initialArray);
            }
            break;
            case "quit": {
                System.out.println("You have choosen to quit the game, we look forward seeing You playing again");
            }
            break;
            default: {
                //do nothing
            }
        }
    }
}

