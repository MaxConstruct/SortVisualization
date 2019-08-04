package com.jasperjinx.svl.visualizer;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class SortTools {

    public static <P extends Pane,N extends Node> void updateScene(P scene,long delay,N[] node) {
        Platform.runLater(()->  {
            try {
                scene.getChildren().setAll(node);
            }
            catch(IllegalArgumentException ex) {
                ex.getMessage();
            }
        });
        try { Thread.sleep(delay);}
        catch(InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void swap(RectNode a, RectNode b) {
        RectNode t = a;
        a = b;
        b = t;
    }

}
