package com.codecool.secureerp.dao;

import com.codecool.secureerp.model.CRMModel;
import com.codecool.secureerp.model.SalesModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalesDAO {
    private final static int ID_TABLE_INDEX = 0;

    private final static int CUSTOMER_ID_TABLE_INDEX = 1;

    private final static int PRODUCT_TABLE_INDEX = 2;

    private final static int PRICE_TABLE_INDEX = 3;

    private final static int TRANSACTION_DATE_TABLE_INDEX = 4;

    private final static String DATA_FILE = "src/main/resources/sales.csv";

    public static String[] headers = {"Id", "Customer Id", "Product", "Price", "Transaction Date"};

    public static List<SalesModel> getSalesList() {
        List<SalesModel> SalesList = new ArrayList<>();
        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(";");
                SalesModel current = new SalesModel(arr[ID_TABLE_INDEX],
                        arr[CUSTOMER_ID_TABLE_INDEX],
                        arr[PRODUCT_TABLE_INDEX],
                        Float.parseFloat(arr[PRICE_TABLE_INDEX]),
                        LocalDate.parse((arr[TRANSACTION_DATE_TABLE_INDEX])));
                SalesList.add(current);
            }
            br.close();
            return SalesList;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    public static String [][] getTwoDimArrayFromList() {

        List<SalesModel> SalesList= SalesDAO.getSalesList();

        String[][] arr = new String[SalesList.size()+1][SalesDAO.headers.length];
        arr[0]=SalesDAO.headers;

        for (int i = 1; i < arr.length; i++) {
            arr[i] = SalesList.get(i-1).toTableRow();
        }
        return arr;
    }
    public static String getSalesString(List<SalesModel> List){
        String newLine="";
        String fullContent = "";
        String transacDate="";
        for (SalesModel salesData : List) {
            transacDate=salesData.getTransactionDate().toString();
            newLine=  salesData.getId() +";"+ salesData.getCustomerId()+ ";" + salesData.getProduct() + ";" + salesData.getPrice() + ";" + transacDate +"\n";
            fullContent+=newLine;
        }
        return fullContent;
    }
    public static void writeToDisc(List<SalesModel> list){
        String content = getSalesString(list);
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
