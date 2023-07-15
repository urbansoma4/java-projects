package com.codecool.secureerp.controller;

import com.codecool.secureerp.Util;
import com.codecool.secureerp.dao.HRDAO;
import com.codecool.secureerp.dao.SalesDAO;
import com.codecool.secureerp.model.SalesModel;
import com.codecool.secureerp.view.TerminalView;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesController {

    public static void listTransactions() {
        TerminalView.printTable(SalesDAO.getTwoDimArrayFromList()); }

    public static void addTransaction() {
        List<SalesModel> SalesList= SalesDAO.getSalesList();

        String[] newTransactionData = TerminalView.getInputs(
                Arrays.copyOfRange(SalesDAO.headers,1,SalesDAO.headers.length-1));

        String[] existingTransactionIds = getExistingTransactionIds();

        SalesList.add(
                new SalesModel(
                        Util.generateUniqueId(existingTransactionIds),
                        newTransactionData[0],
                        newTransactionData[1],
                        tryParsePrice(newTransactionData[2]),
                        getTransactionDay()
        ));
        SalesDAO.writeToDisc(SalesList);
        TerminalView.printMessage("\n-----Transaction has been added-----\n");
    }

    public static void updateTransactions() {
        List<SalesModel> SalesList= SalesDAO.getSalesList();

        String transactionId = TerminalView.getInput(SalesDAO.headers[0]);

        for (int i = 0; i < SalesDAO.getSalesList().size(); i++) {
            if (SalesList.get(i).getId().equalsIgnoreCase(transactionId) ) {
                SalesList.get(i).setCustomerId(TerminalView.getInput(SalesDAO.headers[1]));
                SalesList.get(i).setProduct(TerminalView.getInput(SalesDAO.headers[2]));
                SalesList.get(i).setPrice(tryParsePrice(TerminalView.getInput(SalesDAO.headers[3])));
                SalesList.get(i).setTransactionDate(getTransactionDay());
                SalesDAO.writeToDisc(SalesList);
                return;
            }
        }
        TerminalView.printMessage("\n-----Transaction Id not found-----\n");
    }

    public static void deleteTransactions() {
        List<SalesModel> SalesList= SalesDAO.getSalesList();

        String transactionId = TerminalView.getInput(SalesDAO.headers[0]);

        for (int i = 0; i < SalesList.size(); i++) {
            if (SalesList.get(i).getId().equalsIgnoreCase(transactionId)) {
                SalesList.remove(i);
                SalesDAO.writeToDisc(SalesList);
                TerminalView.printMessage("\n-----Transaction has been removed from list-----\n");
                return;
            }
        }
        TerminalView.printMessage("\n-----Transaction Id not found-----\n");
    }

    public static void getBiggestRevenueTransaction() {
        List<SalesModel> list = SalesDAO.getSalesList();
        float biggestRevenue=list.get(0).getPrice(); LocalDate date = null;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getPrice()>biggestRevenue){
                biggestRevenue=list.get(i).getPrice();
                date=list.get(i).getTransactionDate();
            }
        }
        TerminalView.printGeneralResults(Float.toString(biggestRevenue), ",Date: " +date.toString());

    }

    public static void getBiggestRevenueProduct() {

        Map<String, Float> productsWithRevenue = new HashMap<>();

        List<SalesModel> SalesList= SalesDAO.getSalesList();
        Float oldValue, maxValue = 0f;
        String productWithMostRevenue = "";


        for (SalesModel transaction : SalesList ) {
            if (!productsWithRevenue.containsKey(transaction.getProduct())) {
                productsWithRevenue.put(transaction.getProduct(), transaction.getPrice());

            } else {
                oldValue = productsWithRevenue.get(transaction.getProduct());
                productsWithRevenue.put(transaction.getProduct(), oldValue + transaction.getPrice());
            }
        }

        for (Map.Entry<String, Float> product : productsWithRevenue.entrySet()) {
            if ( product.getValue() > maxValue ) maxValue = product.getValue();
        }

        for (Map.Entry<String, Float> product : productsWithRevenue.entrySet()) {
            if ( product.getValue() == maxValue ) productWithMostRevenue = product.getKey();
        }

        TerminalView.printMessage(
                "The product that made the biggest revenue altogether is: " +
                productWithMostRevenue + " " + maxValue + "\n");

    }

    public static void countTransactionsBetween() {
        LocalDate inputFirst= getRevenueDate();
        LocalDate inputSecond=getRevenueDate();
        LocalDate biggerDate= inputSecond.isAfter(inputFirst) ? inputSecond:inputFirst;
        LocalDate smallerDate= inputSecond.isBefore(inputFirst) ? inputSecond:inputFirst;
        List<SalesModel> list= SalesDAO.getSalesList();
        int counter=0;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getTransactionDate().isBefore(biggerDate)&&list.get(i).getTransactionDate().isAfter(smallerDate)){
                counter++;
            }
        }
        TerminalView.printGeneralResults(Integer.toString(counter) , " transactions found in this interval.");
    }

    public static void sumTransactionsBetween() {
        LocalDate inputFirst= getRevenueDate();
        LocalDate inputSecond=getRevenueDate();
        LocalDate biggerDate= inputSecond.isAfter(inputFirst) ? inputSecond:inputFirst;
        LocalDate smallerDate= inputSecond.isBefore(inputFirst) ? inputSecond:inputFirst;
        List<SalesModel> list= SalesDAO.getSalesList();
        float counter=0;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getTransactionDate().isBefore(biggerDate)&&list.get(i).getTransactionDate().isAfter(smallerDate)){
                counter+=list.get(i).getPrice();
            }
        }
        TerminalView.printGeneralResults(Float.toString(counter) , " is the sum of the revenue in this period.");
    }

    public static void runOperation(int option) {
        switch (option) {
            case 1: {
                listTransactions();
                break;
            }
            case 2: {
                addTransaction();
                break;
            }
            case 3: {
                updateTransactions();
                break;
            }
            case 4: {
                deleteTransactions();
                break;
            }
            case 5: {
                getBiggestRevenueTransaction();
                break;
            }
            case 6: {
                getBiggestRevenueProduct();
                break;
            }
            case 7: {
                countTransactionsBetween();
                break;
            }
            case 8: {
                sumTransactionsBetween();
                break;
            }
            case 0:
                return;
            default:
                throw new IllegalArgumentException("There is no such option");
        }
    }

    public static void displayMenu() {
        String[] options = {
                "Back to main menu",
                "List transactions",
                "Add new transaction",
                "Update transaction",
                "Remove transaction",
                "Get the transaction that made the biggest revenue",
                "Get the product that made the biggest revenue altogether",
                "Count number of transactions between",
                "Sum the price of transactions between"
        };

        TerminalView.printMenu("Sales", options);
    }

    public static void menu() {
        int operation = -1;
        while (operation != 0) {
            displayMenu();
            String userInput = TerminalView.getInput("Select an operation");
            if (Util.tryParseInt(userInput)) {
                operation = Integer.parseInt(userInput);
                runOperation(operation);
            } else {
                TerminalView.printErrorMessage("This is not a number");
            }
        }
    }
    private static String[] getExistingTransactionIds() {

        List<SalesModel> SalesList= SalesDAO.getSalesList();

        String[] existingTransactionIds = new String[SalesList.size()];

        for (int i = 0; i < SalesList.size(); i++) {
            existingTransactionIds[i] = SalesList.get(i).getId();
        }
        return existingTransactionIds;
    }
    private static LocalDate getTransactionDay() {

        LocalDate date;

        while (true) {
            String input = TerminalView.getInput(SalesDAO.headers[4]);
            if (isValidDate(input)) {
                date = LocalDate.parse(input);
                return date;
            }
        }
    }
    private static float tryParsePrice(String userInput) {
        try {
            return Float.parseFloat(userInput);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }
    private static boolean isValidDate(String userInput) {
        try {
            LocalDate date = LocalDate.parse(userInput);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
    private static LocalDate getRevenueDate() {
        LocalDate date;
        while (true) {
            String input = TerminalView.getInput(SalesDAO.headers[4]);
            if (isValidDate(input)) {
                date = LocalDate.parse(input);
                return date;
            }
        }
    }
}
