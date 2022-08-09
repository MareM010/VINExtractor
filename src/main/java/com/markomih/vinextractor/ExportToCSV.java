package com.markomih.vinextractor;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;

public class ExportToCSV {
    CsvReader csvReader = new CsvReader();
    private String reportName;
    private Object reportTruck;
    private Object reportVin;
    private Object bolName;
    private Object bolVin;

    public void exportExtractedData(ArrayList<BolLoadedData> list) throws IOException {
        int i = 0;
        Writer writer = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save extracted data to CSV");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV File", "*.csv"));
        File file = fileChooser.showSaveDialog(new Stage());
        try {
//            File file = new File("C:\\Users\\ghost\\Downloads\\Export1.csv");
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("Driver Name;BOL VIN Number\n");

            while (list.listIterator(i).hasNext()){
                String text =  csvReader.bolNameReturnMethod(list.get(i)) + ";" + csvReader.bolVinReturnMethod(list.get(i)) + "\n";
                writer.write(text);
                i++;
            }
            } catch (IOException e) {
            throw new RuntimeException(e);
        }
     finally {
            writer.flush();
            writer.close();
        }
    }

    public void exportAllData(ArrayList<BolLoadedData> list) throws IOException {
            Writer writer = null;

            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Table to CSV");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("CSV File", "*.csv"));
                File file = fileChooser.showSaveDialog(new Stage());
                writer = new BufferedWriter(new FileWriter(file));
                writer.write("Driver Name;Report Name;BOL VIN Number;Report VIN;Truck Number\n");
                for (BolLoadedData bolLoadedData : list) {

                    String text = bolLoadedData.getBolName() + ";" + bolLoadedData.getReportName() + ";" + bolLoadedData.getBolVin() + ";"
                            + bolLoadedData.getReportVin() + ";" + bolLoadedData.getReportTruck() + "\n";

                    writer.write(text);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {

                writer.flush();
                writer.close();
            }

    }

    }

