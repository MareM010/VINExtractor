package com.markomih.vinextractor;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.markomih.vinextractor.Controller.*;

public class CsvReader {
    TableVinComparator tableVinComparator = new TableVinComparator();
    private File file = null;
    public void setVinListAsString(String vinListAsString) {
        this.vinListAsString = vinListAsString;
    }

    String vinListAsString = null;

    public String getLastReportLocation() {
        return LastReportLocation;
    }

    public void setLastReportLocation(String lastReportLocation) {
        LastReportLocation = lastReportLocation;
    }

    public String getLastReportLocationMazda() {
        return lastReportLocationMazda;
    }

    public void setLastReportLocationMazda(String lastReportLocationMazda) {
        this.lastReportLocationMazda = lastReportLocationMazda;
    }

    private String LastReportLocation = null;

    private String lastReportLocationMazda = null;


    private boolean appendExtractedDataMode;
    private String name;

    public String getName() {
        return name;
    }

    public String getTruck() {
        return truck;
    }

    public String getVin() {
        return vin;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    private String truck;
    private String vin;

//    public void showVinListElements(BolLoadedData bolLoadedData){
//        System.out.println(bolLoadedData.getBolName() + " " + bolLoadedData.getBolVin());
//    }

    public String bolNameReturnMethod(BolLoadedData bolLoadedData){
        return bolLoadedData.getBolName();
    }
    public String bolVinReturnMethod(BolLoadedData bolLoadedData){
        return bolLoadedData.getBolVin();
    }
    public String reportVinReturnMethod(BolLoadedData bolLoadedData){
        return bolLoadedData.getReportVin();
    }
    public String reportNameReturnMethod(BolLoadedData bolLoadedData){
        return bolLoadedData.getReportName();
    }
    public String reportTruckReturnMethod(BolLoadedData bolLoadedData){
        return bolLoadedData.getReportTruck();
    }

    public void readCsv() throws IOException, CsvValidationException {

        if (appendExtractedDataMode == false) {

            int i = 0;
            int m = 0;
            String[] nextLine;
            String[] values = new String[0];

            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open All Units Report to Load");
//        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV", "*.csv")
            );

            // ovo sam probao da sredim to sto mi se pri ucitavanju novog vin broja sa bola brise report iz tabele pa moram ponovo da ucitavbam

                file = chooser.showOpenDialog(new Stage());
                System.out.println("Rezim ucitavanja reporta obicno");
            LastReportLocation = file.getAbsolutePath();

            CSVReader reader = new CSVReader(new FileReader(file.getAbsolutePath()));

            reader.skip(1);

            // iterating trough previously loaded arrayList that contains bol vin and driver name(image name)
//        while (vinList.listIterator(m).hasNext()){
//            System.out.println(bolNameReturnMethod(vinList.get(m)) + " " + bolVinReturnMethod(vinList.get(m)));
//            m++;
//        }


            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                String line = nextLine[0];
                if (line.contains("has been updated")) {
                    //    System.out.println(line); // test print whole unseparated line, one by one
                    values = line.split(";");

                    System.out.println(values[5]); // name
                    System.out.println("VIN From message(not always accurate) " + values[8].substring(values[8].length() - 17)); // vin extracted from message
                    System.out.println("VIN From dedicated column " + values[4]);
                    System.out.println(values[2]); //  truck number
                    try {
                        reportList.add(new BolLoadedData(values[5], values[2], values[4], bolNameReturnMethod(vinList.get(i)), bolVinReturnMethod(vinList.get(i))));
                        if (tableVinComparator.vinNumberMatchFound(values[4],bolVinReturnMethod(vinList.get(i)))){
                         // here goes cod for coloring that cell


                        }
                    } catch (IndexOutOfBoundsException e) {
//
                        reportList.add(new BolLoadedData(values[5], values[2], values[4]));

                    }

                    // appending to new ArrayList used for populating whole table, by passing 5 arguments to a constructor of BolLoadedData class
                    i++;
                    System.out.println(vinList.size());
                    System.out.println(reportList.size());
                }
            }


            // if vinList list is bigger, the previous loop won't load more elements than the size of reportList
            // to Bypass this, i added a loop that checks the difference and prints the rest of the first list anyway, even if there's no value to compare it to.
            if (vinList.size() > reportList.size()) {
                int difference = vinList.size() - reportList.size();
                while (difference != 0) {
                    reportList.add(new BolLoadedData(bolNameReturnMethod(vinList.get(vinList.size() - difference)), bolVinReturnMethod(vinList.get(vinList.size() - difference))));
                    difference--;
                }
            }


            // code for iterating trough all values and printing them, don't need it so far because i need few columns only(VIN number, truck number, name)
//            while (g<values.length){
//                System.out.println("Values of array "+ values[g]);
//                g++;
//            }
//            g = 0;

        }

        else {

            int i = 0;
            int m = 0;
            String[] nextLine;
            String[] values = new String[0];

            // ovo sam probao da sredim to sto mi se pri ucitavanju novog vin broja sa bola brise report iz tabele pa moram ponovo da ucitavbam

            CSVReader reader = new CSVReader(new FileReader(LastReportLocation));

            reader.skip(1);

            // iterating trough previously loaded arrayList that contains bol vin and driver name(image name)
//        while (vinList.listIterator(m).hasNext()){
//            System.out.println(bolNameReturnMethod(vinList.get(m)) + " " + bolVinReturnMethod(vinList.get(m)));
//            m++;
//        }


            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                String line = nextLine[0];
                if (line.contains("has been updated")) {
                    //    System.out.println(line); // test print whole unseparated line, one by one
                    values = line.split(";");

                    System.out.println(values[5]); // name
                    System.out.println("VIN From message(not always accurate) " + values[8].substring(values[8].length() - 17)); // vin extracted from message
                    System.out.println("VIN From dedicated column " + values[4]);
                    System.out.println(values[2]); //  truck number
                    try {
                        reportList.add(new BolLoadedData(values[5], values[2], values[4], bolNameReturnMethod(vinList.get(i)), bolVinReturnMethod(vinList.get(i))));
                    } catch (IndexOutOfBoundsException e) {
//
                        reportList.add(new BolLoadedData(values[5], values[2], values[4]));

                    }

                    // appending to new ArrayList used for populating whole table, by passing 5 arguments to a constructor of BolLoadedData class
                    i++;
                    System.out.println(vinList.size());
                    System.out.println(reportList.size());
                }
            }


            // if vinList list is bigger, the previous loop won't load more elements than the size of reportList
            // to Bypass this, i added a loop that checks the difference and prints the rest of the first list anyway, even if there's no value to compare it to.
            if (vinList.size() > reportList.size()) {
                int difference = vinList.size() - reportList.size();
                while (difference != 0) {
                    reportList.add(new BolLoadedData(bolNameReturnMethod(vinList.get(vinList.size() - difference)), bolVinReturnMethod(vinList.get(vinList.size() - difference))));
                    difference--;
                }
            }


        }
    }


    public void readMazdaCsv() throws IOException, CsvValidationException {

        if (appendExtractedDataMode == false) {

            int i = 0;
            int m = 0;
            String[] nextLine;
            String[] values = new String[0];

            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open Mazda Report to Load");
//        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV", "*.csv")
            );

            // ovo sam probao da sredim to sto mi se pri ucitavanju novog vin broja sa bola brise report iz tabele pa moram ponovo da ucitavbam

            file = chooser.showOpenDialog(new Stage());
            System.out.println("Rezim ucitavanja reporta obicno");
            lastReportLocationMazda = file.getAbsolutePath();

            CSVReader reader = new CSVReader(new FileReader(file.getAbsolutePath()));

            reader.skip(5);

            // iterating trough previously loaded arrayList that contains bol vin and driver name(image name)
//        while (vinList.listIterator(m).hasNext()){
//            System.out.println(bolNameReturnMethod(vinList.get(m)) + " " + bolVinReturnMethod(vinList.get(m)));
//            m++;
//        }


            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                String line = nextLine[0];

                    //    System.out.println(line); // test print whole unseparated line, one by one
                    values = line.split(";");

                    System.out.println("VIN From dedicated column " + values[1]);
                    System.out.println("Truck number" + values[7]); //  truck number
                    try {
                        reportList.add(new BolLoadedData(values[7], values[1], bolNameReturnMethod(vinList.get(i)), bolVinReturnMethod(vinList.get(i)),true));
                    } catch (IndexOutOfBoundsException e) {
                        reportList.add(new BolLoadedData(values[7], values[1], true));
                    }

                    i++;
                    System.out.println(vinList.size());
                    System.out.println(reportList.size());

            }


            // if vinList list is bigger, the previous loop won't load more elements than the size of reportList
            // to Bypass this, i added a loop that checks the difference and prints the rest of the first list anyway, even if there's no value to compare it to.
            if (vinList.size() > reportList.size()) {
                int difference = vinList.size() - reportList.size();
                while (difference != 0) {
                    reportList.add(new BolLoadedData(bolNameReturnMethod(vinList.get(vinList.size() - difference)), bolVinReturnMethod(vinList.get(vinList.size() - difference))));
                    difference--;
                }
            }


            // code for iterating trough all values and printing them, don't need it so far because i need few columns only(VIN number, truck number, name)
//            while (g<values.length){
//                System.out.println("Values of array "+ values[g]);
//                g++;
//            }
//            g = 0;

        }

        else {

            int i = 0;
            int m = 0;
            String[] nextLine;
            String[] values = new String[0];

            // ovo sam probao da sredim to sto mi se pri ucitavanju novog vin broja sa bola brise report iz tabele pa moram ponovo da ucitavbam

            CSVReader reader = new CSVReader(new FileReader(lastReportLocationMazda));

            reader.skip(5);

            // iterating trough previously loaded arrayList that contains bol vin and driver name(image name)
//        while (vinList.listIterator(m).hasNext()){
//            System.out.println(bolNameReturnMethod(vinList.get(m)) + " " + bolVinReturnMethod(vinList.get(m)));
//            m++;
//        }



            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                String line = nextLine[0];

                //    System.out.println(line); // test print whole unseparated line, one by one
                values = line.split(";");

                System.out.println("VIN From dedicated column " + values[1]);
                System.out.println("Truck number" + values[7]); //  truck number
                try {
                    reportList.add(new BolLoadedData(values[7], values[1], bolNameReturnMethod(vinList.get(i)), bolVinReturnMethod(vinList.get(i)),true));
                } catch (IndexOutOfBoundsException e) {
                    reportList.add(new BolLoadedData(values[7], values[1],true));
                }
                    i++;
                    System.out.println(vinList.size());
                    System.out.println(reportList.size());
                }
            }


            // if vinList list is bigger, the previous loop won't load more elements than the size of reportList
            // to Bypass this, i added a loop that checks the difference and prints the rest of the first list anyway, even if there's no value to compare it to.
            if (vinList.size() > reportList.size()) {
                int difference = vinList.size() - reportList.size();
                while (difference != 0) {
                    reportList.add(new BolLoadedData(bolNameReturnMethod(vinList.get(vinList.size() - difference)), bolVinReturnMethod(vinList.get(vinList.size() - difference))));
                    difference--;
                }
            }


        }




    public boolean isAppendExtractedDataMode() {
        return appendExtractedDataMode;
    }

    public boolean vinsAreMatching(){
        return true;
    }

    public void setAppendExtractedDataMode(boolean appendExtractedDataMode) {
        this.appendExtractedDataMode = appendExtractedDataMode;
    }

    // iterating trough previously loaded arrayList that contains bol vin and driver name(image name)

    public String getVinListAsString() {
        int m = 0;

        while (vinList.listIterator(m).hasNext()) {
         vinListAsString = vinListAsString + " " + bolNameReturnMethod(vinList.get(m)) + " " + bolVinReturnMethod(vinList.get(m));
            m++;
        }
        return vinListAsString;
    }

    public ObservableList<BolLoadedData> observableReportList(){
        ObservableList<BolLoadedData> oListStavaka = FXCollections.observableArrayList(reportList);
        return oListStavaka;
    }

}
