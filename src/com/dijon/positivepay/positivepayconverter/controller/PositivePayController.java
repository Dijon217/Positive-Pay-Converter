package com.dijon.positivepay.positivepayconverter.controller;

import com.dijon.positivepay.positivepayconverter.service.PositivePayService;
import com.dijon.positivepay.positivepayconverter.view.PosPayView;
import com.dijon.positivepay.positivepayconverter.view.PositivePayGUIView;

public class PositivePayController {

    private PositivePayGUIView positivePayGUIView;
    private PositivePayService positivePayService;
    private PosPayView posPayView;

    public PositivePayController(){
        positivePayGUIView = new PositivePayGUIView();
        positivePayService = new PositivePayService();
        posPayView = new PosPayView(positivePayService);
    }
    public void runPositivePayApp(){
//        positivePayGUIView.createAndShowGUI();
        posPayView.createUserInterface();
    }
}
