package com.heycream.gui;

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

    public static void addBackgroundWithClouds(
            AnchorPane root,
            String backgroundPng,
            String cloudPng,
            double width, double height,
            int cloudCount
    ) {
        // 🔹 Background image
        ImageView bg = new ImageView(new Image(
                Objects.requireNonNull(AnimationUtil.class.getResource(backgroundPng),
                        "Missing asset: " + backgroundPng).toExternalForm()
        ));
        bg.setFitWidth(width);
        bg.setFitHeight(height);
        bg.setPreserveRatio(false);

        AnchorPane layer = new AnchorPane(bg);
        root.getChildren().add(0, layer);

        // 🔹 Clouds (fewer, higher, slower)
        int safeCount = Math.max(3, Math.min(cloudCount, 6));
        for (int i = 0; i < safeCount; i++) {
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

        // ☁️ Random scale & Y (only top sky zone)
        double scale = r.nextDouble(0.7, 1.1);
        cloud.setScaleX(scale);
        cloud.setScaleY(scale);

        double y = r.nextDouble(sceneH * 0.05, sceneH * 0.25);
        cloud.setLayoutY(y);

        double startX = -250 - r.nextDouble(0, 300);
        cloud.setLayoutX(startX);
        layer.getChildren().add(cloud);

        // ☁️ Random gentle speed
        double seconds = r.nextDouble(20, 40);
        TranslateTransition tt = new TranslateTransition(Duration.seconds(seconds), cloud);
        tt.setFromX(0);
        tt.setToX(sceneW + 300);
        tt.setCycleCount(TranslateTransition.INDEFINITE);
        tt.setInterpolator(Interpolator.LINEAR);
        tt.play();
    }
}
