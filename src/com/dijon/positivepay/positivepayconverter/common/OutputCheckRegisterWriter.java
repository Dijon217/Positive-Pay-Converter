package com.dijon.positivepay.positivepayconverter.common;

import com.dijon.positivepay.positivepayconverter.model.PositivePayRecordModel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OutputCheckRegisterWriter {

    public void exportBankFile(List<PositivePayRecordModel> positivePayRecordModelList, String bankFilePath){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String lineToWrite;

        try{BufferedWriter writer = new BufferedWriter(new FileWriter(bankFilePath));
            for(PositivePayRecordModel positivePayRecordModel : positivePayRecordModelList){
                lineToWrite = positivePayRecordModel.getAccountNumber() + ","
                        + positivePayRecordModel.getAmount() + ","
                        + positivePayRecordModel.getPaymentNumber() + ","
                        + dateTimeFormatter.format(positivePayRecordModel.getPostDate()) + ","
                        + positivePayRecordModel.getPayeeName();
                writer.write(lineToWrite);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportTotalByBankNameFile(List<PositivePayRecordModel> positivePayRecordModelList, String bankSummaryFilepath){

        HashMap<String,Double> totalByBankName = new HashMap<>();
        Double total = 0.0;
        String lineToWrite;
        String bankName;
        Double amount;

        for(PositivePayRecordModel positivePayRecordModel : positivePayRecordModelList){
            bankName = positivePayRecordModel.getBankDescription();
            amount = positivePayRecordModel.getAmount();
            total += amount;
            totalByBankName.put(bankName,totalByBankName.getOrDefault(bankName,0.0) + amount);
        }

        try{BufferedWriter writer = new BufferedWriter(new FileWriter(bankSummaryFilepath));
            for(String positivePayRecordModel : totalByBankName.keySet()){
                lineToWrite = String.format("%40s -%20.2f", positivePayRecordModel, totalByBankName.get(positivePayRecordModel));
                writer.write(lineToWrite);
                writer.newLine();
                writer.flush();
            }
            writer.write(String.format("%40s -%20s","------------------------------------","-----------------"));
            writer.newLine();
            writer.write(String.format("%40s -%20.2f","Total",total));
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
