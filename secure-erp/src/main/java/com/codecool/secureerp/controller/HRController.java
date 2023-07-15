package com.codecool.secureerp.controller;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.codecool.secureerp.Util;
import com.codecool.secureerp.dao.HRDAO;
import com.codecool.secureerp.model.HRModel;
import com.codecool.secureerp.view.TerminalView;

public class HRController {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void listEmployees() {
        TerminalView.printTable(HRDAO.getTwoDimArrayFromList());
    }

    public static void addEmployee() {
        List<HRModel> HRList = HRDAO.getHRList();

        String[] existingEmployeeIds = getExistingEmployeeIds();

        HRList.add(
                new HRModel(
                        Util.generateUniqueId(existingEmployeeIds),
                        TerminalView.getInput(HRDAO.headers[1]),
                        getBirthDay(),
                        TerminalView.getInput(HRDAO.headers[3]),
                        tryParseClearance())
        );
        HRDAO.writeToDisc(HRList);
        TerminalView.printMessage("\n-----Customer has been added-----\n");
    }

    public static void updateEmployee() {
        List<HRModel> HRList = HRDAO.getHRList();

        String employeeId = TerminalView.getInput(HRDAO.headers[0]);

        for (int i = 0; i < HRDAO.getHRList().size(); i++) {
            if (HRList.get(i).getId().equalsIgnoreCase(employeeId)) {
                HRList.get(i).setName(TerminalView.getInput(HRDAO.headers[1]));
                HRList.get(i).setBirthDate(getBirthDay());
                HRList.get(i).setDepartment(TerminalView.getInput(HRDAO.headers[3]));
                HRList.get(i).setClearance(tryParseClearance());
                HRDAO.writeToDisc(HRList);
                return;
            }
        }
        TerminalView.printMessage("\n-----Employee Id not found-----\n");
    }

    public static void deleteEmployee() {
        List<HRModel> HRList = HRDAO.getHRList();

        String employeeId = TerminalView.getInput(HRDAO.headers[0]);

        for (int i = 0; i < HRList.size(); i++) {
            if (HRList.get(i).getId().equalsIgnoreCase(employeeId)) {
                HRList.remove(i);
                HRDAO.writeToDisc(HRList);
                TerminalView.printMessage("\n-----Employee has been removed from list-----\n");
                return;
            }
        }
        TerminalView.printMessage("\n-----Employee Id not found-----\n");
    }

    public static void getOldestAndYoungest() {
        TerminalView.printGeneralResults(getNameFromBirthDate(getYoungest()), "is the youngest Employee");
        TerminalView.printGeneralResults(getNameFromBirthDate(getOldest()), "is the Oldest Employee");


    }

    public static void getAverageAge() {
        TerminalView.printGeneralResults(calcAverage(createAgeList()), "is the Average age of the employees");
    }

    public static void nextBirthdays() {
        List<String> names = getBirthdaysInTwoWeek();
        if (!names.isEmpty()) {
            for (String s : names) {
                TerminalView.printMessage(s);
            }
        } else {
            TerminalView.printMessage("No birthdays in the next 2 weeks.");
        }
    }

    public static void countEmployeesWithClearance() {
        String input = TerminalView.getInput("Give me the clearance level: ");
        List<HRModel> list = HRDAO.getHRList();
        int numberOfEmployees = 0;
        for (int i = 0; i < list.size(); i++) {
            if (Integer.parseInt(input) <= list.get(i).getClearance()) {
                numberOfEmployees++;
            }
        }
        TerminalView.printGeneralResults(Integer.toString(numberOfEmployees), "Employees hes greater clearance level, than the input.");
    }

    public static void countEmployeesPerDepartment() {
        for (Map.Entry<String, Integer> entry : createEmployeesByDepartmentMap().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void runOperation(int option) {
        switch (option) {
            case 1: {
                listEmployees();
                break;
            }
            case 2: {
                addEmployee();
                break;
            }
            case 3: {
                updateEmployee();
                break;
            }
            case 4: {
                deleteEmployee();
                break;
            }
            case 5: {
                getOldestAndYoungest();
                break;
            }
            case 6: {
                getAverageAge();
                break;
            }
            case 7: {
                nextBirthdays();
                break;
            }
            case 8: {
                countEmployeesWithClearance();
                break;
            }
            case 9: {
                countEmployeesPerDepartment();
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
                "List employees",
                "Add new employee",
                "Update employee",
                "Remove employee",
                "Oldest and youngest employees",
                "Employees average age",
                "Employees with birthdays in the next two weeks",
                "Employees with clearance level",
                "Employee numbers by department"
        };
        TerminalView.printMenu("Human Resources", options);
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

    private static String[] getExistingEmployeeIds() {
        List<HRModel> list = HRDAO.getHRList();
        String[] existingEmployeeIds = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            existingEmployeeIds[i] = list.get(i).getId();
        }
        return existingEmployeeIds;
    }

    private static LocalDate getBirthDay() {
        LocalDate date;
        while (true) {
            String input = TerminalView.getInput(HRDAO.headers[2]);
            if (isValidDate(input)) {
                date = LocalDate.parse(input);
                return date;
            }
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

    private static int tryParseClearance() {
        while (true) {
            String input = TerminalView.getInput(HRDAO.headers[4]);
            if (Util.tryParseInt(input)) {
                return Integer.parseInt(input);
            }
        }
    }

    private static List<LocalDate> getBirthDayList() {
        List<LocalDate> list = new ArrayList<>();
        List<HRModel> hrList = HRDAO.getHRList();
        for (int i = 0; i < hrList.size(); i++) {
            list.add(hrList.get(i).getBirthDate());
        }
        return list;
    }

    private static LocalDate getOldest() {
        List<LocalDate> list = getBirthDayList();
        LocalDate oldest = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if (oldest.isAfter(list.get(i))) {
                oldest = list.get(i);
            }

        }
        return oldest;
    }

    private static LocalDate getYoungest() {
        List<LocalDate> list = getBirthDayList();
        LocalDate youngest = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if (youngest.isBefore(list.get(i))) {
                youngest = list.get(i);
            }
        }
        return youngest;
    }

    private static String getNameFromBirthDate(LocalDate date) {
        List<HRModel> hrList = HRDAO.getHRList();
        String name = "";
        for (int i = 0; i < hrList.size(); i++) {
            if (date.equals(hrList.get(i).getBirthDate())) {
                name = hrList.get(i).getName();
            }
        }
        return name;
    }

    private static List<Integer> createAgeList() {
        LocalDate today = LocalDate.now();
        List<Integer> list = new ArrayList<>();
        List<LocalDate> bdList = getBirthDayList();
        for (int i = 0; i < bdList.size(); i++) {
            LocalDate birthday = bdList.get(i);
            Period period = Period.between(birthday, today);
            list.add(period.getYears());
        }
        return list;
    }

    private static String calcAverage(List<Integer> list) {
        double sumOfYears = 0;
        double divisor = list.size();
        for (int i = 0; i < list.size(); i++) {
            sumOfYears += list.get(i);
        }
        double average = sumOfYears / divisor;
        return df.format(average);
    }

    private static Map<String, Integer> createEmployeesByDepartmentMap() {
        List<HRModel> list = HRDAO.getHRList();
        Map<String, Integer> employeeByDepartment = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            if (employeeByDepartment.containsKey(list.get(i).getDepartment())) {
                employeeByDepartment.put(list.get(i).getDepartment(), employeeByDepartment.get(list.get(i).getDepartment()) + 1);
            } else {
                employeeByDepartment.put(list.get(i).getDepartment(), 1);
            }
        }
        return employeeByDepartment;
    }

    private static List<String> getBirthdaysInTwoWeek() {
        LocalDate input = getBirthDay();
        List<LocalDate> list = getBirthDayList(); //bd dates list
        List<String> bdInTwoWeek = new ArrayList<>(); // return list
        LocalDate startDate = input;
        LocalDate endDate = startDate.plusDays(14);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isAfter(startDate) && list.get(i).isBefore(endDate)) {
                bdInTwoWeek.add(HRDAO.getHRList().get(i).getName());
            }
        }
        return bdInTwoWeek;
    }
}
