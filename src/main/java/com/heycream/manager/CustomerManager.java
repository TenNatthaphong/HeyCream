package com.heycream.manager;

import com.heycream.actor.*;
import javafx.animation.*;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class CustomerManager {
    private final Pane customerLayer;
    private final UIManager uiManager;
    private ImageView currentCustomerView;

    public CustomerManager(Pane customerLayer, UIManager uiManager)
    {
        this.customerLayer = customerLayer;
        this.uiManager = uiManager;
    }

    public ImageView getCurrentCustomerView() { return currentCustomerView; }

    public void spawnCustomer(Customer customer, Runnable onArrived)
    {
        String path = "/com/heycream/assets/Customer" + customer.getName() + ".png";
        Image image;
        try {
            image = new Image(path);
        } catch (Exception e) {
            System.err.println("⚠ Failed to load customer image: " + e.getMessage());
            image = new Image("about:blank");
        }
        ImageView img = new ImageView(image);
        img.setFitHeight(200);
        img.setPreserveRatio(true);
        img.setLayoutY(280); 
        double startX = -200;
        double endX = 420;
        img.setLayoutX(startX);
        customerLayer.getChildren().add(img); 
        currentCustomerView = img;
        TranslateTransition enter = new TranslateTransition(Duration.seconds(2), img);
        enter.setFromX(0);
        enter.setToX(endX - startX);
        enter.setInterpolator(Interpolator.EASE_OUT);

        enter.setOnFinished(e -> {
            System.out.println("✅ Customer arrived: " + customer.getName());
            if (onArrived != null) onArrived.run();
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
            customerLayer.getChildren().remove(currentCustomerView);
            currentCustomerView = null;
            if (onExit != null) onExit.run();
        });
        exit.play();
    }
}
