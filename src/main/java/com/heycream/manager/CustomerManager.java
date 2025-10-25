package com.heycream.manager;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class CustomerManager {
    private AnchorPane rootPane;
    private ImageView currentCustomer;

    public CustomerManager(AnchorPane rootPane) {
        this.rootPane = rootPane;
    }

    public void spawnCustomer(String name, Runnable onArrived) {
        ImageView customer = new ImageView(
            new Image(getClass().getResource("/com/heycream/assets/Customer" + name + ".png").toExternalForm())
        );
        customer.setLayoutX(120);
        customer.setLayoutY(230);
        customer.setOpacity(0);
        customer.setFitHeight(180);
        customer.setPreserveRatio(true);

        rootPane.getChildren().add(customer);
        currentCustomer = customer;

        FadeTransition fade = new FadeTransition(Duration.seconds(2), customer);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setOnFinished(e -> {
            if (onArrived != null) onArrived.run();
        });
        fade.play();
    }
}
