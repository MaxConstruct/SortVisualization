package com.jasperjinx.svl.sort;

import com.jasperjinx.svl.visualizer.SortAlgorithm;

public class SelectionSort extends SortAlgorithm implements Sort {

    public SelectionSort(SortBuilder builder) {
        super(builder);
    }

    @Override
    public void start() {
        setPlay();
        for(int i = 0;i<val.length-1 && play;i++) {
            int min = i;
            for(int j = i+1;j<val.length && play;j++) {
                if(val[j]<val[min]) {
                    min = j;
                    updateScene(delay);
                }
            }
            swap(min ,i);
            updateScene(delay);
        }
    }

}
