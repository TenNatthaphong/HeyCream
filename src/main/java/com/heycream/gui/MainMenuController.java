package com.heycream.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuController implements HasRootPane {

    @FXML private AnchorPane rootPane;
    @FXML private Pane backgroundLayer;
    @FXML private Button startButton;
    @FXML private Button exitButton;

    @FXML
    public void initialize() {
        // 🌤️ ใช้พื้นหลังเดียวกับเกม
        BackgroundBase.setupBase(backgroundLayer);

        // 🍦 โลโก้
        ImageView logo = new ImageView(new Image(
                getClass().getResource("/com/heycream/assets/Logo.png").toExternalForm()
        ));
        logo.setFitWidth(420);
        logo.setPreserveRatio(true);
        logo.setLayoutX((900 - 450) / 2);
        logo.setLayoutY(-30);
        rootPane.getChildren().add(logo);

        // 🎮 ปุ่ม (ใช้สไตล์จาก CSS)
        double buttonWidth = 160;
        double buttonHeight = 45;
        double x = (900 - buttonWidth) / 2 - 10; // เลื่อนซ้าย 10px

        startButton.setPrefSize(buttonWidth, buttonHeight);
        exitButton.setPrefSize(buttonWidth, buttonHeight);

        startButton.setLayoutX(x);
        startButton.setLayoutY(310);
        exitButton.setLayoutX(x);
        exitButton.setLayoutY(370);

        // 🎀 ใช้คลาสจาก CSS
        startButton.getStyleClass().add("primary-btn");
        exitButton.getStyleClass().add("secondary-btn");

        // ⚙️ Action ปุ่ม
        startButton.setOnAction(e ->
            SceneFactory.show((Stage) startButton.getScene().getWindow(),
                "/com/heycream/gui/fxml/game_scene.fxml")
        );

        exitButton.setOnAction(e -> Platform.exit());

        // ✅ Layer order
        backgroundLayer.toBack();
        logo.toFront();
        startButton.toFront();
        exitButton.toFront();

        System.out.println("✅ Main Menu styled with CSS hover effects.");
    }

    @Override
    public AnchorPane getRootPane() { return rootPane; }
}
