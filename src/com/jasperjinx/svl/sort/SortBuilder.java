package com.jasperjinx.svl.sort;

import com.jasperjinx.svl.visualizer.SortAlgorithm;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class SortBuilder {

    private Pane[] node;
    private int[] val;
    private long delay;
    private Pane scene;

    public SortBuilder() {

    }

    public SortBuilder setNode(StackPane[] node) {
        this.node = node;
        return this;
    }
    public SortBuilder setValue(int[] val) {
        this.val = val;
        return this;
    }
    public SortBuilder setDelay(long delay) {
        this.delay = delay;
        return this;
    }
    public <T extends Pane> SortBuilder setScene(T scene) {
        this.scene = scene;
        return this;
    }

    public SortAlgorithm build() {
        return new SortAlgorithm(this);
    }

    public Pane getScene() {
        return scene;
    }

    public int[] getValue() {
        return val;
    }

    public long getDelay() {
        return delay;
    }

    public Pane[] getNode() {
        return node;
    }



}
