package com.codecool.secureerp.dao;

import com.codecool.secureerp.model.CRMModel;
import com.codecool.secureerp.model.HRModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

public class HRDAO {
    private final static int ID_TABLE_INDEX = 0;
    private final static int NAME_TABLE_INDEX = 1;
    private final static int BIRTH_DATE_TABLE_INDEX = 2;
    private final static int DEPARTMENT_TABLE_INDEX = 3;
    private final static int CLEARANCE_TABLE_INDEX = 4;
    private final static String DATA_FILE = "src/main/resources/hr.csv";
    public static String[] headers = {"Id", "Name", "Date of birth", "Department", "Clearance"};

    public static List<HRModel> getHRList() {
        List<HRModel> HRList = new ArrayList<>();
        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(";");
                HRModel current = new HRModel(arr[ID_TABLE_INDEX],
                        arr[NAME_TABLE_INDEX],
                        LocalDate.parse(arr[BIRTH_DATE_TABLE_INDEX]),
                        arr[DEPARTMENT_TABLE_INDEX],
                        Integer.parseInt((arr[CLEARANCE_TABLE_INDEX])));
                HRList.add(current);
            }
            br.close();
            return HRList;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    public static String [][] getTwoDimArrayFromList() {

        List<HRModel> HRList= HRDAO.getHRList();
        String[][] arr = new String[HRList.size()+1][HRDAO.headers.length];
        arr[0]=HRDAO.headers;

        for (int i = 1; i < arr.length; i++) {
            arr[i] = HRList.get(i-1).toTableRow();
        }
        return arr;
    }
    public static String getHRString(List<HRModel> List){
       String newLine="";
       String fullContent = "";
       String birthDate="";
        for (HRModel hrData : List) {
             birthDate=hrData.getBirthDate().toString();
            newLine=  hrData.getId() +";"+ hrData.getName()+ ";" + birthDate + ";" + hrData.getDepartment() + ";" + hrData.getClearance() +"\n";
            fullContent+=newLine;
        }
        return fullContent;
    }
    public static void writeToDisc(List<HRModel> list){
        String content = getHRString(list);
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
