package com.dijon.positivepay.positivepayconverter.view;

import com.dijon.positivepay.positivepayconverter.model.PositivePayRecordModel;
import com.dijon.positivepay.positivepayconverter.service.PositivePayService;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PosPayView {
    private static GridBagConstraints gbConstraint;
    private static JFrame mainFrame;
    private static JLabel importLabel;
    private static JLabel exportFolderLabel;
    private static JPanel importAndExportPanel;
    private static JPanel runAndCancelPanel;
    private static JTextField importTextField;
    private static JTextField exportFolderTextField;
    private static JButton runButton;
    private static JButton cancelButton;
    private static JButton browseImportButton;
    private static JButton browseExportButton;
    private PositivePayService positivePayService;

    public PosPayView(PositivePayService positivePayService){
        gbConstraint = new GridBagConstraints();
        mainFrame = new JFrame();
        importLabel = new JLabel("Import Field");
        exportFolderLabel = new JLabel("Export Folder");
        importAndExportPanel = new JPanel();
        runAndCancelPanel = new JPanel();
        importTextField = new JTextField(20);
        exportFolderTextField = new JTextField(20);
        runButton = new JButton("Run");
        cancelButton = new JButton("Cancel");
        browseImportButton = new JButton("Browse");
        browseExportButton = new JButton("Browse");
        this.positivePayService = positivePayService;
    }

    public void createUserInterface(){
        createMainFrame();
        createImportAndExportPanel();
        createRunAndCancelPanel();
        addImportAndExportPanelToMainFrame(importAndExportPanel);
        addRunAndCancelPanelToMainFrame(runAndCancelPanel);
        addImportSession();
        addExportSession();
        addRunAndCancelButton();
        handleImportButtonEvent();
        handleExportButtonEvent();
        handleRunButtonEvent(positivePayService);
        handleCancelButtonEvent();
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
    }

    private void createMainFrame(){
        mainFrame.setTitle("Positive Pay Converter");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setSize(800,300);
    }

    private void createImportAndExportPanel(){
        importAndExportPanel.setLayout(new GridBagLayout());
        gbConstraint.insets= new Insets(10,10,10,10);
        gbConstraint.fill = GridBagConstraints.HORIZONTAL;
    }

    private void createRunAndCancelPanel(){
        runAndCancelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    }

    private void addImportAndExportPanelToMainFrame(JPanel importAndCancelPanel){
        mainFrame.add(importAndExportPanel,BorderLayout.CENTER);
    }

    private void addImportSession(){
        gbConstraint.gridx = 0;
        gbConstraint.gridy = 0;
        importAndExportPanel.add(importLabel, gbConstraint);

        gbConstraint.gridx = 1;
        gbConstraint.gridy = 0;
        importAndExportPanel.add(importTextField, gbConstraint);

        gbConstraint.gridx = 2;
        gbConstraint.gridy = 0;
        importAndExportPanel.add(browseImportButton, gbConstraint);
    }

    private void addExportSession(){
        gbConstraint.gridx = 0;
        gbConstraint.gridy = 1;
        importAndExportPanel.add(exportFolderLabel,gbConstraint);

        gbConstraint.gridx = 1;
        gbConstraint.gridy = 1;
        importAndExportPanel.add(exportFolderTextField, gbConstraint);

        gbConstraint.gridx = 2;
        gbConstraint.gridy = 1;
        importAndExportPanel.add(browseExportButton, gbConstraint);
    }

    private void addRunAndCancelButton(){
        runAndCancelPanel.add(runButton);
        runAndCancelPanel.add(cancelButton);
    }

    private void addRunAndCancelPanelToMainFrame(JPanel runAndCancelPanel){
        mainFrame.add(runAndCancelPanel,BorderLayout.SOUTH);
    }

    private void handleImportButtonEvent(){
        browseImportButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(mainFrame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                importTextField.setText(selectedFile.getAbsolutePath());
            }
        });

    }

    private void handleExportButtonEvent(){
        browseExportButton.addActionListener(e -> {
            JFileChooser folderChooser = new JFileChooser();
            folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = folderChooser.showOpenDialog(mainFrame);
            if(result == JFileChooser.APPROVE_OPTION){
                File selectedFile = folderChooser.getSelectedFile();
                exportFolderTextField.setText(selectedFile.getAbsolutePath());
            }
        });
    }

    private void handleRunButtonEvent(PositivePayService positivePayService){
        runButton.addActionListener(e -> {
            String importPath = importTextField.getText();
            String exportPath = exportFolderTextField.getText();
            String[] pathArray = positivePayService.convertListToBankPositivePayFormat(importPath,exportPath);
            String fileName1 = pathArray[0];
            String fileName2 = pathArray[1];
            String outCome = pathArray[2];

            if (importPath.isEmpty() || exportPath.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Both import and export paths are required.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if(outCome.equals("Failed")){
                JOptionPane.showMessageDialog(mainFrame, "Program failed. Please contact Tech Support.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(mainFrame,outCome + "\n"
                                +"\nFile Name 1: " + fileName1
                                +"\nFile Name 2: " + fileName2,
                        "Positive Pay Converter Message",
                        JOptionPane.INFORMATION_MESSAGE, null);
            }
        });
    }

    private void handleCancelButtonEvent(){
        cancelButton.addActionListener(e -> mainFrame.dispose());
    }
}
