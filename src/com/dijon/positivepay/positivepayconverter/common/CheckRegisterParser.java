package com.dijon.positivepay.positivepayconverter.common;

import com.dijon.positivepay.positivepayconverter.model.PositivePayRecordModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CheckRegisterParser {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

    public List<PositivePayRecordModel> paresFileContent(String filePath){

        List<PositivePayRecordModel>positivePayRecordModelList = new ArrayList<>();
        PositivePayRecordModel positivePayRecordModel;

        try{Reader reader = new FileReader(filePath);
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for(CSVRecord record : parser){
                positivePayRecordModel = mapToPositivePayRecordModel(record);
                positivePayRecordModelList.add(positivePayRecordModel);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return positivePayRecordModelList;
    }

    public List<PositivePayRecordModel> removeDuplicates(List<PositivePayRecordModel> positivePayRecordModelList){
        Set<PositivePayRecordModel> positivePayRecordModelSet = new HashSet<>(positivePayRecordModelList);
        List<PositivePayRecordModel> positivePayList = new ArrayList<>(positivePayRecordModelSet);
        Collections.sort(positivePayList);
        return positivePayList;
    }

    private PositivePayRecordModel mapToPositivePayRecordModel(CSVRecord record){
        String bankDescription = record.get("Bank description");
        String accountNumber = record.get("Account number");
        String paymentNumber = record.get("Payment number");
        LocalDate postDate = LocalDate.parse(record.get("Post date"),dateTimeFormatter);
        String payeeName = record.get("Payee name").replace(",", ""); //Remove "Comma"
        String amountString = record.get("Amount").replace("$", "").replace(",",""); //Remove "Dollar Sign" & "Comma"
        double amount = Double.parseDouble(amountString);

        return new PositivePayRecordModel(bankDescription,accountNumber,amount,paymentNumber,postDate,payeeName);
    }
}
