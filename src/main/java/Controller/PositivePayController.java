package Controller;

import View.PositivePayGUIView;

public class PositivePayController {

    PositivePayGUIView positivePayGUIView;

    public PositivePayController(){
        positivePayGUIView = new PositivePayGUIView();
    }

    public void runPositivePayApp(){
        positivePayGUIView.createAndShowGUI();
    }













}
