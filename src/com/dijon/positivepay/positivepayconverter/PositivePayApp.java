package com.dijon.positivepay.positivepayconverter;

import com.dijon.positivepay.positivepayconverter.controller.PositivePayController;

public class PositivePayApp {

    public static void main(String[] args) {
        PositivePayController positivePayController = new PositivePayController();
        positivePayController.runPositivePayApp();
    }
}
