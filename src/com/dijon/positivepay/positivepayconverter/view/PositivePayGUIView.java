package com.dijon.positivepay.positivepayconverter.view;

import com.dijon.positivepay.positivepayconverter.service.PositivePayService;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PositivePayGUIView {

    PositivePayService positivePayService;
    String filePath;
    String folderPath;

    public PositivePayGUIView(){
        positivePayService = new PositivePayService();
    }

    public void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("Positive Pay Converter"); // Icon
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800, 300);
        frame.setLayout(new BorderLayout());

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add file chooser for import
        JLabel importLabel = new JLabel("Import File:");
        JTextField importField = new JTextField(20);
        JButton importButton = new JButton("Browse");

        importButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                importField.setText(selectedFile.getAbsolutePath());
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(importLabel, gbc);

        gbc.gridx = 1;
        panel.add(importField, gbc);

        gbc.gridx = 2;
        panel.add(importButton, gbc);

        // Add file chooser for export folder
        JLabel exportLabel = new JLabel("Export Folder:");
        JTextField exportField = new JTextField(20);
        JButton exportButton = new JButton("Browse");

        exportButton.addActionListener(e -> {
            JFileChooser folderChooser = new JFileChooser();
            folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = folderChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = folderChooser.getSelectedFile(); //*********
                exportField.setText(selectedFolder.getAbsolutePath()); //*********
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(exportLabel, gbc);

        gbc.gridx = 1;
        panel.add(exportField, gbc);

        gbc.gridx = 2;
        panel.add(exportButton, gbc);

        // Add Run and Cancel buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton runButton = new JButton("Run");
        JButton cancelButton = new JButton("Cancel");

        runButton.addActionListener(e -> {
            String importPath = importField.getText();
            String exportPath = exportField.getText();
            System.out.println(importField.getText() + " ******");
            String[] pathArray = positivePayService.convertListToBankPositivePayFormat(importPath,exportPath);
            String fileName1 = pathArray[0];
            String fileName2 = pathArray[1];
            String outCome = pathArray[2];

            if (importPath.isEmpty() || exportPath.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Both import and export paths are required.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if(outCome.equals("Failed")){
                JOptionPane.showMessageDialog(frame, "Program failed. Please contact Tech Support.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(frame,outCome + "\n"
                        +"\nFile Name 1: " + fileName1
                        +"\nFile Name 2: " + fileName2,
                        "Positive Pay Converter Message",
                        JOptionPane.INFORMATION_MESSAGE, null);
            }
        });

        cancelButton.addActionListener(e -> frame.dispose());

        buttonPanel.add(runButton);
        buttonPanel.add(cancelButton);

        // Add components to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Display the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
