package com.heycream.manager;

import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.time.LocalTime;

public class TimeManager {
    private Label timeLabel;
    private LocalTime gameTime;
    private Timeline timeline;

    public TimeManager(Label timeLabel) { this.timeLabel = timeLabel; }

    public void startAt(int hour, int minute) {
        this.gameTime = LocalTime.of(hour, minute);
        updateLabel();
    }

    public void runGameClockRealtime(double secondsPerMinute) {
        stop();
        timeline = new Timeline(new KeyFrame(Duration.seconds(secondsPerMinute), e -> {
            gameTime = gameTime.plusMinutes(1);
            updateLabel();
            if (!gameTime.isBefore(LocalTime.of(18, 0))) stop();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void stop() { if (timeline != null) timeline.stop(); }

    private void updateLabel() {
        if (timeLabel != null && gameTime != null) {
            timeLabel.setText(String.format("🕒 %02d:%02d", gameTime.getHour(), gameTime.getMinute()));
        }
    }

    public int getCurrentMinute() { return gameTime != null ? gameTime.getHour()*60 + gameTime.getMinute() : 0; }
    public String getTime() { return gameTime != null ? String.format("%02d:%02d", gameTime.getHour(), gameTime.getMinute()) : "00:00"; }
}
