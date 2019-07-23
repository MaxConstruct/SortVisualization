package com.jasperjinx.svl.components;

public enum SVGIcon {

    PLAY(15),
    PAUSE(25),
    STOP(12),
    ADD(25),
    SUBTRACT(25),
    SHUFFLE(15),
    SETTING(15),
    RESET(15);

    private int size;
    SVGIcon(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
