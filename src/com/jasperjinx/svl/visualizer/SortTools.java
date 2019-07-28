package com.jasperjinx.svl.visualizer;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class SortTools {
    public static <P extends Pane,N extends Node> void updateScene(P scene,N node,long delay) {
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

    public static <N extends Node> void swap(N[] node,int[] val,int i, int j) {
        N tmp = node[i];
        node[i] = node[j];
        node[j] = tmp;

        int t = val[i];
        val[i] = val[j];
        val[j] = t;
    }

}
