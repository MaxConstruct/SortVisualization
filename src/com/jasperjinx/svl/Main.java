package com.jasperjinx.svl;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        System.out.println("Starting...");

        var root = SceneInitialize.getScene();
        root.setStyle("-fx-background-color:Black;");
        root.getStylesheets().add("com/jasperjinx/svl/app.css");
        primaryStage.getIcons().add(new Image("com/jasperjinx/svl/icon.png"));
        primaryStage.setOnCloseRequest(actionEvent -> SceneInitialize.exit());
        primaryStage.setTitle("Sort Visualization");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false");
        launch(args);
    }
}
