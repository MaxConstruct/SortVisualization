package com.jasperjinx.svl.sort;

import com.jasperjinx.svl.visualizer.RectNode;
import com.jasperjinx.svl.visualizer.SortAlgorithm;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Dual Pivot QuickSort by Robert Sedgewick and Kevin Wayne.
 *
 */
public class DualPivotQuicksort extends SortAlgorithm implements Sort {

    public DualPivotQuicksort(RectNode[] rectNodes) {
        super(rectNodes);
    }
    @Override
    public void start(Pane scene) {
        doSort(scene,rectNodes, 0, rectNodes.length - 1);
    }
    private void doSort(Pane scene,RectNode[] a, int lo, int hi) {
        if (hi <= lo) return;
        if (less(a[hi], a[lo])) swap(lo, hi);
        int lt = lo + 1, gt = hi - 1;
        int i = lo + 1;
        while (i <= gt) {
            if       (less(a[i], a[lo])) swap(lt++, i++);
            else if  (less(a[hi], a[i])) swap(i, gt--);
            else                         i++;
            updateScene(scene,delay);
        }
        swap( lo, --lt);
        updateScene(scene,delay/2);
        swap( hi, ++gt);
        updateScene(scene,delay/2);


        doSort(scene,a, lo, lt-1);
        if (less(a[lt], a[gt])) doSort(scene,a, lt+1, gt-1);
        doSort(scene,a, gt+1, hi);

        assert isSorted(a, lo, hi);
    }
    private static boolean isSorted(RectNode[] a) {
        return isSorted(a, 0, a.length - 1);
    }
    private static boolean isSorted(RectNode[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;

    }
    private static boolean less(RectNode v, RectNode w) {
        return v.compareTo(w) < 0;
    }

}
