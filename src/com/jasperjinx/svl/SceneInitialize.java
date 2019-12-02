package com.jasperjinx.svl;

import com.jasperjinx.svl.components.ComponentTools;
import com.jasperjinx.svl.components.SVGIcon;
import com.jasperjinx.svl.sort.*;
import com.jasperjinx.svl.visualizer.IntegerNode;
import com.jasperjinx.svl.visualizer.RectNode;
import com.jasperjinx.svl.visualizer.SortAlgorithm;

import com.jasperjinx.svl.visualizer.Stopwatch;
import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.scene.paint.Color;
import javafx.stage.Screen;


final class SceneInitialize {

    private static final Runnable WINDOWS_ALERT_SOUND =
            (Runnable) Toolkit
            .getDefaultToolkit()
            .getDesktopProperty("win.sound.exclamation");

    private static final double HIGH = Screen.getPrimary().getVisualBounds().getHeight()*3.5/5;
    private static final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth()*9/10;
    private static final double SPACING = 0.0;

    private static final int UPPER_BOUND = 1280;
    private static final int LOWER_BOUND = 20;

    private static boolean split = true;
    private static int size = 160;
    private static long delay = 50;

    private static Label intID;

    private static IntegerNode integerNode1;
    private static IntegerNode integerNode2;

    private static HBox playScene1;
    private static HBox playScene2;

    private static JFXButton moreButton = ComponentTools.SVGIconButton(SVGIcon.ADD,"More");
    private static JFXButton lessButton = ComponentTools.SVGIconButton(SVGIcon.SUBTRACT,"Less");

    private static SortAlgorithm sortAlgorithm1;
    private static SortAlgorithm sortAlgorithm2;

    private static ArrayList<Integer> indexes = new ArrayList<>();

    private static void shuffleIndexArray() {
        System.out.println("Index Size: " + indexes.size());
        System.out.println("Rect Size: " + size);
        if(indexes.size() != size) {
            indexes.clear();
            for (int i = 0; i < size; i++)
                indexes.add(i);
        }
        Collections.shuffle(indexes);
    }

    static {

        shuffleIndexArray();

        intID = ComponentTools.label("Int: -");



        integerNode1 = new IntegerNode.Builder()
                .setHigh(split ? HIGH / 2 : HIGH)
                .setWidth(WIDTH)
                .setSpacing(SPACING)
                .setRipplerColor(ComponentTools.ripplerColor)
                .setLabelID(intID)
                .build();

        integerNode2 = new IntegerNode.Builder()
                .setHigh(split ? HIGH / 2 : HIGH)
                .setWidth(WIDTH)
                .setSpacing(SPACING)
                .setRipplerColor(ComponentTools.ripplerColor)
                .setLabelID(intID)
                .build();


        playScene1  = new HBox();
        playScene2  = new HBox();

        update();



    }
    static Main.ExitOption exit() {
        sortAlgorithm1.stop();
        sortAlgorithm2.stop();
        return Main.ExitOption.SHUTDOWN;
    }

    private static void update() {

        shuffleIndexArray();

        RectNode[] rectNodes1 = integerNode1.getRectNode(size);
        RectNode[] rectNodes2 = integerNode2.getRectNode(size);
        playScene1.getChildren().setAll(rectNodes1);
        playScene2.getChildren().setAll(rectNodes2);
        sortAlgorithm1 = new SortAlgorithm(rectNodes1);
        sortAlgorithm2 = new SortAlgorithm(rectNodes2);
    }

    private static void playAlertSound() {
        WINDOWS_ALERT_SOUND.run();
    }

    static Parent getScene() {

        System.out.println("Initializing");
        System.out.println("High: " + HIGH);
        System.out.println("Width: "+ WIDTH);

        //Create components
        var isStop = new AtomicBoolean[]{new AtomicBoolean(false),new AtomicBoolean(false)};
        var clockLabel1 = ComponentTools.label("0.000");
        var clockLabel2 = ComponentTools.label("0.000");
        var stopwatch1 = new Stopwatch(clockLabel1);
        var stopwatch2 = new Stopwatch(clockLabel2);

        //Control Button

        var playButton = ComponentTools.SVGIconButton(SVGIcon.PLAY,"Play");
        var playBorder = new HBox(playButton);
        var sortCombo1 = ComponentTools.createComboBox("");
        var sortCombo2 = ComponentTools.createComboBox("");


        //var stopButton = ComponentTools.SVGIconButton(SVGIcon.STOP,"Stop");
        var resetButton = ComponentTools.SVGIconButton(SVGIcon.RESET,"Reset");
        var shuffleButton = ComponentTools.SVGIconButton(SVGIcon.SHUFFLE,"Shuffle");

        //Delay Setting
        var delayText = ComponentTools.label("Delay:");
        var delaySlider = ComponentTools.slider();
        var delayShowTime = ComponentTools.label(delay+"");
        var delayMSText = ComponentTools.label("ms");

        //Create pane
        var delayBar = new HBox(delayText,delaySlider,delayShowTime,delayMSText,clockLabel1,clockLabel2);
        var controlBar = new HBox(
                playBorder,shuffleButton,resetButton,sortCombo1,sortCombo2,
                delayBar,
                moreButton,lessButton,intID
        );
        System.out.println(playScene1.getChildren().size());
        System.out.println(playScene2.getChildren().size());

        var sortLabel1 = ComponentTools.label("",18);
        var sortLabel2 = ComponentTools.label("",18);

        var scene = split? new VBox(controlBar,sortLabel1,playScene1,sortLabel2,playScene2):new VBox(controlBar,playScene1);

        //Set component actions
        shuffleButton.setOnAction(actionEvent-> {

            moreButton.setDisable(true);
            lessButton.setDisable(true);
            resetButton.setDisable(true);
            playBorder.setDisable(true);
            shuffleButton.setDisable(true);
            new Thread(() -> {
                sortAlgorithm1.shuffleAnimation(playScene1,size > 500? 3:10,indexes);
                resetButton.setDisable(false);
                playBorder.setDisable(false);
                shuffleButton.setDisable(false);
            }).start();
            if(split) new Thread(() -> sortAlgorithm2.shuffleAnimation(playScene2,size > 500? 3:10,indexes)).start();

        });

        resetButton.setOnAction(actionEvent -> {
            clockLabel1.setText("0.000");
            clockLabel2.setText("0.000");
            sortAlgorithm1.reset(playScene1);
            sortAlgorithm2.reset(playScene2);
            shuffleButton.setDisable(false);
            resetButton.setDisable(false);
            moreButton.setDisable(false);
            lessButton.setDisable(false);
        });

        sortCombo1.setOnAction(actionEvent ->{
            sortLabel1.setText(sortCombo1.getSelectionModel().getSelectedItem().getName());
        });

        sortCombo2.setOnAction(actionEvent ->{
            sortLabel2.setText(sortCombo2.getSelectionModel().getSelectedItem().getName());
        });


        playButton.setOnAction(actionEvent-> {
            var currentSort1 = sortAlgorithm1.getSortByName(sortCombo1.getValue());
            var currentSort2 = sortAlgorithm2.getSortByName(sortCombo2.getValue());
            if(isStop[0].compareAndSet(false,true) && isStop[1].compareAndSet(false,true)) {
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
                    stopwatch1.start();
                    currentSort1.start(playScene1);
                    stopwatch1.stop();

                    playAlertSound();

                    if(!split) {
                        setToPlay(playButton);
                        delayBar.setStyle("-fx-background-color: rgba(32,40,48,1);" +
                                "-fx-background-radius: 25;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-color: Black;" +
                                "-fx-border-radius: 25;"
                        );

                        shuffleButton.setDisable(false);
                        resetButton.setDisable(false);
                        moreButton.setDisable(false);
                        lessButton.setDisable(false);
                    }
                    else if(isStop[0].get()&&isStop[1].get()) {
                        setToPlay(playButton);
                        shuffleButton.setDisable(false);
                        resetButton.setDisable(false);
                        moreButton.setDisable(false);
                        lessButton.setDisable(false);
                        isStop[0].set(false);
                        isStop[1].set(false);
                    }

                }).start();

                if(split)
                    new Thread(() -> {

                        stopwatch2.start();
                        currentSort2.start(playScene2);
                        stopwatch2.stop();

                        playAlertSound();
                        delayBar.setStyle("-fx-background-color: rgba(32,40,48,1);" +
                                "-fx-background-radius: 25;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-color: Black;" +
                                "-fx-border-radius: 25;"
                        );
                        if(!split) {
                            setToPlay(playButton);
                            shuffleButton.setDisable(false);
                            resetButton.setDisable(false);
                            moreButton.setDisable(false);
                            lessButton.setDisable(false);
                        }
                        if(isStop[0].get()&&isStop[1].get()) {
                            setToPlay(playButton);
                            shuffleButton.setDisable(false);
                            resetButton.setDisable(false);
                            moreButton.setDisable(false);
                            lessButton.setDisable(false);
                            isStop[0].set(false);
                            isStop[1].set(false);
                        }

                    }).start();


            } else {
                setToPlay(playButton);
                currentSort1.stop();
                currentSort2.stop();
            }
        });

        moreButton.setOnAction(actionEvent -> {
            size *= 2;
            changeSizeChecking();
            update();
        });

        lessButton.setOnAction(actionEvent -> {
            size /= 2;
            changeSizeChecking();
            update();
        });
        delaySlider.setOnMouseDragged(mouseEvent -> {
            delay = (long) Math.ceil(delaySlider.getValue());
            sortAlgorithm1.setDelay(delay);
            sortAlgorithm2.setDelay(delay);
            delayShowTime.setText(delay+"");
        });

        //Set property
        sortCombo1.setItems(FXCollections.observableList(Arrays.asList(SortType.class.getEnumConstants())));
        sortCombo1.getSelectionModel().select(1);
        sortCombo2.setItems(FXCollections.observableList(Arrays.asList(SortType.class.getEnumConstants())));
        sortCombo2.getSelectionModel().select(1);

        sortLabel1.setText(sortCombo1.getSelectionModel().getSelectedItem().getName());
        sortLabel2.setText(sortCombo2.getSelectionModel().getSelectedItem().getName());

        sortLabel1.setPadding(new Insets(20,0,0,0));
        sortLabel2.setPadding(new Insets(20,0,0,0));

        resetButton.setDisable(true);
        playBorder.setDisable(true);
        playButton.setMinWidth(80);
        playButton.setMaxWidth(80);

        clockLabel1.setMaxWidth(60);
        clockLabel1.setMinWidth(60);
        clockLabel1.setAlignment(Pos.CENTER);
        clockLabel1.setStyle("-fx-font-size: 16; -fx-text-fill: rgba(72,200,160,1); -fx-font-family: Consolas; -fx-font-weight: bold;");

        clockLabel2.setMaxWidth(60);
        clockLabel2.setMinWidth(60);
        clockLabel2.setAlignment(Pos.CENTER);
        clockLabel2.setStyle("-fx-font-size: 16; -fx-text-fill: rgba(72,200,160,1); -fx-font-family: Consolas; -fx-font-weight: bold;");

        delaySlider.setValue(delay);
        delayShowTime.setMinWidth(30);
        delayShowTime.setAlignment(Pos.CENTER);
        intID.setMinWidth(60);

        playScene1.setAlignment(Pos.BOTTOM_CENTER);
        playScene1.setMaxSize(WIDTH,HIGH);

        playScene2.setAlignment(Pos.BOTTOM_CENTER);
        playScene2.setMaxSize(WIDTH,HIGH);

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
        scene.setAlignment(Pos.CENTER);
        scene.setSpacing(0);
        scene.setPadding(new Insets(10,0,0,0));

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

    private static void changeSizeChecking() {
        if(size>=UPPER_BOUND)
            moreButton.setDisable(true);
        else
            moreButton.setDisable(false);
        if(size<=LOWER_BOUND)
            lessButton.setDisable(true);
        else
            lessButton.setDisable(false);
    }

    private static <T> ObservableList<String> toStringArrayList(ObservableList<T> list) {
        ObservableList<String> t = FXCollections.observableArrayList();
        list.forEach(x->{
            String s = x.toString();
            t.add(
                    s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase()
            );
        });

        return t;
    }

}
