package com.heycream.manager;

import java.time.LocalTime;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class TimeManager 
{
    // =====================
    // SECTION: Attributes
    // ===================== 
    private Label timeLabel;
    private LocalTime gameTime;
    private Timeline timeline;
    private Runnable onCloseShop;

    // =====================
    // SECTION: Constructor
    // ===================== 
    public TimeManager(Label timeLabel)
    {
        this.timeLabel = timeLabel;
    }

    // =====================
    // SECTION: Methods
    // ===================== 
    public void startAt(int hour, int minute)
    {
        this.gameTime = LocalTime.of(hour, minute);
        updateLabel();
    }
  
    public void runGameClockRealtime(double secondsPerMinute)
    {
        stop();
        timeline = new Timeline(new KeyFrame(Duration.seconds(secondsPerMinute), e -> 
        {
            gameTime = gameTime.plusMinutes(1);
            updateLabel();
            if (!gameTime.isBefore(LocalTime.of(16,0)))
            {
                stop();
                System.out.println("🕕 Time's up!");
                if (onCloseShop != null) onCloseShop.run();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void stop()
    {
        if (timeline != null)
        {
            timeline.stop();
            timeline = null;
        }
    }

    private void updateLabel()
    {
        if (timeLabel != null && gameTime != null)
        {
            Platform.runLater(() ->
                timeLabel.setText(String.format("🕒 %02d:%02d", gameTime.getHour(), gameTime.getMinute()))
            );
        }
    }

    // Getter , Setter
    public int getCurrentMinute()
    {
        return gameTime != null ? gameTime.getHour() * 60 + gameTime.getMinute() : 0;
    }
    public String getTime()
    {
        return gameTime != null
                ? String.format("%02d:%02d", gameTime.getHour(), gameTime.getMinute())
                : "00:00";
    }
    public void setOnCloseShop(Runnable onCloseShop)
    {
        this.onCloseShop = onCloseShop;
    }
    
}
