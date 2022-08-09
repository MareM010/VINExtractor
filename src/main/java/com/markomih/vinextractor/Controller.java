package com.markomih.vinextractor;

import com.opencsv.exceptions.CsvValidationException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.markomih.vinextractor.BarCodeClass.readMultipleBarcodeImageData;

public class Controller implements Initializable {

    Image image1;
    ImageTextExtraction textExtraction = new ImageTextExtraction();
    CheckVinLength checkVinLength = new CheckVinLength();
    CsvReader csvReader = new CsvReader();
    public static boolean doPreprocessing = true;
    String fileName;
    // public StackPane image_bol_stack_pane;
    BufferedImage croppedBufferedImage = null;
    Canvas imageCanvas = new Canvas();
    double startX, startY, endX, endY;
    Rectangle rect = null;
    public static Group rectGroup = new Group();
    Boolean rectIsBeingDrawn = false;
    public static ArrayList<BolLoadedData> vinList = new ArrayList<>();
    public static ArrayList<BolLoadedData> reportList = new ArrayList<>();
    public static ArrayList<BolLoadedData> reportListLastCopy = new ArrayList<>();

    @FXML
    protected RadioButton bar_code_mode;
    @FXML
    private Button export_all_button;

    @FXML
    private Button export_extracted_button;

    @FXML
    private Button copy_1;

    @FXML
    private Button delete_table;

    @FXML
    private Button copy_10;

    @FXML
    private Button copy_11;

    @FXML
    private Button copy_12;

    @FXML
    private Button copy_2;

    @FXML
    private Button copy_3;

    @FXML
    private Button copy_4;

    @FXML
    private Button copy_5;

    @FXML
    private Button copy_6;

    @FXML
    private Button copy_7;

    @FXML
    private Button copy_8;

    @FXML
    private Button copy_9;

    @FXML
    private Button delete1;

    @FXML
    private Button delete11;

    @FXML
    private Button delete110;

    @FXML
    private Button delete1101;

    @FXML
    private Button delete12;

    @FXML
    private Button delete13;

    @FXML
    private Button delete14;

    @FXML
    private Button delete15;

    @FXML
    private Button delete16;

    @FXML
    private Button delete17;

    @FXML
    private Button delete18;

    @FXML
    private Button delete19;

    @FXML
    private Button delete_append;

    @FXML
    private Button delete_all;

    @FXML
    private Button load_csv;

    @FXML
    private CheckBox rect_auto_erase_checkbox;

    @FXML
    private Text image_name;

    @FXML
    private ImageView image_bol;

    @FXML
    protected ImageView image_cropped;

    @FXML
    private Button load_image_button;

    @FXML
    private ProgressBar progress_bar;

    @FXML
    protected RadioButton text_recongition_mode;

    @FXML
    private TextField vin_1;

    @FXML
    private TextField vin_10;

    @FXML
    private TextField vin_11;

    @FXML
    private TextField vin_12;

    @FXML
    private TextField vin_2;

    @FXML
    private TextField vin_3;

    @FXML
    private TextField vin_4;

    @FXML
    private TextField vin_5;

    @FXML
    private TextField vin_6;

    @FXML
    private TextField vin_7;

    @FXML
    private TextField vin_8;

    @FXML
    private TextField vin_9;

    @FXML
    private TableView<BolLoadedData> table_view;

    @FXML
    private CheckBox vin_correction_checkbox;

    @FXML
    private ChoiceBox table_brand_choice_box;

    @FXML
    private CheckBox image_preprocessing;

    @FXML
    private Button erase_rects_button;

    @FXML
    private CheckBox limit_mouse_movement;

    @FXML
    void imagePreprocessing(ActionEvent event){
        if(image_preprocessing.isSelected()){
            doPreprocessing = true;
        }
        else {
            doPreprocessing = false;
        }
    }

    @FXML
    void deleteAll(ActionEvent event) {
        vin_1.setText("");
        vin_2.setText("");
        vin_3.setText("");
        vin_4.setText("");
        vin_5.setText("");
        vin_6.setText("");
        vin_7.setText("");
        vin_8.setText("");
        vin_9.setText("");
        vin_10.setText("");
        vin_11.setText("");
        vin_12.setText("");

        eraseRectangles();

    }

    @FXML
    void deleteTable(ActionEvent event) {
        vinList.clear();
        reportList.clear();
        reportListLastCopy.clear();
        csvReader.setLastReportLocation(null);
        csvReader.setLastReportLocationMazda(null);
        table_view.setItems(csvReader.observableReportList());
        csvReader.setVinListAsString(null);
    }

    @FXML
    void loadCsvReport(ActionEvent event) throws CsvValidationException, IOException {

        if (table_brand_choice_box.getValue().equals("All Units")){
            csvReader.readCsv();

            reportListLastCopy.clear();
            for (int g = 0; g < reportList.size(); g++) {
                reportListLastCopy.add(reportList.get(g));
            }
            table_view.setItems(csvReader.observableReportList());

               reportList.clear();
        }
        else {
            csvReader.readMazdaCsv();
            table_view.setItems(csvReader.observableReportList());

            reportListLastCopy.clear();
            for (int g = 0; g < reportList.size(); g++) {
                reportListLastCopy.add(reportList.get(g));
            }

                reportList.clear();
        }

    }

    @FXML
    void TextRecongitionMode(ActionEvent event) {
        vin_correction_checkbox.setDisable(false);
        image_preprocessing.setDisable(false);
    }

    @FXML
    void barCodeMode(ActionEvent event) {
        vin_correction_checkbox.setDisable(true);
        image_preprocessing.setDisable(true);
    }

    @FXML
    void exportAll(ActionEvent event) throws IOException {
        if (reportListLastCopy.size() > 0) {
            System.out.println(reportListLastCopy.size() + " velicina reportliste");
            ExportToCSV exportToCSV = new ExportToCSV();
            exportToCSV.exportAllData(reportListLastCopy);
        }
    }

    @FXML
    void exportExtractedData(ActionEvent event) throws IOException {


        if (vinList.size() > 0) {
            ExportToCSV exportToCSV = new ExportToCSV();
            exportToCSV.exportExtractedData(vinList);
        }
    }

    @FXML
    void copyText1(ActionEvent event) {
        StringSelection stringSelection = new StringSelection(vin_1.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @FXML
    void copyText10(ActionEvent event) {
        StringSelection stringSelection = new StringSelection(vin_10.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);

    }

    @FXML
    void copyText11(ActionEvent event) {
        StringSelection stringSelection = new StringSelection(vin_11.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @FXML
    void copyText12(ActionEvent event) {
        StringSelection stringSelection = new StringSelection(vin_12.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @FXML
    void copyText2(ActionEvent event) {
        StringSelection stringSelection = new StringSelection(vin_2.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @FXML
    void copyText3(ActionEvent event) {
        StringSelection stringSelection = new StringSelection(vin_3.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @FXML
    void copyText4(ActionEvent event) {
        StringSelection stringSelection = new StringSelection(vin_4.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @FXML
    void copyText5(ActionEvent event) {
        StringSelection stringSelection = new StringSelection(vin_5.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @FXML
    void copyText6(ActionEvent event) {
        StringSelection stringSelection = new StringSelection(vin_6.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @FXML
    void copyText7(ActionEvent event) {
        StringSelection stringSelection = new StringSelection(vin_7.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @FXML
    void copyText8(ActionEvent event) {
        StringSelection stringSelection = new StringSelection(vin_8.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @FXML
    void copyText9(ActionEvent event) {
        StringSelection stringSelection = new StringSelection(vin_9.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @FXML
    void deleteAndAppend(ActionEvent event) throws CsvValidationException, IOException {

        if (vin_1.getText().length() > 0) {
            vinList.add(new BolLoadedData(fileName, vin_1.getText()));

        }
        if (vin_2.getText().length() > 0) {
            vinList.add(new BolLoadedData(fileName, vin_2.getText()));

        }
        if (vin_3.getText().length() > 0) {
            vinList.add(new BolLoadedData(fileName, vin_3.getText()));

        }
        if (vin_4.getText().length() > 0) {
            vinList.add(new BolLoadedData(fileName, vin_4.getText()));

        }

        if (vin_5.getText().length() > 0) {
            vinList.add(new BolLoadedData(fileName, vin_5.getText()));

        }
        if (vin_6.getText().length() > 0) {
            vinList.add(new BolLoadedData(fileName, vin_6.getText()));

        }
        if (vin_7.getText().length() > 0) {
            vinList.add(new BolLoadedData(fileName, vin_7.getText()));

        }
        if (vin_8.getText().length() > 0) {
            vinList.add(new BolLoadedData(fileName, vin_8.getText()));

        }
        if (vin_9.getText().length() > 0) {
            vinList.add(new BolLoadedData(fileName, vin_9.getText()));

        }
        ObservableList<BolLoadedData> oListStavaka = FXCollections.observableArrayList(vinList);

            if(table_brand_choice_box.getValue().equals("All Units")){
                if (csvReader.getLastReportLocation() != null){
                    csvReader.setAppendExtractedDataMode(true);
                    csvReader.readCsv();
                    csvReader.setAppendExtractedDataMode(false);
                    table_view.setItems(csvReader.observableReportList());
                    reportListLastCopy.clear();

                    for (int g = 0; g < reportList.size(); g++) {
                        reportListLastCopy.add(reportList.get(g));
                    }

                    reportList.clear();
                }
                else {
                    table_view.setItems(oListStavaka);
                }
            }

            else if (csvReader.getLastReportLocationMazda() != null){
                csvReader.setAppendExtractedDataMode(true);
            csvReader.readMazdaCsv();
                csvReader.setAppendExtractedDataMode(false);
                table_view.setItems(csvReader.observableReportList());
                reportListLastCopy.clear();

                for (int g = 0; g < reportList.size(); g++) {
                    reportListLastCopy.add(reportList.get(g));
                }

                reportList.clear();
        }
            else {
                table_view.setItems(oListStavaka);
            }

        vin_1.setText("");
        vin_2.setText("");
        vin_3.setText("");
        vin_4.setText("");
        vin_5.setText("");
        vin_6.setText("");
        vin_7.setText("");
        vin_8.setText("");
        vin_9.setText("");
        vin_10.setText("");
        vin_11.setText("");
        vin_12.setText("");

        eraseRectangles();

    }

    @FXML
    void delete_1(ActionEvent event) {
        vin_1.setText("");

    }

    @FXML
    private TableColumn<BolLoadedData, String> report_vin_table;

    @FXML
    private TableColumn<BolLoadedData, String> bol_name_table;

    @FXML
    private TableColumn<BolLoadedData, String> bol_vin_table;

    @FXML
    private TableColumn<BolLoadedData, String> report_name_table;

    @FXML
    private TableColumn<BolLoadedData, String> report_truck_number;

    @FXML
    void delete_10(ActionEvent event) {
        vin_10.setText("");
    }

    @FXML
    void delete_11(ActionEvent event) {
        vin_11.setText("");
    }

    @FXML
    void delete_12(ActionEvent event) {
        vin_12.setText("");
    }

    @FXML
    void delete_2(ActionEvent event) {
        vin_2.setText("");
    }

    @FXML
    void delete_3(ActionEvent event) {
        vin_3.setText("");
    }

    @FXML
    void delete_4(ActionEvent event) {
        vin_4.setText("");
    }

    @FXML
    void delete_5(ActionEvent event) {
        vin_5.setText("");
    }

    @FXML
    void delete_6(ActionEvent event) {
        vin_6.setText("");
    }

    @FXML
    void delete_7(ActionEvent event) {
        vin_7.setText("");
    }

    @FXML
    void delete_8(ActionEvent event) {
        vin_8.setText("");
    }

    @FXML
    void delete_9(ActionEvent event) {
        vin_9.setText("");
    }

    @FXML
    void eraseRectsClicked(ActionEvent event) {
        eraseLastRectangle();
    }


    @FXML
    void imageDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (event.getDragboard().hasFiles()) {
            File fileToLoad = db.getFiles().get(0); //get files from dragboard
            image1 = new Image(fileToLoad.toURI().toString(), 576, 878, true, true);
            Rectangle2D resizeViewToImage = new Rectangle2D(0, 0, image1.getWidth(), image1.getHeight());
            image_bol.setViewport(resizeViewToImage);
            image_bol.setFitHeight(image1.getHeight());
            image_bol.setFitWidth(image1.getWidth());
            image_bol.setImage(image1);

            fileName = fileToLoad.getName().substring(0, fileToLoad.getName().length() - 4);
            image_name.setText("Image name: " + fileName);
        }
    }

    @FXML
    void imageDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.MOVE);
    }

    @FXML
    void imageCropMousePressed(MouseEvent event) {

        startX = event.getX();
        startY = event.getY();

        rect = new Rectangle();
        rect.setFill(Color.BLUE);
        rect.setOpacity(0.4);

        rectGroup.getChildren().add(rect);
        rectIsBeingDrawn = true;


    }

    void capturePane() {

        try {
            PixelReader reader = image1.getPixelReader();
            WritableImage newImage = new WritableImage(reader, (int) startX, (int) startY, (int) (endX - startX), (int) (endY - startY));

            croppedBufferedImage = SwingFXUtils.fromFXImage(newImage, croppedBufferedImage);
            image_cropped.setImage(newImage);

//            ImageIO.write(SwingFXUtils.fromFXImage(newImage, null),
//                    "png", new File("test.png"));


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Program crashed while making a screenshot");
        }
    }

    @FXML
    void imageCropMouseDragged(MouseEvent event) {

        progress_bar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        if (rectIsBeingDrawn == true) {
            endX = event.getX();
            endY = event.getY();
    if (limit_mouse_movement.isSelected()){
        restrictMouseMovement((int) image1.getWidth(), (int) image1.getHeight(), (int) endX, (int) endY);
    }
            rect.setX(startX);
            rect.setY(startY);
            rect.setWidth(endX - startX);
            rect.setHeight(endY - startY);

        if (rect.getWidth() > 0){

        }
            if (rect.getWidth() < 0) {
                rect.setWidth(-rect.getWidth());
                rect.setX(rect.getX() - rect.getWidth());
            }

            if (rect.getHeight() < 0) {
                rect.setHeight(-rect.getHeight());
                rect.setY(rect.getY() - rect.getHeight());
            }
        }

    }

    @FXML
    void imageCropMouseRelased(MouseEvent event) {

        rect.setFill(Color.GREEN);

        if (rectIsBeingDrawn == true) {
            rect.setFill(Color.GREEN);
        }

        rect = null;
        rectIsBeingDrawn = false;

        if (rect_auto_erase_checkbox.isSelected()){
            eraseLastRectangle();
        }


        resultToTextField();
        progress_bar.setProgress(0);
    }

    @FXML
    void loadImageButton(ActionEvent event) {


        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open BOL Picture");
        //        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("All Images", "*.*")
        );
        File file = chooser.showOpenDialog(new Stage());

        image1 = new Image(file.toURI().toString(), 576, 878, true, true);
        Rectangle2D resizeViewToImage = new Rectangle2D(0, 0, image1.getWidth(), image1.getHeight());
        image_bol.setViewport(resizeViewToImage);
        image_bol.setFitHeight(image1.getHeight());
        image_bol.setFitWidth(image1.getWidth());
        image_bol.setImage(image1);


        fileName = file.getName().substring(0, file.getName().length() - 4);
        image_name.setText("Image name: " + fileName);

        eraseRectangles();
        // setting canvas frame size depending on image width and height properties
        imageCanvas.setWidth(image1.getWidth());
        imageCanvas.setHeight(image1.getHeight());

    }

    // mode = true for bar code text recognition
    // mode = false for text extraction
    public void resultToTextField() {
        TextAutoCorrection textAutoCorrection = new TextAutoCorrection();
        int i = 0;
        double progress, p = 1;
        capturePane();
        if (bar_code_mode.isSelected()) {
            try {
                progress = 0.33;
                while (readMultipleBarcodeImageData(croppedBufferedImage).length > i) {
                    if (vin_1.getText().isEmpty())
                        vin_1.setText(String.valueOf(readMultipleBarcodeImageData(croppedBufferedImage)[i]));
                    else if (vin_2.getText().isEmpty())
                        vin_2.setText(String.valueOf(readMultipleBarcodeImageData(croppedBufferedImage)[i]));
                    else if (vin_3.getText().isEmpty())
                        vin_3.setText(String.valueOf(readMultipleBarcodeImageData(croppedBufferedImage)[i]));
                    else if (vin_4.getText().isEmpty())
                        vin_4.setText(String.valueOf(readMultipleBarcodeImageData(croppedBufferedImage)[i]));
                    else if (vin_5.getText().isEmpty())
                        vin_5.setText(String.valueOf(readMultipleBarcodeImageData(croppedBufferedImage)[i]));
                    else if (vin_6.getText().isEmpty())
                        vin_6.setText(String.valueOf(readMultipleBarcodeImageData(croppedBufferedImage)[i]));
                    else if (vin_7.getText().isEmpty())
                        vin_7.setText(String.valueOf(readMultipleBarcodeImageData(croppedBufferedImage)[i]));
                    else if (vin_8.getText().isEmpty())
                        vin_8.setText(String.valueOf(readMultipleBarcodeImageData(croppedBufferedImage)[i]));
                    else if (vin_9.getText().isEmpty())
                        vin_9.setText(String.valueOf(readMultipleBarcodeImageData(croppedBufferedImage)[i]));
                    else if (vin_10.getText().isEmpty())
                        vin_10.setText(String.valueOf(readMultipleBarcodeImageData(croppedBufferedImage)[i]));
                    else if (vin_11.getText().isEmpty())
                        vin_11.setText(String.valueOf(readMultipleBarcodeImageData(croppedBufferedImage)[i]));
                    else if (vin_12.getText().isEmpty())
                        vin_12.setText(String.valueOf(readMultipleBarcodeImageData(croppedBufferedImage)[i]));

                    //   progress = progress + 0.33;
                    i++;
                }

            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
        } else if (text_recongition_mode.isSelected()) {
            image_cropped.setImage(textExtraction.textImageExtraction(croppedBufferedImage));

            if (vin_correction_checkbox.isSelected()) {
                if (vin_1.getText().isEmpty())
                    vin_1.setText(textAutoCorrection.correctedString(textExtraction.returnResult()));
                else if (vin_2.getText().isEmpty())
                    vin_2.setText(textAutoCorrection.correctedString(textExtraction.returnResult()));
                else if (vin_3.getText().isEmpty())
                    vin_3.setText(textAutoCorrection.correctedString(textExtraction.returnResult()));
                else if (vin_4.getText().isEmpty())
                    vin_4.setText(textAutoCorrection.correctedString(textExtraction.returnResult()));
                else if (vin_5.getText().isEmpty())
                    vin_5.setText(textAutoCorrection.correctedString(textExtraction.returnResult()));
                else if (vin_6.getText().isEmpty())
                    vin_6.setText(textAutoCorrection.correctedString(textExtraction.returnResult()));
                else if (vin_7.getText().isEmpty())
                    vin_7.setText(textAutoCorrection.correctedString(textExtraction.returnResult()));
                else if (vin_8.getText().isEmpty())
                    vin_8.setText(textAutoCorrection.correctedString(textExtraction.returnResult()));
                else if (vin_9.getText().isEmpty())
                    vin_9.setText(textAutoCorrection.correctedString(textExtraction.returnResult()));
                else if (vin_10.getText().isEmpty())
                    vin_10.setText(textAutoCorrection.correctedString(textExtraction.returnResult()));
                else if (vin_11.getText().isEmpty())
                    vin_11.setText(textAutoCorrection.correctedString(textExtraction.returnResult()));
                else if (vin_12.getText().isEmpty())
                    vin_12.setText(textAutoCorrection.correctedString(textExtraction.returnResult()));

                croppedBufferedImage = null;

            } else {
                if (vin_1.getText().isEmpty())
                    vin_1.setText(textExtraction.returnResult());
                else if (vin_2.getText().isEmpty())
                    vin_2.setText(textExtraction.returnResult());
                else if (vin_3.getText().isEmpty())
                    vin_3.setText(textExtraction.returnResult());
                else if (vin_4.getText().isEmpty())
                    vin_4.setText(textExtraction.returnResult());
                else if (vin_5.getText().isEmpty())
                    vin_5.setText(textExtraction.returnResult());
                else if (vin_6.getText().isEmpty())
                    vin_6.setText(textExtraction.returnResult());
                else if (vin_7.getText().isEmpty())
                    vin_7.setText(textExtraction.returnResult());
                else if (vin_8.getText().isEmpty())
                    vin_8.setText(textExtraction.returnResult());
                else if (vin_9.getText().isEmpty())
                    vin_9.setText(textExtraction.returnResult());
                else if (vin_10.getText().isEmpty())
                    vin_10.setText(textExtraction.returnResult());
                else if (vin_11.getText().isEmpty())
                    vin_11.setText(textExtraction.returnResult());
                else if (vin_12.getText().isEmpty())
                    vin_12.setText(textExtraction.returnResult());

                croppedBufferedImage = null;

            }
        } else {
            System.out.println("Select mode please");
        }

    }

    void drawRectangles(Graphics graphics) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        bol_vin_table.setCellValueFactory(new PropertyValueFactory<BolLoadedData, String>("bolVin"));
        report_vin_table.setCellValueFactory(new PropertyValueFactory<BolLoadedData, String>("reportVin"));
        bol_name_table.setCellValueFactory(new PropertyValueFactory<BolLoadedData, String>("bolName"));
        report_truck_number.setCellValueFactory(new PropertyValueFactory<BolLoadedData, String>("reportTruck"));
        report_name_table.setCellValueFactory(new PropertyValueFactory<BolLoadedData, String>("reportName"));

        report_vin_table.setCellFactory(column -> new TextFieldTableCell<BolLoadedData, String>(new DefaultStringConverter()) {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null | empty)
                {
                    setStyle("");
                }
                else
                {

                    if (csvReader.getVinListAsString() != null && csvReader.getVinListAsString().length() >8) {
                        if (csvReader.getVinListAsString().contains(item.substring(item.length() - 8)))
                            setTextFill(Color.FORESTGREEN);
                    }
                    else if (item.length() != 17) setTextFill(Color.RED);
                    else setTextFill(Color.BLUE);
                }
            }
        });

        bol_vin_table.setCellFactory(column -> new TextFieldTableCell<BolLoadedData, String>(new DefaultStringConverter()) {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null | empty)
                {
                    setStyle("");
                }
                else if (item.length() != 17) setTextFill(Color.RED);
            }
        });

        vin_1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (checkVinLength.checkVin(vin_1.getText()) == false) {
                    vin_1.setStyle("-fx-text-fill: red;");
                } else
                    vin_1.setStyle("-fx-text-fill: green;");
            }
        });
        vin_2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (checkVinLength.checkVin(vin_2.getText()) == false) {
                    vin_2.setStyle("-fx-text-fill: red;");
                } else
                    vin_2.setStyle("-fx-text-fill: green;");
            }
        });
        vin_3.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (checkVinLength.checkVin(vin_3.getText()) == false) {
                    vin_3.setStyle("-fx-text-fill: red;");
                } else
                    vin_3.setStyle("-fx-text-fill: green;");
            }
        });
        vin_4.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (checkVinLength.checkVin(vin_4.getText()) == false) {
                    vin_4.setStyle("-fx-text-fill: red;");
                } else
                    vin_4.setStyle("-fx-text-fill: green;");
            }
        });
        vin_5.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (checkVinLength.checkVin(vin_5.getText()) == false) {
                    vin_5.setStyle("-fx-text-fill: red;");
                } else
                    vin_5.setStyle("-fx-text-fill: green;");
            }
        });
        vin_6.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (checkVinLength.checkVin(vin_6.getText()) == false) {
                    vin_6.setStyle("-fx-text-fill: red;");
                } else
                    vin_6.setStyle("-fx-text-fill: green;");
            }
        });
        vin_7.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (checkVinLength.checkVin(vin_7.getText()) == false) {
                    vin_7.setStyle("-fx-text-fill: red;");
                } else
                    vin_7.setStyle("-fx-text-fill: green;");
            }
        });
        vin_8.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (checkVinLength.checkVin(vin_8.getText()) == false) {
                    vin_8.setStyle("-fx-text-fill: red;");
                } else
                    vin_8.setStyle("-fx-text-fill: green;");
            }
        });
        vin_9.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (checkVinLength.checkVin(vin_9.getText()) == false) {
                    vin_9.setStyle("-fx-text-fill: red;");
                } else
                    vin_9.setStyle("-fx-text-fill: green;");
            }
        });
        vin_10.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (checkVinLength.checkVin(vin_10.getText()) == false) {
                    vin_10.setStyle("-fx-text-fill: red;");
                } else
                    vin_10.setStyle("-fx-text-fill: green;");
            }
        });
        vin_11.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (checkVinLength.checkVin(vin_11.getText()) == false) {
                    vin_11.setStyle("-fx-text-fill: red;");
                } else
                    vin_11.setStyle("-fx-text-fill: green;");
            }
        });
        vin_12.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (checkVinLength.checkVin(vin_12.getText()) == false) {
                    vin_12.setStyle("-fx-text-fill: red;");
                } else
                    vin_12.setStyle("-fx-text-fill: green;");
            }
        });
    }

    public void choiceBoxMethod(ActionEvent actionEvent) throws CsvValidationException, IOException {
        ObservableList<BolLoadedData> oListStavaka = FXCollections.observableArrayList(vinList);

        if (table_brand_choice_box.getValue().equals("All Units")){
            if (csvReader.getLastReportLocation() != null){
                csvReader.setAppendExtractedDataMode(true);
                csvReader.readCsv();
                csvReader.setAppendExtractedDataMode(false);
                table_view.setItems(csvReader.observableReportList());
                reportListLastCopy.clear();

            for (int g = 0; g < reportList.size(); g++) {
                reportListLastCopy.add(reportList.get(g));
            }

            reportList.clear();
        }
            else {
            table_view.setItems(oListStavaka);
            }
        }
        else if (csvReader.getLastReportLocationMazda() != null){
            csvReader.setAppendExtractedDataMode(true);
            csvReader.readMazdaCsv();
            csvReader.setAppendExtractedDataMode(false);
            table_view.setItems(csvReader.observableReportList());
            reportListLastCopy.clear();

            for (int g = 0; g < reportList.size(); g++) {
                reportListLastCopy.add(reportList.get(g));
            }

            reportList.clear();
        }
        else {
            table_view.setItems(oListStavaka);
        }
    }

    public void eraseRectangles(){
        int rectCount = rectGroup.getChildren().size();
        while(rectCount > 0){
        rectGroup.getChildren().remove(rectCount - 1);
        rectCount--;
        }
    }

    public void eraseLastRectangle(){
        int rectCount = rectGroup.getChildren().size();
        if (rectCount > 0){
            rectGroup.getChildren().remove(rectCount - 1);
        }
    }

    // restricts mouse movement to main image frame
    public void restrictMouseMovement(int width, int height, int x, int y) {

        if (x > width) {
            Platform.runLater(() -> {
                try {
                    Robot robot = new Robot();
                    robot.mouseMove(width +60, y+ 31);
                } catch (AWTException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        }
        if (y>height){

            Platform.runLater(() -> {
                try {
                    Robot robot = new Robot();
                    robot.mouseMove(x + 60, height + 31);
                } catch (AWTException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        }
        if (y<0){

            Platform.runLater(() -> {
                try {
                    Robot robot = new Robot();
                    robot.mouseMove(x + 60, 31);
                } catch (AWTException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        }

        if (x<0){

            Platform.runLater(() -> {
                try {
                    Robot robot = new Robot();
                    robot.mouseMove(60, y + 31);
                } catch (AWTException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        }
    }
}



