package com.jasperjinx.svl.sort;

import javafx.scene.Node;

public class CustomQuickSort {

    private static void swap(Node[] n,int[] a, int index1, int index2) {
        int tmp = a[index1];
        a[index1] = a[index2];
        a[index2] = tmp;

        Node tmp2 = n[index1];
        n[index1] = n[index2];
        n[index2] = tmp2;

    }

    private static int partition(Node[] n,int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low-1);
        for (int j=low; j<high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(n,arr,i,j);
            }
        }
        swap(n,arr,i+1,high);
        return i+1;
    }
    public static void sort(Node[] n,int[] arr, int low, int high) {
        if (low < high)
        {
            int pi = partition(n,arr, low, high);
            sort(n,arr, low, pi-1);
            sort(n,arr, pi+1, high);
        }
    }
}
