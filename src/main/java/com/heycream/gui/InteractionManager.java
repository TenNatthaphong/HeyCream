package com.heycream.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class InteractionManager {
    private AnchorPane rootPane;
    private ImageView heldItem;

    public InteractionManager(AnchorPane rootPane) {
        this.rootPane = rootPane;
    }

    public void enableCupDrag() {
        ImageView cup = new ImageView(new Image(getClass().getResource("/com/heycream/assets/CupS.png").toExternalForm()));
        cup.setFitWidth(60);
        cup.setPreserveRatio(true);
        cup.setLayoutX(200);
        cup.setLayoutY(460);

        rootPane.getChildren().add(cup);

        cup.setOnMousePressed(e -> {
            heldItem = new ImageView(cup.getImage());
            heldItem.setFitWidth(60);
            heldItem.setPreserveRatio(true);
            rootPane.getChildren().add(heldItem);
        });

        cup.setOnMouseDragged(e -> {
            if (heldItem != null) {
                heldItem.setLayoutX(e.getSceneX() - 30);
                heldItem.setLayoutY(e.getSceneY() - 30);
            }
        });

        cup.setOnMouseReleased(e -> {
            if (heldItem != null) {
                heldItem.setLayoutX(700);
                heldItem.setLayoutY(420);
                rootPane.getChildren().add(heldItem);
                heldItem = null;
            }
        });
    }
}
