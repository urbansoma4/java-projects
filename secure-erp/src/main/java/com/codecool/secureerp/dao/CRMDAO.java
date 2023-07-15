package com.codecool.secureerp.dao;

import com.codecool.secureerp.model.CRMModel;
import com.codecool.secureerp.model.HRModel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class CRMDAO {
    private final static int ID_TABLE_INDEX = 0;
    private final static int NAME_TABLE_INDEX = 1;
    private final static int EMAIL_TABLE_INDEX = 2;
    private final static int SUBSCRIBED_TABLE_INDEX = 3;
    private final static String DATA_FILE = "src/main/resources/crm.csv";
    public static String[] headers = {"Id", "Name", "Email", "Subscribed"};

    public static List<CRMModel> getCRMList() {

        try {
            String line;
            List<CRMModel> CRMList = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(DATA_FILE));

            while ((line = bufferedReader.readLine()) != null) {

             CRMModel currentCRM = new CRMModel(
                        line.split(";")[ID_TABLE_INDEX],
                        line.split(";")[NAME_TABLE_INDEX],
                        line.split(";")[EMAIL_TABLE_INDEX],
                     Integer.parseInt(line.split(";")[SUBSCRIBED_TABLE_INDEX]) == 1
                );
                CRMList.add(currentCRM);
            }
            bufferedReader.close();
            return CRMList;

        } catch (IOException e) {
            System.out.println("Hiba");
        }
        return null;
    }

    public static String [][] getTwoDimArrayFromList() {

        List<CRMModel> CRMList= CRMDAO.getCRMList();
        String[][] arr = new String[CRMList.size()+1][CRMDAO.headers.length];
        arr[0]=CRMDAO.headers;

        for (int i = 1; i < arr.length; i++) {
            arr[i] = CRMList.get(i-1).toTableRow();
        }
        return arr;
    }
    private static String getContentAsString(List<CRMModel> list) {
        String subcribed = "";
        String newLine = "";
        String fullContent = "";

        for (CRMModel line :  list ) {
            subcribed = (line.isSubscribed()) ? "1" : "0";
            newLine = line.getId() + ";" + line.getName() + ";" + line.getEmail() + ";" + subcribed + "\n";
            fullContent += newLine;
        }
        return fullContent;
    }

    public static void writeToDisc(List<CRMModel> list){
        String content = getContentAsString(list);
        writeToFile(content);
    }

    private static void writeToFile(String content) {
        try {
            Files.write(Path.of(DATA_FILE), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}