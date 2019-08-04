package com.jasperjinx.svl.sort;


import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public interface Sort {

    void start(Pane scene);
    void stop();
    void reset(Pane scene);

}
