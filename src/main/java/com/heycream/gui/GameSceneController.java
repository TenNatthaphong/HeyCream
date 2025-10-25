package com.heycream.gui;

import com.heycream.manager.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Random;

public class GameSceneController implements Initializable {

    @FXML
    private AnchorPane rootPane;

    private UIManager uiManager;
    private CustomerManager customerManager;
    private InteractionManager interactionManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            setupBackground();
            uiManager = new UIManager(rootPane);
            customerManager = new CustomerManager(rootPane);
            interactionManager = new InteractionManager(rootPane);

            // เริ่มต้น spawn ลูกค้า
            spawnNewCustomer();
        });
    }

    private void setupBackground() {
        ImageView bg = new ImageView(
            new Image(getClass().getResource("/com/heycream/assets/BackgroundAndFoodTruck.png").toExternalForm())
        );
        bg.setFitWidth(900);
        bg.setFitHeight(600);
        bg.setLayoutX(0);
        bg.setLayoutY(0);
        rootPane.getChildren().add(bg);
    }

    private void spawnNewCustomer() {
        String[] names = {"Pig", "Cat", "Dog", "Tiger", "Elephant"};
        String randomName = names[new Random().nextInt(names.length)];

        customerManager.spawnCustomer(randomName, () -> {
            uiManager.showSpeechBubble("I'd like Strawberry & Matcha, please!", () -> {
                uiManager.showOrderAdded();
                // ทดลองถือ cup
                interactionManager.enableCupDrag();
            });
        });
    }
}
