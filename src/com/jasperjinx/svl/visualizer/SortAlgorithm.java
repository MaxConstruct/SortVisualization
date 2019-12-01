package com.jasperjinx.svl.visualizer;

import com.jasperjinx.svl.sort.*;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.Random;

public class SortAlgorithm {

    protected static boolean play = false;
    protected static long delay = 50;

    protected RectNode[] rectNodes;

    public SortAlgorithm(RectNode[] rectNodes) {
        this.rectNodes = rectNodes;
    }

    public void setDelay(long delay) {
       SortAlgorithm.delay = delay;
    }

    public Sort getSortByName(SortType type) {
        switch (type) {
            case SELECTION  : return new SelectionSort(rectNodes);
            case QUICK      : return new QuickSort(rectNodes);
            case DUAL_QUICK : return new DualPivotQuickSort(rectNodes);
            case MERGE_IP   : return new MergeSortInPlace(rectNodes);
            default:
                throw new UnsupportedOperationException("Unsupported Sort: "+type.toString());
        }
    }

    protected void setPlay() {
        play = true;
    }

    public void stop() {
        play = false;
    }

    /*
    public void shuffleAnimation(long delay, int[] shuffle) {

        int n = node.length;
        for(int i = 0;i<n;i++) {
            swap(node,val,i , shuffle[i]);
            updateChildren(delay);
        }
    }

     */
    public void reset(Pane scene) {
        Arrays.sort(rectNodes);
        updateScene(scene,0);
    }

    public void shuffleAnimation(Pane scene,long delay) {
        var n = rectNodes.length;
        var r = new Random();
        for(int i = 0;i<n;i++) {
            swap(i , i + r.nextInt(n-i));
            updateScene(scene,delay);
        }
    }

    protected void swap(int i, int j) {
        RectNode o = rectNodes[i];
        rectNodes[i] = rectNodes[j];
        rectNodes[j] = o;
    }

    protected void updateScene(Pane scene, long delay) {
        Platform.runLater(()->  {
            try {
                scene.getChildren().setAll(rectNodes);
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
    protected void updateScene(Pane scene) {
        updateScene(scene,delay);
    }


}
