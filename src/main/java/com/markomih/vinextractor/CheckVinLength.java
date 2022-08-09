package com.markomih.vinextractor;

public class CheckVinLength {

    public Boolean checkVin(String vin){
        if (vin.length() > 17 || vin.length() <17){
            return false;
        }
        else
            return true;
    }
}
