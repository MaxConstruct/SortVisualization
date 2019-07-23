package com.jasperjinx.svl.visualizer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;

public class Stopwatch {
    private long start = 0,stop = 0;
    private AtomicBoolean isStop = new AtomicBoolean(false);;
    private Timeline timeline;

    public Stopwatch(Label label) {
        init(label);
    }


    public void init(Label label) {
        timeline = new Timeline(new KeyFrame(Duration.millis(50), event ->
            label.setText(String.format(
                    "%,.3f",
                    1e-3*((isStop.get() ? stop:System.currentTimeMillis())-start)
            ))
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);

    }

    public void start() {
        isStop.set(false);
        start = System.currentTimeMillis();
        timeline.play();
    }
    public void stop() {
        isStop.set(true);
        stop = System.currentTimeMillis();
        timeline.stop();
    }

}
