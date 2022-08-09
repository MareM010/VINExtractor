package com.markomih.vinextractor;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import nu.pattern.OpenCV;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.markomih.vinextractor.Controller.doPreprocessing;
import static org.opencv.imgproc.Imgproc.*;
import static org.opencv.imgproc.Imgproc.MORPH_ERODE;


public class ImageTextExtraction {
    BufferedImage bufImage;
    String result;
    Image imageForSetting;

    public static Mat imgToMat(BufferedImage image) {
        image = convertTo3ByteBGRType(image);
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
    }

    private static BufferedImage convertTo3ByteBGRType(BufferedImage image) {
        BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_3BYTE_BGR);
        convertedImage.getGraphics().drawImage(image, 0, 0, null);
        return convertedImage;
    }

    public Image textImageExtraction(BufferedImage image){

        // For proper execution of native libraries
        // Core.NATIVE_LIBRARY_NAME must be loaded
        // before calling any of the opencv methods
        try {
            OpenCV.loadLocally();


            if (doPreprocessing){
                Mat destinationGrayscale = new Mat();
                Mat destinationGaussianBlur = new Mat();
                Mat destinationBinarisation = new Mat();
                Mat destinationErosion = new Mat();

                Mat src = imgToMat(image);
         //       Mat src = Imgcodecs.imread("test.png");
                // for loading image to mat from local file system, not used now

                Size upscaleSize = new Size(src.cols()*16, src.rows()*16);


                Mat destinationSize = new Mat(src.rows(), src.cols(), src.type());
                Imgproc.resize(src, destinationSize, upscaleSize, 16, 16, Imgproc.INTER_CUBIC);

                Imgproc.cvtColor(destinationSize, destinationGrayscale, Imgproc.COLOR_RGBA2GRAY);

                Imgproc.GaussianBlur(destinationGrayscale,destinationGaussianBlur, new Size(3, 3),0.5,0.5, 0);
//            Imgproc.GaussianBlur(destinationGrayscale,destinationGaussianBlur,upscaleSize,0.5,0.5,0);

                Imgproc.threshold(destinationGaussianBlur, destinationBinarisation, 100,255, Imgproc.THRESH_BINARY+Imgproc.THRESH_OTSU);

                Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ERODE, new Size( 1,1));
                Mat element = Imgproc.getStructuringElement(MORPH_ERODE, new Size(2 * 2 + 1, 2 * 2 + 1),
                        new Point(1, 1));

                Imgproc.dilate(destinationBinarisation,destinationErosion,element);

                bufImage = (BufferedImage) HighGui.toBufferedImage(destinationBinarisation);


                imageForSetting = SwingFXUtils.toFXImage(bufImage,null);



            }
            else {
                Mat destinationGrayscale = new Mat();
                Mat src = imgToMat(image);

                Size upscaleSize = new Size(src.cols()*16, src.rows()*16);


                Mat destinationSize = new Mat(src.rows(), src.cols(), src.type());
                Imgproc.resize(src, destinationSize, upscaleSize, 16, 16, Imgproc.INTER_CUBIC);

                Imgproc.cvtColor(destinationSize, destinationGrayscale, Imgproc.COLOR_RGBA2GRAY);

                bufImage = (BufferedImage) HighGui.toBufferedImage(destinationGrayscale);


                imageForSetting = SwingFXUtils.toFXImage(bufImage,null);

            }

        }
        catch (Exception e){
            System.out.println("Error in OpenCV image processing. Exception: "+ e.getMessage());
        }

        Tesseract instance = new Tesseract();
        Tesseract1 instance1 = new Tesseract1();
        instance.setLanguage("eng");
        instance.setPageSegMode(ITessAPI.TessPageSegMode.PSM_SINGLE_LINE);
        instance.setOcrEngineMode(1);
        System.out.println("Page segmentation set mode complete");
        List<String> configs = Arrays.asList("tessdata\\configs\\bazaar");
        instance.setConfigs(configs);
        System.out.println("Dictionary should be disabled now");

        try {

            instance.setDatapath("tessdata");

            System.out.println(instance.doOCR(bufImage));
            result = instance.doOCR(bufImage);


        }
        catch (TesseractException e){
            System.out.println("Tesseract exception: "+ e.getMessage());
        }

        return imageForSetting;
    }

    protected String returnResult(){
        return result;
    }

}
