package com.jasperjinx.svl.sort;

import com.jasperjinx.svl.visualizer.RectNode;
import com.jasperjinx.svl.visualizer.SortAlgorithm;
import javafx.scene.layout.Pane;

public class MergeSortInPlace extends SortAlgorithm implements Sort {
    public MergeSortInPlace(RectNode[] rectNodes) {
        super(rectNodes);
    }

    @Override
    public void start(Pane scene) {
        setPlay();
        doSort(scene,0,rectNodes.length - 1);
    }

    private void doSort(Pane scene, int left, int right) {
        if(left < right && play) {
            int mid = left + (right - left) / 2;
            doSort(scene, left, mid);
            doSort(scene, mid + 1, right);
            merge(scene, left, mid, right);
        }
    }

    private void merge(Pane scene, int left, int mid, int right) {
        int s = mid + 1;
        if (rectNodes[mid].compareTo(rectNodes[s]) <= 0 && play)
            return;
        while (left <= mid && s <= right && play) {
            if (rectNodes[left].compareTo(rectNodes[s]) <= 0) left++;
            else {
                RectNode value = rectNodes[s];
                int index = s;
                while(index != left) rectNodes[index] = rectNodes[--index];
                rectNodes[left] = value;
                updateScene(scene,delay);
                left++;
                mid++;
                s++;
            }
        }
        updateScene(scene,delay);
    }



}
