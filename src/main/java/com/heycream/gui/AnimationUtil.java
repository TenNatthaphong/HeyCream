package com.heycream.gui.util;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public final class AnimationUtil {
    private AnimationUtil() {}

    /**
     * Add a background image (cover) and spawn multiple moving clouds.
     * Clouds move left->right endlessly with random speed/height.
     */
    public static void addBackgroundWithClouds(
            AnchorPane root,
            String backgroundPng,
            String cloudPng,
            double width, double height,
            int cloudCount
    ) {
        // Background layer
        ImageView bg = new ImageView(new Image(
                Objects.requireNonNull(AnimationUtil.class.getResource(backgroundPng),
                        "Missing asset: " + backgroundPng).toExternalForm()
        ));
        bg.setFitWidth(width);
        bg.setFitHeight(height);
        bg.setPreserveRatio(false);

        AnchorPane layer = new AnchorPane(bg);
        root.getChildren().add(0, layer);

        // Clouds
        for (int i = 0; i < cloudCount; i++) {
            spawnCloud(layer, cloudPng, width, height);
        }
    }

    private static void spawnCloud(AnchorPane layer, String cloudPng, double sceneW, double sceneH) {
        ThreadLocalRandom r = ThreadLocalRandom.current();

        ImageView cloud = new ImageView(new Image(
                Objects.requireNonNull(AnimationUtil.class.getResource(cloudPng),
                        "Missing asset: " + cloudPng).toExternalForm()
        ));
        cloud.setOpacity(0.85);
        cloud.setPreserveRatio(true);

        // Random scale and Y band near sky
        double scale = r.nextDouble(0.9, 1.3);
        cloud.setScaleX(scale);
        cloud.setScaleY(scale);

        double y = r.nextDouble(20, sceneH * 0.35);
        cloud.setLayoutY(y);

        // Start slightly off-screen on the left with random offset
        double startX = -200 - r.nextDouble(0, 300);
        cloud.setLayoutX(startX);

        layer.getChildren().add(cloud);

        // Random speed
        double seconds = r.nextDouble(22, 36); // slower looks cute
        TranslateTransition tt = new TranslateTransition(Duration.seconds(seconds), cloud);
        tt.setFromX(0);
        tt.setToX(sceneW + 400); // run off the right side
        tt.setCycleCount(TranslateTransition.INDEFINITE);
        tt.setInterpolator(Interpolator.LINEAR);
        tt.setOnFinished(ev -> {
            // When a cycle ends we can randomize again (not strictly needed for INDEFINITE)
        });
        tt.play();
    }
}
