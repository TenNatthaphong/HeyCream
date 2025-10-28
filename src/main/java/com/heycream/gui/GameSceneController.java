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
import java.util.Random;
import javafx.application.Platform;

/**
 * GameSceneController - main gameplay logic for HeyCream.
 * Handles initialization, customer spawning, and item interactions.
 */
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

    private final Random random = new Random();
    private boolean isServing = false;

    // =======================================================
    // 🔹 INITIALIZE
    // =======================================================
    @FXML
public void initialize() {
    // 1️⃣ Setup พื้นหลังและรถขายไอติม
    BackgroundBase.setupBase(backgroundLayer);
    FoodTruckLayer.setupTruck(truckLayer);

    // 2️⃣ Initialize UI และ Time
    uiManager = new UIManager(uiLayer);
    timeManager = new TimeManager(timeLabel);
    timeManager.startAt(12, 0);
    timeManager.runGameClockRealtime(0.75);

    // 3️⃣ Initialize Managers หลัก
    orderManager = new OrderManager();
    itemManager = new ItemManager(itemLayer);
    customerManager = new CustomerManager(customerLayer, uiManager);
    gameManager = new GameManager(rootPane, timeManager, uiManager);

    // 4️⃣ เชื่อมโยง managers
    itemManager.setGameManager(gameManager);
    interactionManager = new InteractionManager(itemManager, uiManager);
    interactionManager.attachToLayer(itemLayer);

    // 5️⃣ ให้ itemLayer ปรับขนาดอัตโนมัติตาม rootPane
    itemLayer.prefWidthProperty().bind(rootPane.widthProperty());
    itemLayer.prefHeightProperty().bind(rootPane.heightProperty());
    itemLayer.setPickOnBounds(true);
    itemLayer.setMouseTransparent(false);

    Platform.runLater(() -> {
        itemLayer.setOnMouseClicked(e -> {
            System.out.println("🖱 CLICK detected on itemLayer at X=" + e.getX() + ", Y=" + e.getY());
            String item = itemManager.detectItemByPosition(e.getX(), e.getY());
            if (item != null) {
                System.out.println("✅ Detected item: " + item);
                handleItemClick(item);
            } else {
                System.out.println("❌ No item detected at that position");
            }
        });
    });

    // 7️⃣ จัดลำดับ Layer ให้ถูกต้อง
    backgroundLayer.toBack();
    customerLayer.toFront();
    truckLayer.toFront();
    itemLayer.toFront();
    uiLayer.toFront();

    // 8️⃣ เริ่ม spawn ลูกค้าคนแรก
    spawnCustomerSequence();

    System.out.println("🎮 Game initialized successfully!");
}


    // =======================================================
    // 🔹 Handle Item Click
    // =======================================================
    private void handleItemClick(String itemName) {
        if (itemName == null) return;

        if (itemName.startsWith("Cup") || itemName.equals("Cone")) {
            if (itemManager.getCurrentCup() == null) {
                itemManager.spawnCup(itemName);
            } else {
                System.out.println("⚠ Cup already placed!");
            }
            return;
        }

        if (itemName.startsWith("Scoop")) {
            itemManager.addScoopToCup(itemName);
            return;
        }

        if (itemName.startsWith("Topping")) {
            itemManager.addToppingToCup(itemName);
            return;
        }

        if (itemName.startsWith("Sauce")) {
            itemManager.addSauceToCup(itemName);
            return;
        }

        // ✅ serve logic (when click CupArea or ServeZone)
        if (itemName.equals("ServeZone") || itemName.equals("CupArea")) {
            itemManager.serveCurrentCup();
            return;
        }

        System.out.println("❓ Unhandled item: " + itemName);
    }

    // =======================================================
    // 🔹 Customer Management
    // =======================================================
    private void spawnCustomerSequence() {
        String[] names = {"Cat", "Dog", "Pig", "Tiger", "Elephant"};
        String name = names[random.nextInt(names.length)];
        CustomerBehavior behavior = Randomizer.randomBehavior();
        Order order = orderManager.generateOrder();
        int arrivalMinute = timeManager.getCurrentMinute();

        Customer customer = new Customer(name, order, behavior, arrivalMinute);
        customerManager.spawnCustomer(customer, () -> {
            System.out.println("✅ " + name + " (" + behavior.getClass().getSimpleName() + ") arrived at " + arrivalMinute);
            uiManager.showSpeechBubble(customer.getSpeech(), null);
        });
    }

    // =======================================================
    // 🔹 Serve Logic (manual)
    // =======================================================
    private void serveCustomer(Customer customer) {
        if (isServing) return;
        isServing = true;

        String reaction = customer.getBehavior().getReactionPhrase();
        System.out.println("🍦 Served " + customer.getName() + " -> " + reaction);

        uiManager.showSpeechBubble(reaction, () -> {
            customerManager.leaveScene(() -> {
                isServing = false;
                spawnCustomerSequence();
            });
        });
    }
}
