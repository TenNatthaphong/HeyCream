package com.heycream.gui;

import com.heycream.AbstractAndInterface.CustomerBehavior;
import com.heycream.actor.*;
import com.heycream.manager.*;
import com.heycream.model.Cup;
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
    private boolean isSpawningCustomer = false;

    // =======================================================
    // 🔹 INITIALIZE
    // =======================================================
   @FXML
public void initialize() {
    // 1️⃣ Setup พื้นหลังและรถขายไอติม
    BackgroundBase.setupBase(backgroundLayer);
    FoodTruckLayer.setupTruck(truckLayer);

    // 2️⃣ Initialize UI + CoinLabel
    uiManager = new UIManager(uiLayer);
    uiManager.setCoinLabelNode(coinLabel); // เพิ่มเพื่อให้ UIManager อัปเดตเงินได้

    // 3️⃣ Initialize Time
    timeManager = new TimeManager(timeLabel);
    timeManager.startAt(12, 0);
    timeManager.runGameClockRealtime(0.75);

    // 4️⃣ Initialize Managers หลัก
    orderManager = new OrderManager();
    customerManager = new CustomerManager(customerLayer, uiManager);
    gameManager = new GameManager(timeManager, uiManager, customerManager);
    gameManager.setController(this);
    itemManager = new ItemManager(itemLayer);
    itemManager.setGameManager(gameManager);

    // 5️⃣ Interaction Manager (ระบบคลิก)
    interactionManager = new InteractionManager(itemManager, uiManager);
    interactionManager.attachToLayer(itemLayer);

    // 6️⃣ ให้ itemLayer ปรับขนาดอัตโนมัติตาม rootPane
    itemLayer.prefWidthProperty().bind(rootPane.widthProperty());
    itemLayer.prefHeightProperty().bind(rootPane.heightProperty());
    itemLayer.setPickOnBounds(true);
    itemLayer.setMouseTransparent(false);

    // 7️⃣ Debug: ตรวจคลิกและ log พิกัด
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

    // 8️⃣ Layer Order (ให้แน่ใจว่าทับถูก)
    backgroundLayer.toBack();
    customerLayer.toFront();
    truckLayer.toFront();
    itemLayer.toFront();
    uiLayer.toFront();

    // 9️⃣ Spawn ลูกค้าคนแรก
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
    public void spawnCustomerSequence() {
    if (isSpawningCustomer) {
        System.out.println("⚠️ Spawn blocked: already spawning a customer.");
        return;
    }
    isSpawningCustomer = true;

    String[] names = {"Cat", "Dog", "Pig", "Tiger", "Elephant"};
    String name = names[random.nextInt(names.length)];
    CustomerBehavior behavior = Randomizer.randomBehavior();
    Order order = orderManager.generateOrder();
    int arrivalMinute = timeManager.getCurrentMinute();

    Customer customer = new Customer(name, order, behavior, arrivalMinute);

    customerManager.spawnCustomer(customer, () -> {
        System.out.println("✅ " + name + " (" + behavior.getClass().getSimpleName() + ") arrived at " + arrivalMinute);
        uiManager.showSpeechBubble(customer.getSpeech(), () -> {
            isSpawningCustomer = false;
        });
    });
}


    // =======================================================
    // 🔹 Serve Logic (manual)
    // =======================================================
    private void serveCustomer(Customer customer) {
    if (isServing) return;
    isServing = true;

    Cup servedCup = itemManager.getCurrentCup();
    if (servedCup == null) {
        System.out.println("⚠ No cup to serve!");
        isServing = false;
        return;
    }
    boolean correct = orderManager.checkMatch(servedCup, customer.getOrder());
    System.out.println("🍦 Served " + customer.getName() + " → Correct = " + correct);
    String reaction = customer.getBehavior().getReactionPhrase(correct);
    uiManager.showSpeechBubble(reaction, () -> {
        int delta = gameManager.getMoneyManager().calculateReward(customer, correct);
        gameManager.getMoneyManager().addMoney(delta);

        uiManager.showCoinFloat(delta);

        itemManager.clearAllPreparedVisuals();

        customerManager.leaveScene(() -> {
            isServing = false;
            spawnCustomerSequence();
        });
    });
}
    
    public boolean isSpawningCustomer() {
        return isSpawningCustomer;
    }
}
