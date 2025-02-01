package Controller;

import View.PositivePayGUIView;

public class PositivePayController {

    //Windows pop up  when program open - ***Model View window***
    //User choose file - save in a variable
    //User choose folder path of where to save file - save on variable
    //Press run button - let user know program run successfully



    String filepath;
    String folderPath;
    PositivePayGUIView positivePayGUIView;

    public PositivePayController(){
        positivePayGUIView = new PositivePayGUIView();
    }

    public void runPositivePayApp(){
        positivePayGUIView.createAndShowGUI();
    }













}
