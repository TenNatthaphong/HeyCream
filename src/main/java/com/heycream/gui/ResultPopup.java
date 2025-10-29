package com.heycream.gui;

import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ResultPopup extends Pane {
    public ResultPopup(int correct, int total, int money,
                       Runnable onRestart, Runnable onBack) {

        // 🔹 Base style
        setPrefSize(400, 300);
        setStyle("""
            -fx-background-color: rgba(255, 240, 245, 0.92);
            -fx-border-color: #ff7fbf;
            -fx-border-width: 3;
            -fx-background-radius: 20;
            -fx-border-radius: 20;
            """);
        setLayoutX(250);
        setLayoutY(300); // เริ่มต่ำหน่อยสำหรับ slide-up

        
        // 🔹 Title
        Label title = new Label("Day Summary");
        title.setStyle("""
            -fx-font-size: 26px;
            -fx-font-weight: bold;
            -fx-text-fill: #ff4fa3;
            """);
        title.setLayoutX(110);
        title.setLayoutY(30);

        // 🔹 Stars
        int stars = computeStars(money);
        Label starLabel = new Label(getStarsSymbol(stars));
        starLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: gold;");
        starLabel.setLayoutX(155);
        starLabel.setLayoutY(80);

        // 🔹 Stats text
        Text stats = new Text(String.format(
            "⭐ Correct orders: %d / %d\n💰 Total money: %d coins",
            correct, total, money
        ));
        stats.setStyle("-fx-font-size: 18px; -fx-fill: #333;");
        stats.setLayoutX(80);
        stats.setLayoutY(150);

        // 🔹 Buttons
        Button restart = new Button("Restart Day");
        restart.setStyle("""
            -fx-font-size: 16px;
            -fx-background-color: #8ad7ff;
            -fx-text-fill: #003366;
            -fx-background-radius: 12;
            -fx-pref-width: 120;
            """);
        restart.setLayoutX(70);
        restart.setLayoutY(220);
        restart.setOnAction(e -> { if (onRestart != null) onRestart.run(); });

        Button back = new Button("Back to Menu");
        back.setStyle("""
            -fx-font-size: 16px;
            -fx-background-color: #ff8fb3;
            -fx-text-fill: white;
            -fx-background-radius: 12;
            -fx-pref-width: 120;
            """);
        back.setLayoutX(220);
        back.setLayoutY(220);
        back.setOnAction(e -> { if (onBack != null) onBack.run(); });

        getChildren().addAll(title, starLabel, stats, restart, back);

        // 🔹 Fade + Slide animation
        setOpacity(0);
        TranslateTransition slideUp = new TranslateTransition(Duration.seconds(0.6), this);
        slideUp.setFromY(80);
        slideUp.setToY(0);
        slideUp.setInterpolator(Interpolator.EASE_OUT);
        slideUp.setAutoReverse(false);
        
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.8), this);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ParallelTransition appear = new ParallelTransition(slideUp, fadeIn);
        appear.play();
    }

    // ==========================================================
    // 🔸 Helper: คำนวณจำนวนดาวจากยอดเงิน
    // ==========================================================
    private int computeStars(int money) {
        if (money >= 850) return 3;
        if (money >= 600) return 2;
        if (money >= 300) return 1;
        return 0;
    }

    // ==========================================================
    // 🔸 Helper: สร้างสัญลักษณ์ดาว
    // ==========================================================
    private String getStarsSymbol(int stars) {
        return switch (stars) {
            case 3 -> "★★★";
            case 2 -> "★★☆";
            case 1 -> "★☆☆";
            default -> "☆☆☆";
        };
    }
}
