package com.jasperjinx.svl.visualizer;

import com.jfoenix.controls.JFXRippler;
import javafx.scene.shape.Rectangle;

public class RectNode extends JFXRippler implements Comparable<RectNode> {

    private int value;

    RectNode(Rectangle node, int value) {
        super(node);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(RectNode o) {
        return this.value - o.value;
    }

}
