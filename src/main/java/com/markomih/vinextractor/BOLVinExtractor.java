package com.markomih.vinextractor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static com.markomih.vinextractor.Controller.rectGroup;

public class BOLVinExtractor extends Application {
ToggleGroup tg = new ToggleGroup();
    @Override
    public void start(Stage stage) throws IOException {
        Controller controller = new Controller();
        FXMLLoader fxmlLoader = new FXMLLoader(BOLVinExtractor.class.getResource("layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        fxmlLoader.setController(controller);
        stage.setTitle("BOL VIN Extractor");
        stage.setResizable(false);
//        stage.setMaximized(true);

        Pane imagePane = (Pane) scene.lookup("#large_bol_pane");
        imagePane.getChildren().add(rectGroup);
//
//       Controller helloControllerClass = new Controller();

        stage.setScene(scene);
        stage.show();

        RadioButton rb1 = (RadioButton) scene.lookup("#bar_code_mode");
        RadioButton rb2 = (RadioButton) scene.lookup("#text_recongition_mode");
        Node myName = scene.lookup("#my_name");
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
        rb2.setSelected(true);

        AtomicReference<Double> opacity = new AtomicReference<>(0.01);
        ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(2);
        ScheduledFuture<?> schedule = executor.scheduleAtFixedRate(() -> {
            myName.setOpacity(opacity.get());
             opacity.set(opacity.get() + 0.01);

             if (opacity.get() > 1){
                 System.out.println("Excecutor is shutting down");
                 executor.shutdown();
             }

        }, 5, 100, TimeUnit.MILLISECONDS);

        executor.schedule(() -> schedule.cancel(false), 15, TimeUnit.SECONDS);
    //    executor.schedule(() -> executor.shutdown(),16,TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        launch();
    }



}

