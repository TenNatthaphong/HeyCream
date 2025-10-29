package com.heycream.gui;

import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ResultPopup extends Pane
{
    // =====================
    // SECTION: Constructor
    // =====================
    public ResultPopup(int correct, int total, int money, Runnable onRestart, Runnable onBack)
    {
        setPrefSize(400, 300);
        getStyleClass().add("result-box");
        setLayoutX(250);
        setLayoutY(300);

        Label title = new Label("Day Summary");
        title.getStyleClass().add("result-title");
        title.setLayoutX(70);
        title.setLayoutY(30);

        int stars = computeStars(money);
        Label starLabel = new Label(getStarsSymbol(stars));
        starLabel.getStyleClass().add("result-stars");
        starLabel.setLayoutX(145);
        starLabel.setLayoutY(90);

        Text orderText = new Text(String.format("Correct orders: %d / %d", correct, total));
        orderText.getStyleClass().add("result-orders");
        orderText.setLayoutX(90);
        orderText.setLayoutY(170);

        Text moneyText = new Text(String.format("Total money: %d coins", money));
        moneyText.getStyleClass().add("result-money");
        moneyText.setLayoutX(90);
        moneyText.setLayoutY(210);

        Button restart = new Button("Restart");
        restart.getStyleClass().addAll("result-btn", "result-btn-restart");
        restart.setLayoutX(70);
        restart.setLayoutY(240);
        restart.setOnAction(e -> { if (onRestart != null) onRestart.run(); });

        Button back = new Button("Back to Menu");
        back.getStyleClass().addAll("result-btn", "result-btn-back");
        back.setLayoutX(220);
        back.setLayoutY(240);
        back.setOnAction(e -> { if (onBack != null) onBack.run(); });

        getChildren().addAll(title, starLabel, orderText, moneyText, restart, back);

        // 🔹 Animation
        setOpacity(0);
        TranslateTransition slideUp = new TranslateTransition(Duration.seconds(0.6), this);
        slideUp.setFromY(80);
        slideUp.setToY(0);
        slideUp.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.8), this);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        new ParallelTransition(slideUp, fadeIn).play();
    }

    // =====================
    // SECTION: Methods
    // =====================
    private int computeStars(int money)
    {
        if (money >= 250) return 3;
        if (money >= 200) return 2;
        if (money >= 100) return 1;
        return 0;
    }

    private String getStarsSymbol(int stars)
    {
        return switch (stars)
        {
            case 3 -> "★★★";
            case 2 -> "★★☆";
            case 1 -> "★☆☆";
            default -> "☆☆☆";
        };
    }
}
