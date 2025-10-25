package com.heycream.gui;

import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimationUtil {

    public static void rotatePour(Node node) {
        RotateTransition rotate = new RotateTransition(Duration.seconds(0.6), node);
        rotate.setFromAngle(0);
        rotate.setToAngle(-45);
        rotate.setAutoReverse(true);
        rotate.setCycleCount(2);
        rotate.play();
    }
}
