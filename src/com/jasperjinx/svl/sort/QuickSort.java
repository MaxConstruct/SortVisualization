package com.jasperjinx.svl.sort;

import com.jasperjinx.svl.visualizer.SortAlgorithm;

public class QuickSort extends SortAlgorithm implements Sort{

    public QuickSort(SortBuilder builder) {
        super(builder);
    }

    @Override
    public void start() {
        sort(0,val.length-1);
    }
    private void sort(int low, int high) {
        if (low < high)
        {
            int pi = partition(low, high);
            sort(low, pi-1);
            sort(pi+1, high);
        }
    }

    private int partition(int low, int high) {
        int pivot = val[high];
        int i = (low-1);
        for (int j=low; j<high; j++) {
            if (val[j] <= pivot) {
                i++;
                swap(i,j);
                updateScene(delay);
            }
        }
        swap(i+1,high);
        updateScene(delay);
        return i+1;
    }

}
