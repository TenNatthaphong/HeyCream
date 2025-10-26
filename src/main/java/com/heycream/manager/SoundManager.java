package com.heycream.manager;

import javafx.application.Platform;
import javafx.scene.media.AudioClip;

import java.net.URL;
import java.util.Objects;

public class SoundManager {
    private static AudioClip orderBell;
    private static AudioClip cashChaChing;
    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        if (!Platform.isFxApplicationThread()) 
        {
            Platform.runLater(SoundManager::init);
            return;
        }
        try {
            URL bellUrl = Objects.requireNonNull(
                SoundManager.class.getResource("/com/heycream/assets/sfx/order_bell.wav"),
                "Missing /com/heycream/assets/sfx/order_bell.wav");
            URL cashUrl = Objects.requireNonNull(
                SoundManager.class.getResource("/com/heycream/assets/sfx/cash.wav"),
                "Missing /com/heycream/assets/sfx/cash.wav");

            orderBell = new AudioClip(bellUrl.toExternalForm());
            cashChaChing = new AudioClip(cashUrl.toExternalForm());

            orderBell.setVolume(0.7);
            cashChaChing.setVolume(0.8);

            initialized = true;
            System.out.println("[SFX] Initialized");
        } catch (Exception e) {
            System.err.println("[SFX] Init failed: " + e.getMessage());
        }
    }

    public static void playOrderBell() {
        if (orderBell != null) orderBell.play();
    }

    public static void playCash() {
        if (cashChaChing != null) cashChaChing.play();
    }
}
