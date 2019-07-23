package com.jasperjinx.svl;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;

public class Main extends Application {

    private static final String CSS_RELATIVE_PATH = "src/com/jasperjinx/svl/resources/css/";

    @Override
    public void start(Stage primaryStage) {

        System.out.println("Starting...");

        var root = SceneInitialize.getScene();
        root.setStyle("-fx-background-color:Black;");
        var file = new String[] {
                getStyleSheet("icon.css"),
                getStyleSheet("rect.css"),
                getStyleSheet("slider.css")
        };

        root.getStylesheets().addAll(file);
        primaryStage.setOnCloseRequest(actionEvent -> SceneInitialize.exit());
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private String getStyleSheet(String name) {
        try {
            var stylesheetFile = new File(CSS_RELATIVE_PATH+name);
            return stylesheetFile.toURI().toURL().toString();
        } catch ( MalformedURLException e ) {
            return null;
        }
    }


    public static void main(String[] args) {
        //System.setProperty("prism.lcdtext", "false");
        launch(args);
    }
}
