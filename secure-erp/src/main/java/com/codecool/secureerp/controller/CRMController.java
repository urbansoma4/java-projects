package com.codecool.secureerp.controller;

import com.codecool.secureerp.Util;
import com.codecool.secureerp.dao.CRMDAO;
import com.codecool.secureerp.model.CRMModel;
import com.codecool.secureerp.view.TerminalView;

import java.util.*;

public class CRMController {
    public static void listCustomers() { TerminalView.printTable(CRMDAO.getTwoDimArrayFromList()); }

    public static void addCustomer() {

        List<CRMModel> CRMList= CRMDAO.getCRMList();

        String[] newCustomerData = TerminalView.getInputs(
                Arrays.copyOfRange(CRMDAO.headers,1,CRMDAO.headers.length-1));

        String[] existingCustomerIds = getExistingCustomerIds();

        CRMList.add(
                new CRMModel(
                        Util.generateUniqueId(existingCustomerIds),
                        newCustomerData[0],
                        newCustomerData[1],
                        getSubscribedValue()
        ));
            CRMDAO.writeToDisc(CRMList);
        TerminalView.printMessage("\n-----Customer has been added-----\n");
    }

    public static void updateCustomers() {

        List<CRMModel> CRMList= CRMDAO.getCRMList();

        String customerId = TerminalView.getInput(CRMDAO.headers[0]);

        for (int i = 0; i < CRMDAO.getCRMList().size(); i++) {
            if ( CRMList.get(i).getId().equalsIgnoreCase(customerId) ) {
                CRMList.get(i).setName(TerminalView.getInput(CRMDAO.headers[1]));
                CRMList.get(i).setEmail(TerminalView.getInput(CRMDAO.headers[2]));
                CRMList.get(i).setSubscribed(getSubscribedValue());
                CRMDAO.writeToDisc(CRMList);
                return;
            }
        }
        TerminalView.printMessage("\n-----Customer Id not found-----\n");
    }

    public static void deleteCustomers() {

        List<CRMModel> CRMList= CRMDAO.getCRMList();

        String customerId = TerminalView.getInput(CRMDAO.headers[0]);

        for (int i = 0; i < CRMList.size(); i++) {
            if (CRMList.get(i).getId().equalsIgnoreCase(customerId)) {
                CRMList.remove(i);
                CRMDAO.writeToDisc(CRMList);
                TerminalView.printMessage("\n-----Customer has been removed from list-----\n");
                return;
            }
        }
        TerminalView.printMessage("\n-----Customer Id not found-----\n");
    }

    public static void getSubscribedEmails() {

        List<CRMModel> CRMList= CRMDAO.getCRMList();

        for (int i = 0; i < CRMList.size(); i++) {
            if (CRMList.get(i).isSubscribed()) {
                TerminalView.printGeneralResults(Boolean.toString(CRMList.get(i).isSubscribed()),
                                                    CRMList.get(i).getEmail());
            }
        }
    }

    public static void runOperation(int option) {
        switch (option) {
            case 1: {
                listCustomers();
                break;
            }
            case 2: {
                addCustomer();
                break;
            }
            case 3: {
                updateCustomers();
                break;
            }
            case 4: {
                deleteCustomers();
                break;
            }
            case 5: {
                getSubscribedEmails();
                break;
            }
            case 0:
                return;
            default:
                throw new IllegalArgumentException("There is no such option.");
        }
    }

    public static void displayMenu() {
        String[] options = {
                "Back to main menu",
                "List customers",
                "Add new customer",
                "Update customer",
                "Remove customer",
                "Subscribed customer emails"
        };
        TerminalView.printMenu("Customer Relationship", options);
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

    private static String[] getExistingCustomerIds() {

        List<CRMModel> CRMList= CRMDAO.getCRMList();

        String[] existingCustomerIds = new String[CRMList.size()];

        for (int i = 0; i < CRMList.size(); i++) {
            existingCustomerIds[i] = CRMList.get(i).getId();
        }
        return existingCustomerIds;
    }

    private static boolean getSubscribedValue() {

        String userInput;

        while ( true ) {
            userInput = TerminalView.getInput(CRMDAO.headers[3]);
            if ( Util.tryParseInt(userInput) ) {
                if ( Integer.parseInt(userInput) == 0 || Integer.parseInt(userInput ) == 1) {
                    break;
                }
            }
        }
        return Integer.parseInt(userInput) == 1;
    }
}