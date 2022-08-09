module com.markomih.vinextractor {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;
    requires com.opencsv;
    requires javafx.swing;
    requires tess4j;
    requires opencv;

    opens com.markomih.vinextractor to javafx.fxml;
    exports com.markomih.vinextractor;
}