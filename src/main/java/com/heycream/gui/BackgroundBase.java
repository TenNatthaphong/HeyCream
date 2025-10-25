/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.gui;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class BackgroundBase {

    public static void setupBase(AnchorPane rootPane) {
    AnchorPane bgLayer = new AnchorPane();

    ImageView background = new ImageView(
        new Image(BackgroundBase.class.getResource("/com/heycream/assets/Nature.png").toExternalForm())
    );
    background.setFitWidth(900);
    background.setFitHeight(600);

    ImageView cloud = new ImageView(
        new Image(BackgroundBase.class.getResource("/com/heycream/assets/Cloud.png").toExternalForm())
    );
    cloud.setLayoutY(-10);
    cloud.setOpacity(0.8);
    cloud.setTranslateX(-200);

    TranslateTransition move = new TranslateTransition(Duration.seconds(25), cloud);
    move.setFromX(-200);
    move.setToX(1000);
    move.setCycleCount(TranslateTransition.INDEFINITE);
    move.setInterpolator(Interpolator.LINEAR);


    bgLayer.getChildren().addAll(background, cloud);
    rootPane.getChildren().add(0, bgLayer); 

    move.play();
}

}

