package com.heycream.manager;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Handles all in-game UI displays: speech bubbles, hints, toasts, and coin gain effects.
 */
public class UIManager {
    private final Pane uiPane;
    private Label coinLabel;
    private Pane activeBubble;

    public UIManager(Pane uiPane) {
        this.uiPane = uiPane;
    }

    /** Optional: link external coin label (for dynamic updates). */
    public void setCoinLabelNode(Label label) {
        this.coinLabel = label;
    }

    /** Update coin label text safely. */
    public void updateCoinLabel(int total) {
        if (coinLabel != null) {
            coinLabel.setText(String.format("%d 💰", total));
        }
    }

    /** Show a fading text message at top-center. */
    public void toast(String message) {
        Text text = new Text(message);
        text.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-fill: white;");
        text.setLayoutX(360);
        text.setLayoutY(100);
        uiPane.getChildren().add(text);

        FadeTransition fade = new FadeTransition(Duration.seconds(2.0), text);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(e -> uiPane.getChildren().remove(text));
        fade.play();
    }

    /** Flash small hint near bottom (shorter duration). */
    public void flashHint(String hint) {
        Text text = new Text(hint);
        text.setStyle("-fx-font-size: 18px; -fx-fill: yellow -fx-font-weight: bold;;");
        text.setLayoutX(375);
        text.setLayoutY(150);
        uiPane.getChildren().add(text);

        FadeTransition fade = new FadeTransition(Duration.seconds(2), text);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.setOnFinished(e -> uiPane.getChildren().remove(text));
        fade.play();
    }

    /** 💬 Display speech bubble with OK button */
    public void showSpeechBubble(String text, Runnable onOk) {
        if (activeBubble != null) {
        uiPane.getChildren().remove(activeBubble);
        activeBubble = null;
    }
        Pane popup = new Pane();
        popup.getStyleClass().add("speech-bubble");
        popup.setPrefSize(300, 130);
        popup.setLayoutX(150);
        popup.setLayoutY(130);

        Label label = new Label(text);
        label.getStyleClass().add("speech-text");
        label.setWrapText(true);
        label.setLayoutX(20);
        label.setLayoutY(20);
        label.setPrefWidth(270);

        Button ok = new Button("OK");
        ok.getStyleClass().add("speech-ok-btn");
        ok.setLayoutX(245);
        ok.setLayoutY(90);
        ok.setOnAction(e -> {
            uiPane.getChildren().remove(popup);
            activeBubble = null;
            if (onOk != null) onOk.run();
        });
        popup.getChildren().addAll(label, ok);
        uiPane.getChildren().add(popup);
        activeBubble = popup;
    }
    public void closeActiveBubble() {
    if (activeBubble != null) {
        uiPane.getChildren().remove(activeBubble);
        activeBubble = null;
    }
}

    /** 💰 Show floating coin gain text near cashier */
    public void showCoinFloat(int amount) {
        String prefix = amount >= 0 ? "+" : "";
        String color = amount >= 0 ? "#00cc66" : "#ff3333";

        Text gainText = new Text(prefix + amount + " 💰");
        gainText.setStyle(String.format(
            "-fx-font-size: 22px; -fx-fill: %s; -fx-font-weight: bold;", color
        ));
        gainText.setLayoutX(720);
        gainText.setLayoutY(300);
        uiPane.getChildren().add(gainText);

        // 🔹 Floating + fading animation
        TranslateTransition rise = new TranslateTransition(Duration.seconds(2), gainText);
        rise.setByY(-40);
        FadeTransition fade = new FadeTransition(Duration.seconds(2), gainText);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        rise.setOnFinished(e -> uiPane.getChildren().remove(gainText));
        rise.play();
        fade.play();
    }
}
