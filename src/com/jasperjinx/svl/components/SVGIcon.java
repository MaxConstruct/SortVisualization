package com.jasperjinx.svl.components;

public class SVGIcon {
    private static final String SVG_PLAY = "M8 5v14l11-7z";
    private static final String SVG_STOP = "M6 6h12v12H6z";
    private static final String SVG_ADD = "M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm5 11h-4v4h-2v-4H7v-2h4V7h2v4h4v2z";
    private static final String SVG_SUBTRACT = "M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm5 11H7v-2h10v2z";
    private static final String SVG_SHUFFLE = "M10.59 9.17L5.41 4 4 5.41l5.17 5.17 1.42-1.41zM14.5 4l2.04 2.04L4 18.59 5.41 20 17.96 7.46 20 9.5V4h-5.5zm.33 9.41l-1.41 1.41 3.13 3.13L14.5 20H20v-5.5l-2.04 2.04-3.13-3.13z";
    private static final String SVG_RESET = "M12 5V1L7 6l5 5V7c3.31 0 6 2.69 6 6s-2.69 6-6 6-6-2.69-6-6H4c0 4.42 3.58 8 8 8s8-3.58 8-8-3.58-8-8-8z";

    public static SVGIcon PLAY = new SVGIcon(15,SVG_PLAY);
    public static SVGIcon STOP = new SVGIcon(12,SVG_STOP);
    public static SVGIcon ADD = new SVGIcon(25,SVG_ADD);
    public static SVGIcon SUBTRACT = new SVGIcon(25,SVG_SUBTRACT);
    public static SVGIcon SHUFFLE = new SVGIcon(15,SVG_SHUFFLE);
    public static SVGIcon RESET = new SVGIcon(15,SVG_RESET);

    private int size;
    private String path;

    SVGIcon(int size, String path) {
        this.size = size;
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }

}
