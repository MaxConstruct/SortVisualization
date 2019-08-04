package com.jasperjinx.svl.components;

import com.jasperjinx.svl.sort.SortType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.util.Arrays;


public class ComponentTools {

    private static String rectStyle = linearColor("#F05F57","#360940");

    private static String timerCSS = ""
            + "-fx-border-color:White;"
            + "-fx-border-radius:10;";
    private static String paneCSS = ""
            + "-fx-background-color:Black;";/*linear-gradient(#FFAA85 0%, #B3315F 100%);*/
    private static String buttonCSS = ""
            + "-fx-background-color:White;" +
              "-fx-font-size:15;";
    private static String barCSS =""
            + "-fx-background-color:rgba(0,0,0,0.5);"
            + "-fx-background-radius:5;";
    public static String ripplerColor = "White";

    private static String linearColor(String top, String bottom) {
        return "-fx-fill:linear-gradient("+top+" 0%, "+bottom+" 100%);"
                + "-fx-stroke:linear-gradient("+top+" 0%, "+bottom+" 100%);"
                + "-fx-stroke-width:1;"
                + "-fx-stroke-type:inside;";
    }

    public static JFXButton button(String text) {
        var jbtn = new JFXButton(text);
        jbtn.setMinHeight(20);
        jbtn.setMinWidth(100);
        jbtn.setStyle(buttonCSS);
        return jbtn;
    }

    public static SVGPath getSVGGraphic(SVGIcon svg) {
        var path = SVGLoader.getPathByName(svg);
        var bounds = path.getBoundsInLocal();

        // scale to size 20x20 (max)
        int iconSize = svg.getSize();
        double scaleFactor = iconSize / Math.max(bounds.getWidth(), bounds.getHeight());
        path.setScaleX(scaleFactor);
        path.setScaleY(scaleFactor);
        path.getStyleClass().add("button-icon");
        path.setStyle("-fx-background-color: transparent, transparent, transparent, transparent, transparent;\n" +
                "    -fx-padding: 5;");


        return path;
    }

    public static JFXButton SVGIconButton(SVGIcon svg,String text) {
        return SVGIconButton(svg, text,
                "-fx-background-color:rgba(0,0,0,0);" +
                        "-fx-font-size:15;" +
                        "-fx-text-fill: White;" +
                        "-fx-background-radius:25;",
                "-fx-background-color:rgba(0,0,0,0);" +
                        "-fx-font-size:15;" +
                        "-fx-text-fill: #F05F57;" +
                        "-fx-background-radius:25;"
        );

    }

    public static JFXButton SVGIconButton(SVGIcon svg,String text,String styleNormal, String styleHover) {

        var button = new JFXButton((svg.getSize() == 15 ? "": " ")+text);
        button.setPickOnBounds(true); // make sure transparent parts of the button register clicks too
        button.setGraphic(getSVGGraphic(svg));
        button.setAlignment(Pos.CENTER);
        button.setStyle(styleNormal);
        button.getStyleClass().add("icon-button");
        button.setOnMouseEntered(actionEvent -> {
            button.setStyle(styleHover);
        });
        button.setOnMouseExited(actionEvent -> {
            button.setStyle(styleNormal);
        });
        return button;
    }

    public static Label label(String text) {
        var t = new Label(text);
        t.setStyle("-fx-font-size:15;");
        t.setAlignment(Pos.CENTER_LEFT);
        t.setTextFill(Color.WHITE);
        return t;
    }

    public static JFXSlider slider() {
        var slide = new JFXSlider();
        slide.setMin(1);
        slide.setMax(100);
        slide.setValue(50);
        slide.setMinSize(200, 30);
        slide.setShowTickLabels(true);
        slide.setShowTickMarks(false);
        slide.setBlockIncrement(1);
        slide.setIndicatorPosition(JFXSlider.IndicatorPosition.RIGHT);
        slide.showTickLabelsProperty().set(false);
        slide.showTickMarksProperty().set(false);
        return slide;
    }
    public static JFXComboBox createCombobox() {
        JFXComboBox sortChoice = new JFXComboBox();
        sortChoice.setMinHeight(20);
        sortChoice.setPromptText("Select Sort");
        sortChoice.setItems(FXCollections.observableList(Arrays.asList("Selection","Quick","DualQuick")));
        sortChoice.getSelectionModel().select(1);
        //sortChoice.getStylesheets().add("sortvisualization/icon_1.css");
        return sortChoice;
    }

}
