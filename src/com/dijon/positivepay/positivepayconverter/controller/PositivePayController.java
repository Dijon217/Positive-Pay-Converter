package com.dijon.positivepay.positivepayconverter.controller;

import com.dijon.positivepay.positivepayconverter.service.PositivePayService;
import com.dijon.positivepay.positivepayconverter.view.PositivePayGUIView;

public class PositivePayController {

    private PositivePayGUIView positivePayGUIView;
    private PositivePayService positivePayService;

    public PositivePayController(){
        positivePayService = new PositivePayService();
        positivePayGUIView = new PositivePayGUIView(positivePayService);
    }
    public void runPositivePayApp(){
//        positivePayGUIView.createAndShowGUI();
        positivePayGUIView.createUserInterface();
    }
}
