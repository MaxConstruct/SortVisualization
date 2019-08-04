package com.jasperjinx.svl.visualizer;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.HashMap;
import java.util.Map;

public class IntegerNode {

    private final static String DEFAULT_RIPPLER_COLOR = "#1e6afe";

    private final static int[] INITIAL_SIZE = {20,40,80,160,320,640,1280};

    private Map<Integer, RectNode[]> rectNode = new HashMap<>();

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

    public RectNode[] getRectNode(int size) {
        checkIsContains(size);
        return rectNode.get(size);
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

    private RectNode[] doCreateScene(int integerSize) {
        Rectangle rect;
        RectNode[] rpl = new RectNode[integerSize];

        double w = (width - (spacing * (integerSize - 1))) / integerSize;
        double h = (high + 1) / integerSize;
        System.out.println("Const:"+integerSize*w+" Size:"+integerSize+"\tW:"+w+"  H:"+h);

        for (int i = 0; i < integerSize; i++) {
            final int no = i + 1;
            rect = new Rectangle();
            rect.getStyleClass().add("rectangle");
            rpl[i] = new RectNode(rect,no);
            rpl[i].setRipplerFill(Color.web(ripplerColor));
            rpl[i].setMaxHeight(h * no);
            rpl[i].setMaxWidth(w);
            rpl[i].setMinHeight(h * no);
            rpl[i].setMinWidth(w);
            rect.widthProperty().bind(rpl[i].widthProperty());
            rect.heightProperty().bind(rpl[i].heightProperty());
            rpl[i].setOnMouseEntered(actionEvent -> {
                labelID.setText("Int: " + no);
                labelID.setStyle("-fx-font-size:15;" +
                        "-fx-text-fill: #F05F57;");
            });
            rpl[i].setOnMouseExited(actionEvent -> {
                labelID.setText("Int: -");
                labelID.setStyle("-fx-font-size:15;" +
                        "-fx-text-fill: White;");
            });
        }
        return rpl;
    }

    private void createScene(int integerSize) {
        var temp = doCreateScene(integerSize);
        rectNode.put(integerSize,temp);
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
