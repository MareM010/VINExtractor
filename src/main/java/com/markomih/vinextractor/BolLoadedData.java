package com.markomih.vinextractor;

import java.util.ArrayList;

public class BolLoadedData {

    private String bolName, reportName, reportTruck, bolVin, reportVin;
    boolean mazdaMode;
    private ArrayList<BolLoadedData> bolLoadedArray = new ArrayList<BolLoadedData>();
    int i = 0;

    public BolLoadedData(String bolName, String bolVin){
        this.bolVin = bolVin;
        this.bolName = bolName;
    }
    public BolLoadedData(String reportTruck, String reportVin, boolean mazdaMode){
        this.reportVin = reportVin;
        this.reportTruck = reportTruck;
        this.mazdaMode = mazdaMode;
    }

    public BolLoadedData(String reportName, String reportTruck, String reportVin){
        this.reportName = reportName;
        this.reportTruck = reportTruck;
        this.reportVin = reportVin;
    }

    public BolLoadedData(String bolName, String reportName, String bolVin, String reportVin){
        this.bolName = bolName;
        this.bolVin = bolVin;
        this.reportVin = reportVin;
        this.reportName = reportName;
    }
    public BolLoadedData(String reportTruck, String reportVin, String bolName, String bolVin, boolean mazdaMode){
        this.reportTruck = reportTruck;
        this.bolName = bolName;
        this.bolVin = bolVin;
        this.reportVin = reportVin;
        this.mazdaMode = mazdaMode;
    }

    public BolLoadedData(String reportName, String reportTruck, String reportVin, String bolName, String bolVin){
        this.bolName = bolName;
        this.bolVin = bolVin;
        this.reportVin = reportVin;
        this.reportName = reportName;
        this.reportTruck = reportTruck;
    }

    public BolLoadedData(String reportName, String reportTruck, String reportVin, ArrayList<BolLoadedData> bolLoadedArray){
        this.reportName = reportName;
        this.reportTruck = reportTruck;
        this.reportVin = reportVin;
        this.bolLoadedArray = bolLoadedArray;
    }
    public BolLoadedData(String reportName, String reportTruck, String reportVin, ArrayList<BolLoadedData> bolLoadedArray, int i){
        this.reportName = reportName;
        this.reportTruck = reportTruck;
        this.reportVin = reportVin;
        this.bolLoadedArray = bolLoadedArray;
        this.i = i;
    }




    public String getBolName() {
        return bolName;
    }

    public void setBolName(String driverName) {
        this.bolName = driverName;
    }

    public String getBolVin() {
        return bolVin;
    }

    public void setBolVin(String bolVin) {
        this.bolVin = bolVin;
    }

    public String getReportVin() {
        return reportVin;
    }

    public void setReportVin(String reportVin) {
        this.reportVin = reportVin;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportTruck() {
        return reportTruck;
    }

    public void setReportTruck(String reportTruck) {
        this.reportTruck = reportTruck;
    }

    public String get(int ind) {
        return String.valueOf(ind);
    }
}
