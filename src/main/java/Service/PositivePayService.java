package Service;

import Common.GeneralTool;
import Model.PositivePayRecordModel;

import java.util.HashMap;
import java.util.HashSet;

public class PositivePayService {

    String fileToUploadName;
    String fileToSaveSummary;
    String[] cleanListSummaryTotal;
    HashMap<String,Double> cleanListSummary;
    HashSet<PositivePayRecordModel> cleanList;
    GeneralTool generalTool = new GeneralTool();

    public String[] convertListPositivePayFormat(String filePath, String folderPath){

        String[] outcome = new String[3];
        outcome[2] = "Failed";

        cleanList = generalTool.readAndCleanFile(filePath);

        fileToUploadName = generalTool.createFileName("PositivePay-Upload-",cleanList); //Add file path
        fileToSaveSummary = generalTool.createFileName("PositivePay-Summary-",cleanList); //Add file path

        cleanListSummary = generalTool.createSummaryTable(cleanList);
        cleanListSummaryTotal = generalTool.summaryTotal(cleanList);

        generalTool.exportFile(cleanList,folderPath +  "/" +fileToUploadName);
        generalTool.exportFile(cleanListSummary,cleanListSummaryTotal,folderPath +  "/" + fileToSaveSummary);

        outcome[0] = fileToUploadName;
        outcome[1] = fileToSaveSummary;
        outcome[2] = "File Created Successfully!";
        return outcome;
    }

//    public String getFilePath() {
//        return filePath;
//    }
//
//    public void setFilePath(String filePath) {
//        this.filePath = filePath;
//    }
//
//    public String getFolderPath() {
//        return folderPath;
//    }
//
//    public void setFolderPath(String folderPath) {
//        this.folderPath = folderPath;
//    }

}
