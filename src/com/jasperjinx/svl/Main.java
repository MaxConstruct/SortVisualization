package com.jasperjinx.svl;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    enum ExitOption {
        RESTART, SHUTDOWN
    }

    private static int i=0;

    private static ExitOption currentOption = ExitOption.SHUTDOWN;

    @Override
    public void start(Stage primaryStage) {
        startApp();
    }

    private void startApp() {
        Stage primaryStage = new Stage();
        System.out.println("Starting...");
        var root = SceneInitialize.getScene();
        root.setStyle("-fx-background-color:Black;");
        root.getStylesheets().add("com/jasperjinx/svl/app.css");
        primaryStage.getIcons().add(new Image("com/jasperjinx/svl/icon.png"));

        primaryStage.setOnCloseRequest(actionEvent -> {
            currentOption = SceneInitialize.exit();
            if(currentOption.equals(ExitOption.RESTART))
                startApp();
        });
        primaryStage.setTitle("Sort Visualization");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        System.out.println("Shutdown");
    }

    public static void main(String[] args) {
        System.setProperty("prism.lcdtext", "false");
        launch(args);

    }
}
