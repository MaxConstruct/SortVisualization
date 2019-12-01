package com.jasperjinx.svl.sort;

import com.jasperjinx.svl.visualizer.RectNode;
import com.jasperjinx.svl.visualizer.SortAlgorithm;
import javafx.scene.layout.Pane;

public class QuickSort extends SortAlgorithm implements Sort {

    public QuickSort(RectNode[] rectNodes) {
        super(rectNodes);
    }
    @Override
    public void start(Pane scene) {
        setPlay();
        doSort(scene,0,rectNodes.length-1);
    }
    private void doSort(Pane scene,int low, int high) {
        if (low < high && play)
        {
            int pi = partition(scene,low, high);
            doSort(scene,low, pi-1);
            doSort(scene,pi+1, high);
        }
    }

    private <P extends Pane> int partition(P scene,int low, int high) {
        RectNode pivot = rectNodes[high];
        int i = low-1;
        for (int j=low; j<high; j++) {
            if (pivot.compareTo(rectNodes[j]) > 0 && play) {
                swap(++i,j);
                updateScene(scene,delay);
            }
        }
        swap(++i,high);
        updateScene(scene,delay);
        return i;
    }

}
