package com.heycream.manager;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.time.LocalTime;

/**
 * TimeManager - controls in-game clock.
 * Automatically updates the label every game minute (per real-time secondsPerMinute).
 */
public class TimeManager {
    private Label timeLabel;
    private LocalTime gameTime;
    private Timeline timeline;
    private Runnable onCloseShop;

    public TimeManager(Label timeLabel) {
        this.timeLabel = timeLabel;
    }

    /** Start clock at given hour and minute. */
    public void startAt(int hour, int minute) {
        this.gameTime = LocalTime.of(hour, minute);
        updateLabel();
    }

    public void setOnCloseShop(Runnable onCloseShop) {
    this.onCloseShop = onCloseShop;
}
    /**
     * Start ticking clock in real-time.
     * @param secondsPerMinute how many *real seconds* represent one in-game minute.
     * Example: 1.0 = 1 real sec = 1 game minute.
     */
    public void runGameClockRealtime(double secondsPerMinute) {
        stop(); // stop old timer if exists

        timeline = new Timeline(new KeyFrame(Duration.seconds(secondsPerMinute), e -> {
            gameTime = gameTime.plusMinutes(1);
            updateLabel();

            // Stop at 18:00 if desired
            if (!gameTime.isBefore(LocalTime.of(12, 30))) {
                stop();
                System.out.println("🕕 ร้านปิดแล้ว!");
                if (onCloseShop != null) {
                    Platform.runLater(onCloseShop);
                }
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play(); // ← This is required!
    }

    /** Stop ticking the clock. */
    public void stop() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
    }

    /** Update label text safely on FX thread. */
    private void updateLabel() {
        if (timeLabel != null && gameTime != null) {
            Platform.runLater(() ->
                timeLabel.setText(String.format("🕒 %02d:%02d", gameTime.getHour(), gameTime.getMinute()))
            );
        }
    }

    public int getCurrentMinute() {
        return gameTime != null ? gameTime.getHour() * 60 + gameTime.getMinute() : 0;
    }

    public String getTime() {
        return gameTime != null
                ? String.format("%02d:%02d", gameTime.getHour(), gameTime.getMinute())
                : "00:00";
    }
}
