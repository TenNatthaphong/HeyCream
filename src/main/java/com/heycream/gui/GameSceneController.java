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
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.util.Duration;


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
    private boolean isServing = false;
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
    timeManager = new TimeManager(timeLabel);
    timeManager.startAt(12, 0);
    timeManager.runGameClockRealtime(1);
    // 🔹 เมื่อถึงเวลา 18:00 ให้ปิดร้าน + ขึ้น popup สรุปผล
    timeManager.setOnCloseShop(() -> {
        System.out.println("🕕 ร้านปิดแล้ว — แสดงหน้าสรุปผล!");

        int correct = orderManager.getCorrectServeCount();
        int total = orderManager.getTotalServeCount();
        int money = moneyManager.getTotal();

        // สร้างเอฟเฟกต์ fade out ทั้งจอ
        FadeTransition fade = new FadeTransition(Duration.seconds(1.2), rootPane);
        fade.setFromValue(1.0);
        fade.setToValue(0.6);
        fade.setInterpolator(Interpolator.EASE_BOTH);
        fade.setOnFinished(ev -> {
            // แสดง popup สรุปผล
            ResultPopup popup = new ResultPopup(correct, total, money);
            rootPane.getChildren().add(popup);
            popup.toFront();
        });
        fade.play();
    });

    moneyManager = new MoneyManager();
    orderManager = new OrderManager();
    customerManager = new CustomerManager(customerLayer, uiManager);
    gameManager = new GameManager(timeManager, uiManager, customerManager, moneyManager, orderManager);
    itemManager = new ItemManager(itemLayer);
    itemManager.setGameManager(gameManager);
    interactionManager = new InteractionManager(itemManager, uiManager);
    interactionManager.attachToLayer(itemLayer);
    customerManager.setPatienceHost(itemLayer);
    customerManager.setItemManager(itemManager);
    customerManager.setController(this);
    itemLayer.prefWidthProperty().bind(rootPane.widthProperty());
    itemLayer.prefHeightProperty().bind(rootPane.heightProperty());
    itemLayer.setPickOnBounds(true);
    itemLayer.setMouseTransparent(false);
    spawnCustomerSequence();
    itemLayer.setOnMouseClicked(e -> {
        System.out.println("🖱 CLICK at X=" + e.getX() + ", Y=" + e.getY());
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
    
    public boolean isSpawningCustomer() {
        return isSpawningCustomer;
    }
}
