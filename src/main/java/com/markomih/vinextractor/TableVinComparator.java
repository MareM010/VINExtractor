package com.markomih.vinextractor;

public class TableVinComparator {

public boolean vinNumberMatchFound(String bolVin, String reportVin){

    // if report vin number contains last eith digits of a bol vin number
    if (reportVin.contains(bolVin.substring(bolVin.length() - 8))){
        return true;
    }
    else return false;
    }
}
