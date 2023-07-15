package com.codecool.secureerp.view;

import java.util.Scanner;
import java.util.StringJoiner;

public class TerminalView {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prints a single message to the terminal
     *
     * @param message information to be printed
     */
    public static void printMessage(String message) {System.out.println(message);
    }

    /**
     * Prints options in standard menu format like this:
     *      Main Menu:
     *      (1) Store manager
     *      (2) Human resources manager
     *      (3) Inventory manager
     *      (0) Exit program
     *
     * @param title the title of the menu (first row)
     * @param options array of all available options in menu as Strings
     */
    public static void printMenu(String title, String[] options) {
        printMessage(title);
        for (int i = 0; i < options.length; i++) {
            printMessage("  "+"("+i+") "+options[i]);
        }
    }

    /**
     * Prints out any type of non-tabular data
     *
     * @param result String with result to be printed
     * @param label label String
     */
    public static void printGeneralResults(String result, String label) {
        printMessage(  result + " " +label);
    }

    /*
     /--------------------------------\
     |   id   |   product  |   type   |
     |--------|------------|----------|
     |   0    |  Bazooka   | portable |
     |--------|------------|----------|
     |   1    | Sidewinder | missile  |
     \--------------------------------/
    */
    /**
     * Prints tabular data like above example
     *
     * @param table 2 dimensional array to be printed as table
     */
    public static void printTable(String[][] table) {

        int[] maxColumnWidths = getColumnWidths(table);
        int tableWidth = getTableWidth(maxColumnWidths),
            innerBorderLength = tableWidth + table[0].length - 1;

        StringJoiner content = new StringJoiner("");

        for (int i = 0; i < table.length; i++) {
            content.add(getDataRow(maxColumnWidths, table[i]) + "\n");
            if(i != table.length-1) content.add(getHorizontalDivider(maxColumnWidths, table[i]) + "\n");
        }

        System.out.println(
                getTopBorder(innerBorderLength) +
                content.toString() +
                getBottomBorder(innerBorderLength));
    }

    private static String getDataRow(int[] maxColumnWidths, String[] tableRow) {

        StringJoiner dataRow = new StringJoiner("|","|","|");

        for (int j = 0; j < tableRow.length; j++) {
            dataRow.add( getLeftSpaces(maxColumnWidths, j, tableRow) +
                        tableRow[j] +
                        getRightSpaces(tableRow[j].length(), maxColumnWidths[j]));
        }
        return dataRow.toString();
    }

    private static String getHorizontalDivider(int[] maxColumnWidths, String[] tableRow) {

        StringJoiner horizontalDivider = new StringJoiner("|","|","|");

        for (int j = 0; j < tableRow.length; j++) {
            horizontalDivider.add("-".repeat(maxColumnWidths[j]));
        }
        return horizontalDivider.toString();
    }

    private static String getLeftSpaces(int[] maxColumnWidths, int j, String[] table) {
        return " ".repeat((maxColumnWidths[j] - table[j].length()) / 2);
    }

    private static String getRightSpaces(int stringLength, int maxColumnWidth){
        return " ".repeat(maxColumnWidth - (((maxColumnWidth - stringLength) / 2) + stringLength));
    }

    private static String getTopBorder(int innerBorderLength) {
        return "/" + "-".repeat(innerBorderLength) + "\\" + "\n";
    }

    private static String getBottomBorder(int innerBorderLength) {
        return "\\" + "-".repeat(innerBorderLength) + "/";
    }

    public static int[] getColumnWidths(String[][] table){

        int[] maxColumnWidths = new int[table[0].length];

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j].length() > maxColumnWidths[j]) {
                    maxColumnWidths[j] = table[i][j].length() + 6;
                }
            }
        }
        return maxColumnWidths;
    }

    private static int getTableWidth(int[] maxColumnWidths) {

        int tableWidth = 0;

        for (int i = 0; i < maxColumnWidths.length; i++) {
            tableWidth += maxColumnWidths[i];
        }
        return tableWidth;
    }

    /**
     * Gets single String input from the user
     *
     * @param label the label before the user prompt
     * @return user input as String
     */
    public static String getInput(String label) {
        printMessage(label);
        String input=scanner.next();
        return input;

    }

    /**
     * Gets a list of String inputs from the user
     *
     * @param labels array of Strings with the labels to be displayed before each prompt
     * @return array of user inputs
     */
    public static String[] getInputs(String[] labels) {
        String [] inputArray=new String[labels.length];
        for (int i = 0; i < labels.length; i++) {
            printMessage(labels[i]);
            inputArray[i]=scanner.next();
        }
        return inputArray;
    }

    /**
     * Prints out error messages to terminal
     *
     * @param message String with error details
     */
    public static void printErrorMessage(String message) {
        throw new RuntimeException(message);
    }
}
