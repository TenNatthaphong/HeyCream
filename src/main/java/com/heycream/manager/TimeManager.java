package com.heycream.manager;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalTime;

public class TimeManager {
    private final Label timeLabel;
    private LocalTime gameTime;
    private Timeline timeline;

    public TimeManager(Label timeLabel) {
        this.timeLabel = timeLabel;
    }

    public void startAt(int hour, int minute) {
        this.gameTime = LocalTime.of(hour, minute);
        updateLabel();
    }
    public void runGameClockRealtime(double speedSecondsPerMinute) {
        stop();
        timeline = new Timeline(new KeyFrame(Duration.seconds(speedSecondsPerMinute), e -> {
            gameTime = gameTime.plusMinutes(1);
            updateLabel();
            if (!gameTime.isBefore(LocalTime.of(21, 0))) {
                stop();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void stop() {
        if (timeline != null) timeline.stop();
    }

    private void updateLabel() {
        timeLabel.setText(String.format("🕒 %02d:%02d", gameTime.getHour(), gameTime.getMinute()));
    }
    public String getTime() {
        return String.format("%02d:%02d", gameTime.getHour(), gameTime.getMinute());
    }
    public int getCurrentMinute() {
        return (gameTime.getHour() - 12) * 60 + gameTime.getMinute();
    }
}
