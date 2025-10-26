package com.heycream.manager;

import com.heycream.actor.Customer;
import com.heycream.utils.Randomizer;
import java.util.Objects;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class CustomerManager {
    private final AnchorPane rootPane;
    private final UIManager uiManager;
    private ImageView currentCustomer;

    public CustomerManager(AnchorPane rootPane, UIManager uiManager) {
        this.rootPane = rootPane;
        this.uiManager = uiManager;
    }

    public ImageView getCurrentCustomerView() {
    return currentCustomer;
}

    public void spawnCustomer(Customer customer, Runnable onArrived) {
    String name = customer.getName();
    String path = "/com/heycream/assets/Customer" + name + ".png";

    ImageView img = new ImageView(
        new Image(Objects.requireNonNull(getClass().getResource(path),
        "Missing asset: " + path).toExternalForm())
    );
    img.setFitHeight(350);
    img.setPreserveRatio(true);
    img.setLayoutY(640);

    double startX = (name.equals("Tiger") || name.equals("Elephant")) ? 1536 : -400;
    double endX = 1100;

    img.setLayoutX(startX);
    rootPane.getChildren().add(img);
    img.toBack();
    currentCustomer = img;

    TranslateTransition enter = new TranslateTransition(Duration.seconds(3), img);
    enter.setFromX(0);
    enter.setToX(endX - startX);
    enter.setInterpolator(Interpolator.EASE_OUT);
    enter.setOnFinished(e -> onArrived.run());
    String msg = Randomizer.randomOrderMessage();
    uiManager.showSpeechBubble(msg, () -> {
        System.out.println("🗨️ Customer ready to be served.");
    });
    enter.play();
}


    public void leaveScene(Runnable onExit) {
        if (currentCustomer == null) return;

        double exitX = currentCustomer.getLayoutX() > 400 ? 900 : -200;
        TranslateTransition exit = new TranslateTransition(Duration.seconds(2), currentCustomer);
        exit.setToX(exitX - currentCustomer.getLayoutX());
        exit.setInterpolator(Interpolator.EASE_IN);
        exit.setOnFinished(e -> {
            rootPane.getChildren().remove(currentCustomer);
            currentCustomer = null;
            onExit.run();
        });
        exit.play();
    }
}
