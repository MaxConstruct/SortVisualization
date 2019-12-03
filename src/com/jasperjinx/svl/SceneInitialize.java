package com.jasperjinx.svl;

import com.jasperjinx.svl.components.ComponentTools;
import com.jasperjinx.svl.components.SVGIcon;
import com.jasperjinx.svl.sort.*;
import com.jasperjinx.svl.visualizer.IntegerNode;
import com.jasperjinx.svl.visualizer.RectNode;
import com.jasperjinx.svl.visualizer.SortAlgorithm;

import com.jasperjinx.svl.visualizer.Stopwatch;
import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;


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

    public static Main.ExitOption currentOption = Main.ExitOption.SHUTDOWN;

    private static boolean split = false;
    private static int size = 160;
    private static long delay = 50;

    private static Label intID;
    private static ArrayList<Integer> indexes = new ArrayList<>();


    private  IntegerNode integerNode1;
    private  IntegerNode integerNode2;

    private  HBox playScene1;
    private  HBox playScene2;

    private  JFXButton moreButton = ComponentTools.SVGIconButton(SVGIcon.ADD,"More");
    private  JFXButton lessButton = ComponentTools.SVGIconButton(SVGIcon.SUBTRACT,"Less");

    private SortAlgorithm sortAlgorithm1;
    private SortAlgorithm sortAlgorithm2;


    private AtomicBoolean[] isStop = new AtomicBoolean[]{new AtomicBoolean(false),new AtomicBoolean(false)};

    private Label clockLabel1 = ComponentTools.label("0.000");
    private Label clockLabel2 = ComponentTools.label("0.000");
    private Stopwatch stopwatch1 = new Stopwatch(clockLabel1);
    private Stopwatch stopwatch2 = new Stopwatch(clockLabel2);

    //Control Button
    private JFXButton playButton;
    private HBox playBorder;
    private JFXComboBox<SortType> sortCombo1;
    private JFXComboBox<SortType> sortCombo2;

    private JFXToggleButton splitToggle;


    //var stopButton = ComponentTools.SVGIconButton(SVGIcon.STOP,"Stop");
    private JFXButton resetButton;
    private JFXButton shuffleButton;

    //Delay Setting
    private Label delayText;
    private JFXSlider delaySlider;
    private Label delayShowTime;
    private Label delayMSText;

    //Create pane
    private HBox delayBar;
    private HBox controlBar;

    private Label sortLabel1;
    private Label sortLabel2;

    private VBox scene;

    private static void shuffleIndexArray() {
        if(indexes.size() != size) {
            indexes.clear();
            for (int i = 0; i < size; i++)
                indexes.add(i);
        }
        Collections.shuffle(indexes);
    }

    public SceneInitialize() {

        currentOption = Main.ExitOption.SHUTDOWN;

        initScene();
        initComponent();
        initPref();
        initActionEvent();
    }

    private void initScene() {
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

    private void initComponent() {

        isStop = new AtomicBoolean[]{new AtomicBoolean(false),new AtomicBoolean(false)};
        clockLabel1 = ComponentTools.label("0.000");
        clockLabel2 = ComponentTools.label("0.000");
        stopwatch1 = new Stopwatch(clockLabel1);
        stopwatch2 = new Stopwatch(clockLabel2);

        //Control Button
        playButton = ComponentTools.SVGIconButton(SVGIcon.PLAY,"Play");
        playBorder = new HBox(playButton);
        sortCombo1 = ComponentTools.createComboBox("");
        sortCombo2 = ComponentTools.createComboBox("");

        splitToggle = new JFXToggleButton();
        splitToggle.setText("Comparison Mode");


        //var stopButton = ComponentTools.SVGIconButton(SVGIcon.STOP,"Stop");
        resetButton = ComponentTools.SVGIconButton(SVGIcon.RESET,"Reset");
        shuffleButton = ComponentTools.SVGIconButton(SVGIcon.SHUFFLE,"Shuffle");

        //Delay Setting
        delayText = ComponentTools.label("Delay:");
        delaySlider = ComponentTools.slider();
        delayShowTime = ComponentTools.label(delay+"");
        delayMSText = ComponentTools.label("ms");

        //Create pane
        delayBar = new HBox(delayText,delaySlider,delayShowTime,delayMSText,clockLabel1,clockLabel2);
        controlBar = split ? new HBox(
                playBorder,shuffleButton,resetButton,sortCombo1,sortCombo2,splitToggle,
                delayBar,
                moreButton,lessButton,intID
        ):
                new HBox(
                        playBorder,shuffleButton,resetButton,sortCombo1,splitToggle,
                        delayBar,
                        moreButton,lessButton,intID
                );

        sortLabel1 = ComponentTools.label("",18);
        sortLabel2 = ComponentTools.label("",18);

        scene = split? new VBox(controlBar,sortLabel1,playScene1,sortLabel2,playScene2):new VBox(controlBar,sortLabel1,playScene1);



    }

    private void initPref() {

        resetButton.setDisable(true);
        playBorder.setDisable(true);
        playButton.setMinWidth(80);
        playButton.setMaxWidth(80);

        clockLabel1.setMaxWidth(60);
        clockLabel1.setMinWidth(60);
        clockLabel1.setAlignment(Pos.CENTER);
        clockLabel1.setStyle("-fx-font-size: 16; -fx-text-fill: rgba(72,200,160,1); -fx-font-family: Consolas; -fx-font-weight: bold;");

        if(split) {
            clockLabel2.setMaxWidth(60);
            clockLabel2.setMinWidth(60);
            clockLabel2.setAlignment(Pos.CENTER);
            clockLabel2.setStyle("-fx-font-size: 16; -fx-text-fill: rgba(72,200,160,1); -fx-font-family: Consolas; -fx-font-weight: bold;");
        }

        delaySlider.setValue(delay);
        delayShowTime.setMinWidth(30);
        delayShowTime.setAlignment(Pos.CENTER);
        intID.setMinWidth(60);

        playScene1.setAlignment(Pos.BOTTOM_CENTER);
        playScene1.setMaxSize(WIDTH,HIGH);

        if(split) {
            playScene2.setAlignment(Pos.BOTTOM_CENTER);
            playScene2.setMaxSize(WIDTH, HIGH);
        }
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

        splitToggle.setTextFill(Color.WHITE);

        controlBar.setSpacing(10);
        controlBar.setAlignment(Pos.CENTER);
        scene.setAlignment(Pos.CENTER);
        scene.setSpacing(0);
        scene.setPadding(new Insets(10,0,0,0));
    }

    public VBox getScene() {
        return scene;
    }

    public Main.ExitOption exit() {
        sortAlgorithm1.stop();
        if(split)
            sortAlgorithm2.stop();
        return Main.ExitOption.SHUTDOWN;
    }

    private void update() {

        shuffleIndexArray();

        RectNode[] rectNodes1 = integerNode1.getRectNode(size);
        playScene1.getChildren().setAll(rectNodes1);
        sortAlgorithm1 = new SortAlgorithm(rectNodes1);

        if(split) {
            RectNode[] rectNodes2 = integerNode2.getRectNode(size);
            playScene2.getChildren().setAll(rectNodes2);
            sortAlgorithm2 = new SortAlgorithm(rectNodes2);
        }
    }

    private void playAlertSound() {
        WINDOWS_ALERT_SOUND.run();
    }

    private void initActionEvent() {

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

            sortAlgorithm1.reset(playScene1);

            shuffleButton.setDisable(false);
            resetButton.setDisable(false);
            moreButton.setDisable(false);
            lessButton.setDisable(false);

            if(split) {
                sortAlgorithm2.reset(playScene2);
                clockLabel2.setText("0.000");
            }
        });

        sortCombo1.setOnAction(actionEvent ->{
            sortLabel1.setText(sortCombo1.getSelectionModel().getSelectedItem().getName());
        });

        if(split)
        sortCombo2.setOnAction(actionEvent ->{
            sortLabel2.setText(sortCombo2.getSelectionModel().getSelectedItem().getName());
        });


        playButton.setOnAction(actionEvent-> {
            var currentSort1 = sortAlgorithm1.getSortByName(sortCombo1.getValue());
            Sort currentSort2 = null;
            if(split)
                 currentSort2 = sortAlgorithm2.getSortByName(sortCombo2.getValue());
            if(isStop[0].compareAndSet(false,true) && isStop[1].compareAndSet(false,true)) {
                delayBar.setStyle("-fx-background-color: rgba(32,40,48,1);" +
                        "-fx-background-radius: 25;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-color: #F05F57;" +
                        "-fx-border-radius: 25;"
                );

                setToStop(playButton);

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

                if(split) {
                    Sort finalCurrentSort = currentSort2;
                    new Thread(() -> {

                        stopwatch2.start();
                        finalCurrentSort.start(playScene2);
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
                }

            } else {
                setToPlay(playButton);
                currentSort1.stop();
                if(split)
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
            if(split)
                sortAlgorithm2.setDelay(delay);
            delayShowTime.setText(delay+"");
        });

        splitToggle.setSelected(split);

        splitToggle.setOnAction(actionEvent-> {
            split = splitToggle.isSelected();
            currentOption = Main.ExitOption.RESTART;
            ((Stage) splitToggle.getScene().getWindow()).close();
        });

        //Set property
        sortCombo1.setItems(FXCollections.observableList(Arrays.asList(SortType.class.getEnumConstants())));
        sortCombo1.getSelectionModel().select(1);
        if(split) {
            sortCombo2.setItems(FXCollections.observableList(Arrays.asList(SortType.class.getEnumConstants())));
            sortCombo2.getSelectionModel().select(1);
        }

        sortLabel1.setText(sortCombo1.getSelectionModel().getSelectedItem().getName());
        if(split)
            sortLabel2.setText(sortCombo2.getSelectionModel().getSelectedItem().getName());

        sortLabel1.setPadding(new Insets(20,0,0,0));
        if(split)
            sortLabel2.setPadding(new Insets(20,0,0,0));

    }

    private void setToPlay(JFXButton button) {
        Platform.runLater(() -> {
            button.setText(" Play");
            button.setGraphic(ComponentTools.getSVGGraphic(SVGIcon.PLAY));
        });
    }
    private void setToStop(JFXButton button) {
        Platform.runLater(() -> {
            button.setText(" Stop");
            button.setGraphic(ComponentTools.getSVGGraphic(SVGIcon.STOP));
        });
    }

    private void changeSizeChecking() {
        if(size>=UPPER_BOUND)
            moreButton.setDisable(true);
        else
            moreButton.setDisable(false);
        if(size<=LOWER_BOUND)
            lessButton.setDisable(true);
        else
            lessButton.setDisable(false);
    }

}
