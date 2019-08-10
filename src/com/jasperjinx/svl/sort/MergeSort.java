package com.jasperjinx.svl.sort;

import com.jasperjinx.svl.visualizer.RectNode;
import com.jasperjinx.svl.visualizer.SortAlgorithm;
import javafx.scene.layout.Pane;

public class MergeSort extends SortAlgorithm implements Sort {
    public MergeSort(RectNode[] rectNodes) {
        super(rectNodes);
    }

    @Override
    public void start(Pane scene) {
        setPlay();
        doSort(scene,0,rectNodes.length-1);
    }
    private void doSort(Pane scene,int left, int right) {
        if(left < right && play) {
            if(right-left < 47) insertion(scene,left,right);
            else {
                int mid = left + (right - left) / 2;
                doSort(scene, left, mid);
                doSort(scene, mid + 1, right);
                merge(scene, left, mid, right);
            }
        }
    }
    private void merge(Pane scene, int left, int mid, int right) {

        int n1 = mid - left + 1;
        int n2 = right - mid;

        RectNode[] L = new RectNode[n1];
        RectNode[] R = new RectNode[n2];
        for (int i = 0; i < n1; i++) L[i] = rectNodes[left+i];
        for (int i = 0; i < n2; i++) R[i] = rectNodes[mid+i+1];

        int i=0,j=0,k=left;
        while (i < n1 && j < n2 && play) {
            rectNodes[k++] = L[i].compareTo(R[j]) <= 0 ? L[i++] : R[j++];
            updateScene(scene,delay);
        }
        while (i < n1) rectNodes[k++] = L[i++];
        while (j < n2) rectNodes[k++] = R[j++];
        updateScene(scene,delay);

    }
    private void insertion(Pane scene, int left, int right) {
        int p,j;
        RectNode t;
        for (p = left; p <= right;p++) {
            t = rectNodes[p];
            for(j = p; j > 0 && t.compareTo(rectNodes[j - 1]) < 0; j--) {
                rectNodes[j] = rectNodes[j - 1];
                updateScene(scene,delay);
            }
            rectNodes[j] = t;
        }

    }
}
