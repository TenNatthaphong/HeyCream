package com.heycream.gui;


import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class BackgroundBase 
{
    // =====================
    // SECTION: Methods
    // =====================
    public static void setupBase(Pane backgroundLayer)
    {
        ImageView bg = new ImageView(new Image(
            BackgroundBase.class.getResource("/com/heycream/assets/Nature.png").toExternalForm()
        ));
        bg.setFitWidth(900);
        bg.setFitHeight(600);

        ImageView cloud = new ImageView(new Image(
            BackgroundBase.class.getResource("/com/heycream/assets/Cloud.png").toExternalForm()
        ));
        cloud.setLayoutY(-10);
        cloud.setOpacity(0.8);

        TranslateTransition move = new TranslateTransition(Duration.seconds(25), cloud);
        move.setFromX(-200);
        move.setToX(1000);
        move.setCycleCount(TranslateTransition.INDEFINITE);
        move.setInterpolator(Interpolator.LINEAR);
        move.play();

        backgroundLayer.getChildren().addAll(bg, cloud);
    }

}
