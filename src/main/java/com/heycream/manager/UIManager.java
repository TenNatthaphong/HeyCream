package com.heycream.manager;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class UIManager {
    private AnchorPane rootPane;
    private Text timeText;
    private Text orderCountText;
    private int orderCount = 0;

    public UIManager(AnchorPane rootPane) {
        this.rootPane = rootPane;
    }

    public void showSpeechBubble(String text, Runnable onOk) {
        AnchorPane popup = new AnchorPane();
        popup.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-background-radius: 15; -fx-border-radius: 15;");
        popup.setLayoutX(180);
        popup.setLayoutY(120);
        popup.setPrefSize(250, 120);

        Label label = new Label(text);
        label.setWrapText(true);
        label.setLayoutX(20);
        label.setLayoutY(20);
        label.setPrefWidth(210);

        Button ok = new Button("OK");
        ok.setLayoutX(100);
        ok.setLayoutY(80);
        ok.setOnAction(e -> {
            rootPane.getChildren().remove(popup);
            if (onOk != null) onOk.run();
        });

        popup.getChildren().addAll(label, ok);
        rootPane.getChildren().add(popup);
    }

    public void showCoinGain(int amount) {
        Text gainText = new Text("+" + amount + "💰");
        gainText.setStyle("-fx-font-size: 22px; -fx-fill: #00cc66; -fx-font-weight: bold;");
        gainText.setLayoutX(760);
        gainText.setLayoutY(40);
        rootPane.getChildren().add(gainText);

        TranslateTransition rise = new TranslateTransition(Duration.seconds(1.5), gainText);
        rise.setByY(-40);
        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), gainText);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        rise.setOnFinished(e -> rootPane.getChildren().remove(gainText));
        rise.play();
        fade.play();
    }
}
