package com.markomih.vinextractor;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.GenericMultipleBarcodeReader;
import com.google.zxing.multi.MultipleBarcodeReader;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

public class BarCodeClass {
    static Result[] readMultipleBarcodeImageData(BufferedImage croppedImage) throws NotFoundException {//
        int i = 0;
        BinaryBitmap bb = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(croppedImage)));
        MultipleBarcodeReader mbReader = new GenericMultipleBarcodeReader(new MultiFormatReader());
        Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        Result[] currentBarCodeResult = mbReader.decodeMultiple(bb, hints);//every result represent a bar code
        return currentBarCodeResult;
    }

    // reads single barcode from image, not in use so far
//    private static String readSingleBarcodeImageData(BufferedImage croppedImage) throws NotFoundException {
//        BinaryBitmap bb = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(croppedImage)));
//        MultipleBarcodeReader mbReader = new GenericMultipleBarcodeReader(new MultiFormatReader());
//        Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
//        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
//        Result[] currentBarCodeResult = mbReader.decodeMultiple(bb, hints);
//        return currentBarCodeResult[0].getText();
//    }

}
