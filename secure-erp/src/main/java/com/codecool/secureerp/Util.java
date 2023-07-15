package com.codecool.secureerp;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Util {
    public final static Random random = new Random();
    public final static String lettersUppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public final static String lettersLowercase = "abcdefghijklmnopqrstuvwxyz";
    public final static String digits = "0123456789";

    public static int getRandomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static String randomChars(String chars, int amount) {
        char[] randomChars = new char[amount];
        for (int i = 0; i < amount; i++) {
            int randomIndex = getRandomInt(0, chars.length());
            randomChars[i] = chars.charAt(randomIndex);
        }
        return String.valueOf(randomChars);
    }

    public static String shuffle(String original) {
        char[] chars = original.toCharArray();
        for (int i = 0; i < original.length() - 1; i++) {
            int j = getRandomInt(i, chars.length);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return String.valueOf(chars);
    }

    public static String generateId() {
        int smallLettersAmount = 4;
        int capitalLettersAmount = 2;
        int digitsAmount = 2;
        int specialCharsAmount = 2;
        String allowedSpecialChars = "_+-!";
        String pool = randomChars(lettersUppercase, capitalLettersAmount)
                + randomChars(lettersLowercase, smallLettersAmount)
                + randomChars(digits, digitsAmount) + randomChars(allowedSpecialChars, specialCharsAmount);
        return shuffle(pool);
    }

    public static String generateUniqueId(String[] ids) {
        while (true) {
            boolean foundIdentical = false;
            String newId = generateId();
            for (String id : ids) {
                if (newId.equals(id)) {
                    foundIdentical = true;
                    break;
                }
            }
            if (!foundIdentical)
                return newId;
        }
    }

    public static boolean tryParseInt(String userInput) {
        try {
            Integer.parseInt(userInput);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Path getPath(String fileName) {
        File file = new File(fileName);
        return Paths.get(file.getAbsolutePath());
    }

    public static String [][] getTwoDimArrayFromList(List<String> list){
        String[][] arr= new String[list.size()][list.get(0).split(";").length];

        for (int i = 0; i <list.size() ; i++) {
            String [] tempArray=list.get(i).split(";");
            for (int j = 0; j < list.get(0).split(";").length; j++) {
                arr[i][j]=tempArray[j];
            }
        }
        return arr;
    }


}
