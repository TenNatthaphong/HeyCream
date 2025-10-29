package com.heycream.gui;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class FoodTruckLayer 
{
    // =====================
    // SECTION: Methods
    // =====================
    public static void setupTruck(Pane truckLayer)
    {
        ImageView truck = new ImageView(new Image(
            FoodTruckLayer.class.getResource("/com/heycream/assets/FoodTruck.png").toExternalForm()
        ));
        truck.setFitWidth(900);
        truck.setFitHeight(600);
        truckLayer.getChildren().add(truck);
    }

}
