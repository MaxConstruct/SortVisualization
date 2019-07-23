package com.jasperjinx.svl.visualizer;

import com.jasperjinx.svl.sort.*;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import java.util.Random;

public class SortAlgorithm {

    private static final SortType DEFAULT_SORT = SortType.SELECTION;

    protected static boolean play = false;
    protected static long delay = 50;

    private Pane[] node;
    protected int[] val;

    private Pane scene;
    private SortBuilder builder;


    public SortAlgorithm(SortBuilder builder) {
        this.node = builder.getNode();
        this.val = builder.getValue();
        this.scene = builder.getScene();
        this.builder = builder;

    }

    public Sort getDefaultSort() {
        return getSortByName(DEFAULT_SORT);
    }

    public void setDelay(long delay) {
       SortAlgorithm.delay = delay;
    }

    public Sort getSortByName(SortType type) {
        switch (type) {
            case SELECTION: return new SelectionSort(builder);
            case QUICK:     return new QuickSort(builder);
            default: throw new UnsupportedOperationException("Unsupported Sort: "+type.toString());
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
    public void reset() {
        CustomQuickSort.sort(node,val,0,val.length-1);
        updateScene(0);
    }

    public void shuffleAnimation(long delay) {
        var n = node.length;
        var r = new Random();
        for(int i = 0;i<n;i++) {
            swap(i , i + r.nextInt(n-i));
            updateScene(delay);
        }
    }

    protected void swap(int i, int j) {
        Pane tmp = node[i];
        node[i] = node[j];
        node[j] = tmp;

        int t = val[i];
        val[i] = val[j];
        val[j] = t;
    }

    protected void updateScene(long delay) {
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
    protected void updateScene() {
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


}
