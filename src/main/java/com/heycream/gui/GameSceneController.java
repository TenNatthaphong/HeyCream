package com.heycream.gui;

import com.heycream.manager.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class GameSceneController {

    // === FXML nodes ===
    @FXML private AnchorPane rootPane;   // <- ต้องมีใน FXML: fx:id="rootPane"
    @FXML private ImageView  foodTruck;
    @FXML private Label      timeLabel;
    @FXML private Label      coinLabel;

    // === Managers ===
    private UIManager uiManager;
    private TimeManager timeManager;
    private ItemManager itemManager;
    private InteractionManager interactionManager;
    private GameManager gameManager;

    @FXML
    public void initialize() {
        // --- Scene base ---
        rootPane.setPrefSize(900, 600);
        BackgroundBase.setupBase(rootPane); // ใส่พื้นหลัง + เมฆ

        // --- Food truck image (front layer) ---
        if (foodTruck != null) {
            String path = "/com/heycream/assets/FoodTruck.png";
            Image truck = new Image(Objects.requireNonNull(
                    getClass().getResource(path), "Missing asset: " + path
            ).toExternalForm());
            foodTruck.setImage(truck);
            // วางให้พอดีกับดีไซน์ 900x600 (ภาพแผงควบคุมอยู่ล่าง)
            foodTruck.setFitWidth(900);
            foodTruck.setPreserveRatio(true);
            foodTruck.setLayoutX(0);
            foodTruck.setLayoutY(0);
            foodTruck.toFront();
        }

        // --- Managers ---
        uiManager = new UIManager(rootPane);

        timeManager = new TimeManager(timeLabel);
        timeManager.startAt(12, 0);              // เริ่มเวลา 12:00
        timeManager.runGameClockRealtime(1.0);   // เดินทุก 1 วินาที = 1 นาทีในเกม

        itemManager = new ItemManager(rootPane);
        interactionManager = new InteractionManager(rootPane, itemManager);

        gameManager = new GameManager(rootPane, timeManager, uiManager);
        gameManager.spawnNextCustomer();

        // --- Mouse interaction (pick/place zones) ---
        rootPane.setOnMouseClicked(e ->
                interactionManager.pickOrPlace(e.getX(), e.getY())
        );

        // --- HUD always on top ---
        if (timeLabel != null) timeLabel.toFront();
        if (coinLabel != null) coinLabel.toFront();
        if (foodTruck != null) foodTruck.toFront();
    }
}
