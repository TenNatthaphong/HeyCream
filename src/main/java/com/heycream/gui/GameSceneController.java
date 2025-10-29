package com.heycream.gui;

import com.heycream.AbstractAndInterface.CustomerBehavior;
import com.heycream.actor.*;
import com.heycream.manager.*;
import com.heycream.model.Order;
import com.heycream.utils.Randomizer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.util.Duration;
import java.util.Random;

public class GameSceneController {

    @FXML private AnchorPane rootPane;
    @FXML private Pane backgroundLayer;
    @FXML private Pane truckLayer;
    @FXML private Pane customerLayer;
    @FXML private Pane itemLayer;
    @FXML private Pane uiLayer;

    @FXML private Label timeLabel;
    @FXML private Label coinLabel;

    private TimeManager timeManager;
    private UIManager uiManager;
    private OrderManager orderManager;
    private ItemManager itemManager;
    private CustomerManager customerManager;
    private InteractionManager interactionManager;
    private GameManager gameManager;
    private MoneyManager moneyManager;

    private final Random random = new Random();
    private boolean isSpawningCustomer = false;

    // =======================================================
    // 🔹 INITIALIZE
    // =======================================================
    @FXML
    public void initialize() {
        BackgroundBase.setupBase(backgroundLayer);
        FoodTruckLayer.setupTruck(truckLayer);

        uiManager = new UIManager(uiLayer);
        uiManager.setCoinLabelNode(coinLabel);

        moneyManager = new MoneyManager();
        orderManager = new OrderManager();
        customerManager = new CustomerManager(customerLayer, uiManager);
        itemManager = new ItemManager(itemLayer);
        interactionManager = new InteractionManager(itemManager, uiManager);

        timeManager = new TimeManager(timeLabel);
        timeManager.startAt(12, 0);
        timeManager.runGameClockRealtime(1);
        timeManager.setOnCloseShop(this::onShopClosed);

        gameManager = new GameManager(timeManager, uiManager, customerManager, moneyManager, orderManager);
        itemManager.setGameManager(gameManager);
        customerManager.setPatienceHost(itemLayer);
        customerManager.setItemManager(itemManager);
        customerManager.setController(this);
        interactionManager.attachToLayer(itemLayer);

        itemLayer.prefWidthProperty().bind(rootPane.widthProperty());
        itemLayer.prefHeightProperty().bind(rootPane.heightProperty());
        itemLayer.setPickOnBounds(true);
        itemLayer.setMouseTransparent(false);

        spawnCustomerSequence();

        itemLayer.setOnMouseClicked(e -> {
            String item = itemManager.detectItemByPosition(e.getX(), e.getY());
            if (item != null) handleItemClick(item);
        });

        backgroundLayer.toBack();
        customerLayer.toFront();
        truckLayer.toFront();
        itemLayer.toFront();
        uiLayer.toFront();

        System.out.println("🎮 Game initialized successfully!");
    }

    // =======================================================
    // 🔹 Handle Item Click
    // =======================================================
    private void handleItemClick(String itemName) {
        if (itemName == null) return;

        if (itemName.startsWith("Cup") || itemName.equals("Cone")) {
            if (itemManager.getCurrentCup() == null) itemManager.spawnCup(itemName);
            else System.out.println("⚠ Cup already placed!");
            return;
        }

        if (itemName.startsWith("Scoop")) { itemManager.addScoopToCup(itemName); return; }
        if (itemName.startsWith("Topping")) { itemManager.addToppingToCup(itemName); return; }
        if (itemName.startsWith("Sauce")) { itemManager.addSauceToCup(itemName); return; }

        if (itemName.equals("ServeZone") || itemName.equals("CupArea")) {
            itemManager.serveCurrentCup();
            return;
        }

        System.out.println("❓ Unhandled item: " + itemName);
    }

    // =======================================================
    // 🔹 Customer Management
    // =======================================================
    public void spawnCustomerSequence() {
        if (isSpawningCustomer) return;
        isSpawningCustomer = true;

        String[] names = {"Cat", "Dog", "Pig", "Tiger", "Elephant"};
        String name = names[random.nextInt(names.length)];
        CustomerBehavior behavior = Randomizer.randomBehavior();
        Order order = orderManager.generateOrder();
        int arrivalMinute = timeManager.getCurrentMinute();

        Customer customer = new Customer(name, order, behavior, arrivalMinute);
        customerManager.spawnCustomer(customer, () -> {
            uiManager.showSpeechBubble(customer.getSpeech(), () -> isSpawningCustomer = false);
        });
    }

    // =======================================================
    // 🔹 When shop closes
    // =======================================================
    public void onShopClosed() {
        System.out.println("🏁 The shop is now closed!");

        // 🔹 Block new actions
        itemLayer.setMouseTransparent(true);
        isSpawningCustomer = true;

        // 🔹 Force all visuals/patience clear before showing popup
        customerManager.forceCloseAndClear(itemManager, () -> {
            int totalMoney = moneyManager.getTotal();
            int totalOrders = orderManager.getTotalOrderCount();
            int correctOrders = orderManager.getCorrectServeCount();

            // 🔹 Overlay fade-in
            Pane dim = new Pane();
            dim.setStyle("-fx-background-color: rgba(0,0,0,0.5);");
            dim.setPrefSize(rootPane.getWidth(), rootPane.getHeight());
            dim.prefWidthProperty().bind(rootPane.widthProperty());
            dim.prefHeightProperty().bind(rootPane.heightProperty());
            rootPane.getChildren().add(dim);
            dim.toFront();

            FadeTransition dimFade = new FadeTransition(Duration.seconds(1), dim);
            dimFade.setFromValue(0);
            dimFade.setToValue(1);
            dimFade.setInterpolator(Interpolator.EASE_OUT);
            dimFade.play();

            // 🔹 Show summary popup (after overlay animation)
            dimFade.setOnFinished(ev -> {
                javafx.application.Platform.runLater(() -> {
                    ResultPopup popup = new ResultPopup(
                        correctOrders,
                        totalOrders,
                        totalMoney,
                        this::restartGame,
                        this::goToMainMenu
                    );

                    // ✅ จัด popup ให้อยู่กลางหน้าจอเสมอ
                    popup.setLayoutX((rootPane.getWidth() - popup.getPrefWidth()) / 2);
                    popup.setLayoutY((rootPane.getHeight() - popup.getPrefHeight()) / 2);

                    rootPane.getChildren().add(popup);
                    popup.toFront();

                    System.out.println("✅ ResultPopup displayed!");
                });
            });
        });
    }


    // =======================================================
    // 🔹 Restart
    // =======================================================
// =======================================================
// 🔹 Restart Day (ใช้งานได้จริง)
// =======================================================
private void restartGame() {
    System.out.println("🔄 Restarting game...");

    // 🔹 1️⃣ Fade out ก่อนรีเซ็ต (ให้ overlay มืดค่อย ๆ หายไป)
    FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.8), rootPane);
    fadeOut.setFromValue(0.6);  // ตอนนี้จอมี overlay มืด 60%
    fadeOut.setToValue(1.0);    // ค่อย ๆ สว่างกลับมาเต็มจอ
    fadeOut.setInterpolator(Interpolator.EASE_OUT);

    fadeOut.setOnFinished(ev -> {
        // 🔹 2️⃣ ลบ popup + overlay สีดำทั้งหมดออก
        rootPane.getChildren().removeIf(node ->
            (node instanceof ResultPopup) ||
            (node instanceof Pane &&
             node.getStyle() != null &&
             node.getStyle().toLowerCase().contains("rgba(0,0,0,0.5)"))
        );

        // 🔹 3️⃣ รีเซ็ตระบบทั้งหมด
        moneyManager.addMoney(-moneyManager.getTotal());
        orderManager.resetStats();
        customerManager.clearCustomer();
        itemManager.clearAllPreparedVisuals();

        // 🔹 4️⃣ รีเซ็ตเวลาใหม่
        timeManager.stop();
        timeManager.startAt(12, 0);
        timeManager.runGameClockRealtime(1);

        // 🔹 5️⃣ Spawn ลูกค้าใหม่
        isSpawningCustomer = false;
        spawnCustomerSequence();

        // 🔹 6️⃣ เปิดให้คลิกใหม่อีกครั้ง
        itemLayer.setMouseTransparent(false);

        System.out.println("✅ Game restarted successfully!");
    });

    fadeOut.play();
}


    // =======================================================
    // 🔹 Back to menu
    // =======================================================
// =======================================================
// 🔹 Back to main menu (ใช้งานได้จริง)
// =======================================================
private void goToMainMenu() {
    System.out.println("🏠 Going back to main menu...");
    try {
        javafx.stage.Stage stage = (javafx.stage.Stage) rootPane.getScene().getWindow();

        // ✅ ปิด clock และเคลียร์ระบบก่อนออก
        timeManager.stop();
        customerManager.clearCustomer();
        itemManager.clearAllPreparedVisuals();

        // ✅ เปลี่ยน Scene ไปหน้าเมนู
        SceneFactory.show(stage, "/com/heycream/gui/fxml/main_menu.fxml");
        System.out.println("✅ Returned to main menu.");
    } catch (Exception e) {
        System.err.println("❌ Failed to return to main menu: " + e.getMessage());
        e.printStackTrace();
    }
}
public boolean isSpawningCustomer() {
        return isSpawningCustomer;
    }
}
