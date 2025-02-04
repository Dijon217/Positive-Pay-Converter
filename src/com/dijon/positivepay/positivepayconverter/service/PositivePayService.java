package com.dijon.positivepay.positivepayconverter.service;

import com.dijon.positivepay.positivepayconverter.common.CheckRegisterParser;
import com.dijon.positivepay.positivepayconverter.common.OutputCheckRegisterWriter;
import com.dijon.positivepay.positivepayconverter.model.PositivePayRecordModel;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class PositivePayService {

    private List<PositivePayRecordModel> positivePayRecordModelList;
    private CheckRegisterParser checkRegisterParser = new CheckRegisterParser();
    private OutputCheckRegisterWriter outputCheckRegisterWriter = new OutputCheckRegisterWriter();
    private String bankFilePath;
    private String bankFileSummaryPath;
    private String bankFileName;
    private String bankFileSummaryName;

    public String[] convertListToBankPositivePayFormat(String filePath, String folderPath){

        String[] result = new String[3];
        result[2] = "Failed";

        positivePayRecordModelList = checkRegisterParser.paresFileContent(filePath);
        positivePayRecordModelList = checkRegisterParser.removeDuplicates(positivePayRecordModelList);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM-dd");
        String newestDate = dateTimeFormatter.format(positivePayRecordModelList.getLast().getPostDate());
        bankFileName = "PositivePay " + newestDate + " BankFileToUpload.txt";
        bankFileSummaryName = "PositivePay " + newestDate + " BankFileSummary.txt";
        bankFilePath = folderPath + "/" + bankFileName;
        bankFileSummaryPath = folderPath + "/" + bankFileSummaryName;

        outputCheckRegisterWriter.exportBankFile(positivePayRecordModelList, bankFilePath);
        outputCheckRegisterWriter.exportTotalByBankNameFile(positivePayRecordModelList, bankFileSummaryPath);

        result[0] = bankFileName;
        result[1] = bankFileSummaryName;
        result[2] = "File Created Successfully!";
        return result;
    }
}
