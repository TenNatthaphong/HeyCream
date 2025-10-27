package com.heycream.manager;

import com.heycream.actor.*;
import javafx.animation.*;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class CustomerManager {
    private final AnchorPane rootPane;
    private final UIManager uiManager;
    private ImageView currentCustomerView;

    public CustomerManager(AnchorPane rootPane, UIManager uiManager) {
        this.rootPane = rootPane;
        this.uiManager = uiManager;
    }

    public ImageView getCurrentCustomerView() { return currentCustomerView; }

    public void spawnCustomer(Customer customer, Runnable onArrived) {
        String path = "/com/heycream/assets/Customer" + customer.getName() + ".png";
        Image image;
        try { image = new Image(path); } catch (Exception e) { image = new Image("about:blank"); }
        ImageView img = new ImageView(image);
        img.setFitHeight(200);
        img.setPreserveRatio(true);
        img.setLayoutY(280);
        double startX = -200, endX = 450;
        img.setLayoutX(startX);
        rootPane.getChildren().add(img);
        currentCustomerView = img;

        TranslateTransition enter = new TranslateTransition(Duration.seconds(2), img);
        enter.setFromX(0);
        enter.setToX(endX - startX);
        enter.setInterpolator(Interpolator.EASE_OUT);
        enter.setOnFinished(e -> {
            if (onArrived != null) onArrived.run();
            uiManager.showSpeechBubble(customer.getSpeech(), null);
        });
        enter.play();
    }

    public void leaveScene(Runnable onExit) {
        if (currentCustomerView == null) { if (onExit != null) onExit.run(); return; }
        double exitTo = 900;
        TranslateTransition exit = new TranslateTransition(Duration.seconds(1.8), currentCustomerView);
        exit.setToX(exitTo - currentCustomerView.getLayoutX());
        exit.setInterpolator(Interpolator.EASE_IN);
        exit.setOnFinished(e -> {
            rootPane.getChildren().remove(currentCustomerView);
            currentCustomerView = null;
            if (onExit != null) onExit.run();
        });
        exit.play();
    }
}
