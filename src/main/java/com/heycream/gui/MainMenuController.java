package com.heycream.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuController implements HasRootPane 
{
    // =====================
    // SECTION: Attributes
    // =====================
    @FXML private AnchorPane rootPane;
    @FXML private Pane backgroundLayer;
    @FXML private Button startButton;
    @FXML private Button exitButton;

    // =====================
    // SECTION: Methods
    // =====================
    @FXML
    public void initialize()
    {
        BackgroundBase.setupBase(backgroundLayer);
        ImageView logo = new ImageView(new Image(
                getClass().getResource("/com/heycream/assets/Logo.png").toExternalForm()
        ));
        logo.setFitWidth(420);
        logo.setPreserveRatio(true);
        logo.setLayoutX((900 - 450) / 2);
        logo.setLayoutY(-30);
        rootPane.getChildren().add(logo);
        double buttonWidth = 160;
        double buttonHeight = 45;
        double x = (900 - buttonWidth) / 2 - 10; 

        startButton.setPrefSize(buttonWidth, buttonHeight);
        exitButton.setPrefSize(buttonWidth, buttonHeight);

        startButton.setLayoutX(x);
        startButton.setLayoutY(310);
        exitButton.setLayoutX(x);
        exitButton.setLayoutY(370);

        startButton.getStyleClass().add("primary-btn");
        exitButton.getStyleClass().add("secondary-btn");

        startButton.setOnAction(e ->
            SceneFactory.show((Stage) startButton.getScene().getWindow(),
                "/com/heycream/gui/fxml/game_scene.fxml")
        );

        exitButton.setOnAction(e -> Platform.exit());

        backgroundLayer.toBack();
        logo.toFront();
        startButton.toFront();
        exitButton.toFront();
    }

    @Override
    public AnchorPane getRootPane() { return rootPane; }
}
