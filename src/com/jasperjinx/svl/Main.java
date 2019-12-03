package com.jasperjinx.svl;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    enum ExitOption {
        RESTART, SHUTDOWN
    }

    private static ExitOption currentOption = ExitOption.SHUTDOWN;

    @Override
    public void start(Stage primaryStage) {
        do {
            startApp();
        } while (SceneInitialize.currentOption.equals(ExitOption.RESTART));
    }

    private void startApp() {

        Stage primaryStage = new Stage();
        System.out.println("Starting...");
        var initScene = new SceneInitialize();
        var root = initScene.getScene();
        root.setStyle("-fx-background-color:Black;");
        root.getStylesheets().add("com/jasperjinx/svl/app.css");
        primaryStage.getIcons().add(new Image("com/jasperjinx/svl/icon.png"));

        primaryStage.setOnCloseRequest(actionEvent -> {
            currentOption = initScene.exit();
            if(currentOption.equals(ExitOption.RESTART))
                startApp();
        });
        primaryStage.setTitle("Sort Visualization");
        primaryStage.setScene(new Scene(root));
        primaryStage.showAndWait();
        System.out.println("ShutDown....");

    }

    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false");
        launch(args);

    }
}
