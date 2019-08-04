package com.jasperjinx.svl;

import com.jasperjinx.svl.components.ComponentTools;
import com.jasperjinx.svl.components.SVGIcon;
import com.jasperjinx.svl.sort.*;
import com.jasperjinx.svl.visualizer.IntegerNode;
import com.jasperjinx.svl.visualizer.SortAlgorithm;

import com.jasperjinx.svl.visualizer.Stopwatch;
import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.awt.Toolkit;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.stage.Screen;


final class SceneInitialize {

    private static final Runnable WINDOWS_ALERT_SOUND =
            (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");

    private static final double HIGH = Screen.getPrimary().getVisualBounds().getHeight()*4/5;
    private static final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth()*9/10;
    private static final double SPACING = 0.0;
    private static final boolean SPLIT = false;

    private static int size = 160;
    private static long delay = 50;

    private static Label intID = ComponentTools.label("Int: -");

    private static IntegerNode.Builder builder = new IntegerNode.Builder()
            .setHigh(HIGH)
            .setWidth(SPLIT? WIDTH/2:WIDTH)
            .setSpacing(SPACING)
            .setRipplerColor(ComponentTools.ripplerColor)
            .setLabelID(intID);

    private static IntegerNode integerNode = builder.build();
    private static HBox playScene = new HBox(integerNode.getRectNode(size));

    private static SortAlgorithm sortAlgorithm = new SortAlgorithm(integerNode.getRectNode(size));

    static void exit() {
        sortAlgorithm.stop();
    }

    private static void update() {
        playScene.getChildren().setAll(integerNode.getRectNode(size));
        sortAlgorithm = new SortAlgorithm(integerNode.getRectNode(size));
    }

    private static void playAlertSound() {
        WINDOWS_ALERT_SOUND.run();
    }

    static Parent getScene() {


        System.out.println("Initializing");
        System.out.println("High: " + HIGH);
        System.out.println("Width: "+ WIDTH);


        //Create components
        var isStop = new AtomicBoolean(false);
        var clockLabel = ComponentTools.label("0.000");
        var stopwatch = new Stopwatch(clockLabel);


        //Control Button
        var moreButton = ComponentTools.SVGIconButton(SVGIcon.ADD,"More");
        var lessButton = ComponentTools.SVGIconButton(SVGIcon.SUBTRACT,"Less");

        var playButton = ComponentTools.SVGIconButton(SVGIcon.PLAY,"Play");
        var playBorder = new HBox(playButton);
        var sortCombo = ComponentTools.createCombobox();

        //var stopButton = ComponentTools.SVGIconButton(SVGIcon.STOP,"Stop");
        var resetButton = ComponentTools.SVGIconButton(SVGIcon.RESET,"Reset");
        var shuffleButton = ComponentTools.SVGIconButton(SVGIcon.SHUFFLE,"Shuffle");

        //Delay Setting
        var delayText = ComponentTools.label("Delay:");
        var delaySlider = ComponentTools.slider();
        var delayShowTime = ComponentTools.label(delay+"");
        var delayMSText = ComponentTools.label("ms");

        //Create pane
        var delayBar = new HBox(delayText,delaySlider,delayShowTime,delayMSText,clockLabel);
        var controlBar = new HBox(
                playBorder,shuffleButton,resetButton,sortCombo,
                delayBar,
                moreButton,lessButton,intID
        );
        var scene = new VBox(controlBar,playScene);

        //Set component actions


        shuffleButton.setOnAction(actionEvent-> {

            moreButton.setDisable(true);
            lessButton.setDisable(true);
            resetButton.setDisable(true);
            playBorder.setDisable(true);
            shuffleButton.setDisable(true);
            new Thread(() -> {
                sortAlgorithm.shuffleAnimation(playScene,size > 500? 3:10);
                resetButton.setDisable(false);
                playBorder.setDisable(false);
                shuffleButton.setDisable(false);

            }).start();

        });

        resetButton.setOnAction(actionEvent -> {
            clockLabel.setText("0.000");
            sortAlgorithm.reset(playScene);
            shuffleButton.setDisable(false);
            resetButton.setDisable(false);
            moreButton.setDisable(false);
            lessButton.setDisable(false);
        });

        sortCombo.setOnAction(actionEvent -> {
            //var value = SortType.valueOf(sortCombo.getValue().toString().toUpperCase());
            //currentSort = sortAlgorithm.getSortByName(SortType.valueOf(sortCombo.getValue().toString().toUpperCase()));
        });

        playButton.setOnAction(actionEvent-> {
            var currentSort = sortAlgorithm.getSortByName(SortType.valueOf(sortCombo.getValue().toString().toUpperCase()));
            if(isStop.compareAndSet(false,true)) {
                delayBar.setStyle("-fx-background-color: rgba(32,40,48,1);" +
                        "-fx-background-radius: 25;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-color: #F05F57;" +
                        "-fx-border-radius: 25;"
                );

                setToStop(playButton);

                //setWhilePlay(true,playButton,playBorder);

                new Thread(() -> {

                    shuffleButton.setDisable(true);
                    resetButton.setDisable(true);
                    stopwatch.start();
                    currentSort.start(playScene);
                    stopwatch.stop();

                    playAlertSound();
                    setToPlay(playButton);
                    delayBar.setStyle("-fx-background-color: rgba(32,40,48,1);" +
                            "-fx-background-radius: 25;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-color: Black;" +
                            "-fx-border-radius: 25;"
                    );
                    isStop.set(false);
                    shuffleButton.setDisable(false);
                    resetButton.setDisable(false);
                    moreButton.setDisable(false);
                    lessButton.setDisable(false);

                }).start();
            } else {
                setToPlay(playButton);
                currentSort.stop();
            }
        });

        moreButton.setOnAction(actionEvent -> {
            size *= 2;
            if(size>=640)
                moreButton.setDisable(true);
            else
                moreButton.setDisable(false);
            if(size<=20)
                lessButton.setDisable(true);
            else
                lessButton.setDisable(false);
            update();
        });

        lessButton.setOnAction(actionEvent -> {
            size /= 2;
            if(size>=640)
                moreButton.setDisable(true);
            else
                moreButton.setDisable(false);
            if(size<=20)
                lessButton.setDisable(true);
            else
                lessButton.setDisable(false);
            update();
        });
        delaySlider.setOnMouseDragged(mouseEvent -> {
            delay = (long) Math.ceil(delaySlider.getValue());
            sortAlgorithm.setDelay(delay);
            delayShowTime.setText(delay+"");
        });

        //Set property


        resetButton.setDisable(true);
        playBorder.setDisable(true);
        playButton.setMinWidth(80);
        playButton.setMaxWidth(80);

        clockLabel.setMaxWidth(60);
        clockLabel.setMinWidth(60);
        clockLabel.setAlignment(Pos.CENTER);
        clockLabel.setStyle("-fx-font-size: 15; -fx-text-fill: rgba(72,200,160,1); ");

        delaySlider.setValue(delay);
        delayShowTime.setMinWidth(30);
        delayShowTime.setAlignment(Pos.CENTER);
        intID.setMinWidth(60);

        playScene.setAlignment(Pos.BOTTOM_CENTER);
        playScene.setMaxSize(WIDTH,HIGH);

        delayBar.setStyle("" +
                "-fx-background-color: rgba(32,40,48,1);" +
                "-fx-background-radius: 25;" +
                "-fx-border-width: 2;" +
                "-fx-border-color: Black;" +
                "-fx-border-radius: 25;"
        );

        delayBar.setAlignment(Pos.CENTER);
        delayBar.setSpacing(5);
        delayBar.setPadding(new Insets(2,10,2,10));
        playBorder.setPadding(new Insets(2,2,2,2));
        playBorder.setStyle(
                "-fx-background-color:rgba(0,0,0,0);" +
                "-fx-background-radius: 25;");
        //setWhilePlay(false,playButton,playBorder);
        playBorder.setAlignment(Pos.CENTER);

        controlBar.setSpacing(10);
        controlBar.setAlignment(Pos.CENTER);
        scene.setAlignment(Pos.CENTER_RIGHT);
        scene.setSpacing(20);
        scene.setPadding(new Insets(20,0,0,0));

        return scene;
    }
    private static void setToPlay(JFXButton button) {
        Platform.runLater(() -> {
            button.setText(" Play");
            button.setGraphic(ComponentTools.getSVGGraphic(SVGIcon.PLAY));
        });
    }
    private static void setToStop(JFXButton button) {
        Platform.runLater(() -> {
            button.setText(" Stop");
            button.setGraphic(ComponentTools.getSVGGraphic(SVGIcon.STOP));
        });
    }






}
