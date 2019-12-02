package com.jasperjinx.svl.visualizer;

import com.jfoenix.controls.JFXRippler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectNode extends JFXRippler implements Comparable<RectNode> {

    private static final String STYLE_NORMAL =
            "-fx-fill:           linear-gradient(rgba(72,200,160,1) 0, rgba(32,40,48,1) 100 );" +
            "    -fx-stroke:         linear-gradient(rgba(72,200,160,1) 0, rgba(32,40,48,1) 100 );" +
            "    -fx-stroke-width:   1;\n" +
            "    -fx-stroke-type:    inside;";

    private static final String STYLE_HOVER =
            "-fx-fill:           #F05F57;" +
                    "    -fx-stroke:         #F05F57;" +
                    "    -fx-stroke-width:   1;" +
                    "    -fx-stroke-type:    inside;";

    private int value;

    RectNode(Rectangle node, int value) {
        super(node);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setSelected(boolean isSelected) {
        ((Rectangle) super.control).setStyle(isSelected ? STYLE_HOVER:STYLE_NORMAL);
    }

    @Override
    public int compareTo(RectNode o) {
        return this.value - o.value;
    }

}
