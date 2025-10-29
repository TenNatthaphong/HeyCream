package com.heycream.gui;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ResultPopup extends StackPane {

    private final Label titleLabel;
    private final Label starLabel;
    private final Label moneyLabel;
    private final Label summaryLabel;
    private final Button okButton;

    public ResultPopup(int correctServes, int totalServes, int totalMoney) {
        setPrefSize(500, 400);
        setStyle("-fx-background-color: rgba(255,255,255,0.95); -fx-background-radius: 20; "
               + "-fx-border-radius: 20; -fx-border-color: #ff8fb3; -fx-border-width: 3;");

        // Overlay มืดหลัง popup
        Rectangle overlay = new Rectangle(900, 600, Color.rgb(0,0,0,0.5));

        titleLabel = new Label("🍦 สรุปผลประจำวัน 🍦");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #ff6fa0;");

        // ★ คำนวณดาว
        int stars = calculateStars(correctServes, totalServes, totalMoney);
        starLabel = new Label("⭐".repeat(stars) + "☆".repeat(3 - stars));
        starLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: gold;");

        moneyLabel = new Label("รายได้ทั้งหมด: " + totalMoney + " 💰");
        moneyLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #333;");

        int wrong = totalServes - correctServes;
        summaryLabel = new Label("เสิร์ฟถูกและทัน: " + correctServes + " / พลาด: " + wrong);
        summaryLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #555;");

        okButton = new Button("OK");
        okButton.getStyleClass().add("primary-btn");
        okButton.setOnAction(e -> ((Pane) getParent()).getChildren().remove(this));

        VBox box = new VBox(20, titleLabel, starLabel, moneyLabel, summaryLabel, okButton);
        box.setAlignment(Pos.CENTER);
        getChildren().addAll(overlay, box);

        // สไลด์ขึ้น
        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.8), this);
        slide.setFromY(600);
        slide.setToY(0);
        slide.setInterpolator(Interpolator.EASE_OUT);
        slide.play();
    }

    private int calculateStars(int correctServes, int totalServes, int totalMoney) {
        if (totalServes == 0) return 0;
        double ratio = (double) correctServes / totalServes;
        double incomeFactor = totalMoney / (double) (totalServes * 30); // เฉลี่ยเงินต่อ order ~30

        double score = (ratio * 0.7) + (incomeFactor * 0.3);

        if (score >= 0.85) return 3;
        else if (score >= 0.6) return 2;
        else if (score >= 0.3) return 1;
        else return 0;
    }
}
