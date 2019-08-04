package com.jasperjinx.svl.sort;

import com.jasperjinx.svl.visualizer.RectNode;
import com.jasperjinx.svl.visualizer.SortAlgorithm;
import javafx.scene.layout.Pane;

public class SelectionSort extends SortAlgorithm implements Sort {

    public SelectionSort(RectNode[] rectNodes) {
        super(rectNodes);
    }

    @Override
    public void start(Pane scene) {
        setPlay();
        int length = rectNodes.length;
        for(int i = 0;i<length-1 && play;i++) {
            int min = i;
            for(int j = i+1;j<length && play;j++) {
                if(rectNodes[min].compareTo(rectNodes[j]) > 0) {
                    min = j;
                    updateScene(scene,delay);
                }
            }
            swap(min ,i);
            updateScene(scene,delay);
        }
    }

}

