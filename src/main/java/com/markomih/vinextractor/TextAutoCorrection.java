package com.markomih.vinextractor;

public class TextAutoCorrection {
    public String correctedString(String stringForCorrection) {
    String vin = stringForCorrection;

   vin = vin.toUpperCase();

    if (vin.contains("( ")){
        vin = vin.replaceAll("\\(","");
    }
    if (vin.contains(" )")){
        vin = vin.replaceAll("\\)","");
    }
    if (vin.contains("C ")){
        vin = vin.replaceAll("\\(","");
    }
    if (vin.contains(" )")){
        vin = vin.replaceAll("\\)","");
    }
    if (vin.contains(" ")){
       vin = vin.replaceAll(" ", "");
    }
    if(vin.contains(":")){
        vin = vin.replaceAll(":","");
    }
    if(vin.contains(";")){
        vin = vin.replaceAll(";","");
    }
    if (vin.contains("(")){
      vin = vin.replaceAll("\\(","");
    }
    if (vin.contains(")")){
       vin = vin.replaceAll("\\)","");
    }
    if (vin.contains("}")){
       vin = vin.replaceAll("\\}","");
    }
    if (vin.contains("{")){
       vin = vin.replaceAll("\\{","");
    }
    if (vin.contains("[")){
       vin = vin.replaceAll("\\[","");
    }
    if (vin.contains("]")){
       vin = vin.replaceAll("]","");
    }
    if (vin.contains("!")){
       vin = vin.replaceAll("!","1");
    }
    if (vin.contains(".")){
      vin = vin.replaceAll("\\.","");
    }
    if (vin.contains("O")){
       vin = vin.replaceAll("O","0");
    }
    if (vin.contains("I")){
       vin = vin.replaceAll("I", "1");
    }
    if (vin.contains("Q")){
       vin = vin.replaceAll("Q","0");
    }
    if (vin.contains("|")){
       vin = vin.replaceAll("\\|","1");
    }
    if (vin.contains("/")){
        vin = vin.replaceAll("/", "7");
    }
    if (vin.contains(",")){
       vin = vin.replaceAll(",","");
    }
//    if (vin.indexOf("W") == 1){
//        vin = vin.replaceFirst("W", "VV");
//    }
    if(vin.indexOf("C") == 0){
        vin = vin.replaceFirst("C", "");
    }
    if(vin.indexOf("S") == 0){
        vin = vin.replaceFirst("S", "5");
    }
//    for (int i = 0; i <= 6;i++) {
//        switch (vin.indexOf(vin.length() - i)){
//            case 'G':
//                vin = vin.substring(0, i) + '6'
//                        + vin.substring(i + 1);
//            case 'B':
//                vin = vin.substring(0, i) + '3'
//                        + vin.substring(i + 1);
//        }
//    }

    return vin;

}}
