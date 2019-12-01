package com.jasperjinx.svl.sort;

public enum SortType {
    SELECTION("Selection Sort"),
    QUICK("Quick Sort"),
    MERGE_IP("Merge Sort"),
    DUAL_QUICK("Dual-Pivot Quick Sort");

    private String name;

    SortType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return name;
    }
}