package Common;

import Model.PositivePayRecordModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GeneralTool {

    public HashSet<PositivePayRecordModel> readAndCleanFile(String filePath){
        PositivePayRecordModel positivePayRecordModel;
        HashSet<PositivePayRecordModel> cleanList = new HashSet<>(); // Will remove duplicate
        try {
            Reader reader = new FileReader(filePath);
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for (CSVRecord record : parser) {
                positivePayRecordModel = buildCheckModel(record);
                cleanList.add(positivePayRecordModel);
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.printf("Error! Error!! ... See readAndCleanFile Method");
        }

        return cleanList;

    }

    private PositivePayRecordModel buildCheckModel(CSVRecord record) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        String bankDescription = record.get("Bank description");
        String accountNumber = record.get("Account number");
        String paymentNumber = record.get("Payment number");
        LocalDate postDate = LocalDate.parse(record.get("Post date"),formatter);
        String payeeName = record.get("Payee name").replace(",", ""); //Remove ","
        String amountString = record.get("Amount").replace("$", "").replace(",",""); //Remove "$" & ","
        double amount = Double.parseDouble(amountString);

        if (isNullOrEmpty(bankDescription) || isNullOrEmpty(accountNumber) ||
                isNullOrEmpty(paymentNumber) || postDate == null ||
                isNullOrEmpty(payeeName) || isNullOrEmpty(amountString)) {
            throw new IllegalArgumentException("Missing required field(s) in CSV record: " + record);
        }

        return new PositivePayRecordModel(bankDescription, accountNumber, amount, paymentNumber, postDate, payeeName);
    }

    public String createFileName(String filePath, HashSet<PositivePayRecordModel> cleanList) {
        ArrayList<PositivePayRecordModel> positivePayRecordModelList = new ArrayList<>(cleanList);
        Collections.sort(positivePayRecordModelList);
        String dateString = positivePayRecordModelList.getLast().getPostDate().toString();
        return filePath + dateString + ".txt";
    }

    public void exportFile(HashSet<PositivePayRecordModel> cleanList, String fileToUploadName) {

        try {BufferedWriter writer = new BufferedWriter(new FileWriter(fileToUploadName));
            {
                for(PositivePayRecordModel check : cleanList) {
                    String postDate = getDayString(check.getPostDate());
                    String amount = String.valueOf(check.getAmount());
                    String lineToWrite = check.getAccountNumber() +"," + amount + "," + check.getPaymentNumber() + "," + postDate + "," + check.getPayeeName();
                    writer.write(lineToWrite);
                    writer.newLine();
                    writer.flush();
//                    System.out.println(lineToWrite);
                }
                System.out.println("File export successfully: " + fileToUploadName);
            }
        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.out.println("Error! ... Please check exportFile Method in Common.GeneralTool Java");
        }
    }

    public void exportFile(HashMap<String, Double> summary, String[] totalRow, String fileToSaveSummary) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSaveSummary));
            for(String s: summary.keySet()){
                String line = String.format("%40s -%20.2f",s,summary.get(s));
                writer.write(line);
                writer.newLine();
                writer.flush();
            }
            writer.write(String.format("%40s -%20s","------------------------------------","-----------------"));
            writer.newLine();
            writer.flush();
            writer.write(String.format("%40s -%20s",totalRow[0],totalRow[1]));
            writer.newLine();
            writer.flush();
            System.out.println("File export successfully: " + fileToSaveSummary);

        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.out.println("Error! ... Please check exportFile Method in Common.GeneralTool Java");
        }
    }

    public String getDayString(LocalDate date){
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        int year = date.getYear();
        return String.format("%02d%02d%04d",month,day,year);
    }

    public HashMap<String, Double> createSummaryTable(HashSet<PositivePayRecordModel> cleanList){
        HashMap<String, Double> summary = new HashMap<>();
        for(PositivePayRecordModel check: cleanList){
            String bankDescription = check.getBankDescription();
            Double amount = check.getAmount();
            summary.put(bankDescription,summary.getOrDefault(bankDescription,0.0) + amount);
        }
        return summary;
    }

    public String[] summaryTotal(HashSet<PositivePayRecordModel> cleanList){
        String[] total = new String[2];
        Double sum = 0.0;
        for(PositivePayRecordModel positivePayRecordModel : cleanList){
            sum += positivePayRecordModel.getAmount();
        }
        total[0] = "Total";
        total[1] = String.valueOf(sum);
        return total;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

}
