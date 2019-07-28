package com.jasperjinx.svl.visualizer;

import com.jfoenix.controls.JFXRippler;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.HashMap;
import java.util.Map;

public class IntegerNode {

    private final static String DEFAULT_RIPPLER_COLOR = "#1e6afe";

    private final static int[] INITIAL_SIZE = {20,40,80,160,320,640,1280};

    private Map<Integer,JFXRippler[]> rectNode = new HashMap<>();
    private Map<Integer,int[]> rectValue = new HashMap<>();

    private double high;
    private double width;
    private double spacing;
    private String ripplerColor;
    private Label labelID;

    private IntegerNode(Builder builder) {
        this.width = builder.width;
        this.spacing = builder.spacing;
        this.high = builder.high;
        this.ripplerColor = builder.ripplerColor == null?
                DEFAULT_RIPPLER_COLOR:
                builder.ripplerColor;
        this.labelID = builder.labelID;
        initScene();
    }

    public StackPane[] getNodeArray(int size) {
        checkIsContains(size);
        return rectNode.get(size);
    }

    public int[] getNodeValue(int size) {
        checkIsContains(size);
        return rectValue.get(size);
    }

    private void checkIsContains(int size) {
        if(!rectNode.containsKey(size))
            createScene(size);
    }

    private void initScene() {

        long s = System.nanoTime();

        for(int initSize:INITIAL_SIZE)
            createScene(initSize);


        long e = System.nanoTime();
        double t = e-s;
        System.out.printf("NodeInt time: %f ms (%f s)\n",t*1e-6,t*1e-9);
    }

    private void createScene(int integerSize) {

        Rectangle rect;
        double w,h;

        int[] val = new int[integerSize];
        JFXRippler[] rippler = new JFXRippler[integerSize];

        w = (width - (spacing * (integerSize - 1))) / integerSize;
        h = (high + 1) / integerSize;

        for (int i = 0; i < integerSize; i++) {
            final int no = i + 1;
            val[i] = no;
            rect = new Rectangle();
            rect.setWidth(w);
            rect.setHeight(h * no);
            rect.getStyleClass().add("rectangle");

            /*


            rect.setStyle("-fx-fill:      linear-gradient(rgba(72,200,160,1) 0, rgba(32,40,48,1) 100 );" +
                    "    -fx-stroke:     linear-gradient(rgba(72,200,160,1) 0, rgba(32,40,48,1) 100 );" +
                    "    -fx-stroke-width:   1;" +
                    "    -fx-stroke-type:    inside;");

             */


            rippler[i] = new JFXRippler(rect);
            rippler[i].setRipplerFill(Color.web(ripplerColor));
            rippler[i].setMaxHeight(h * no);
            rippler[i].setOnMouseEntered(actionEvent -> {
                labelID.setText("Int: " + no);
                labelID.setStyle("-fx-font-size:15;" +
                        "-fx-text-fill: #F05F57;");
            });
            rippler[i].setOnMouseExited(actionEvent -> {
                labelID.setText("Int: -");
                labelID.setStyle("-fx-font-size:15;" +
                        "-fx-text-fill: White;");
            });
        }
        rectNode.put(integerSize,rippler);
        rectValue.put(integerSize,val);
    }

    public static class Builder {
        private double high;
        private double width;
        private double spacing;
        private String ripplerColor;

        private Label labelID;

        public Builder() {

        }

        public Builder setLabelID(Label labelID) {
            this.labelID = labelID;
            return this;
        }

        public Builder setHigh(double high) {
            this.high = high;
            return this;
        }

        public Builder setWidth(double width) {
            this.width = width;
            return this;
        }

        public Builder setSpacing(double spacing) {
            this.spacing = spacing;
            return this;
        }

        public Builder setRipplerColor(String ripplerColor) {
            this.ripplerColor = ripplerColor;
            return this;
        }

        public IntegerNode build() {
            return new IntegerNode(this);
        }
    }

}
