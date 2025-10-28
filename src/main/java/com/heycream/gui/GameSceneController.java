package com.heycream.gui;

import com.heycream.AbstractAndInterface.CustomerBehavior;
import com.heycream.actor.*;
import com.heycream.gui.*;
import com.heycream.manager.*;
import com.heycream.model.Order;
import com.heycream.utils.Randomizer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.Random;
import javafx.scene.layout.Pane;

/**
 * GameSceneController - main gameplay logic for HeyCream.
 * Includes customer spawn sequence and behavior integration.
 */
public class GameSceneController {

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

    private final Random random = new Random();
    private boolean isServing = false;

    @FXML
    public void initialize() {
        BackgroundBase.setupBase(backgroundLayer);
        FoodTruckLayer.setupTruck(truckLayer);
        customerManager = new CustomerManager(customerLayer, uiManager);
        orderManager = new OrderManager();
        timeManager = new TimeManager(timeLabel);
        timeManager.startAt(12, 0);
        timeManager.runGameClockRealtime(1.0);
        spawnCustomerSequence();
    }


    /**
     * Customer loop: spawn, interact, serve, and repeat.
     */
    private void spawnCustomerSequence() {
        String[] names = {"Cat", "Dog", "Pig", "Tiger", "Elephant"};
        String name = names[random.nextInt(names.length)];

        // Random behavior using your provided static method
        CustomerBehavior behavior = Randomizer.randomBehavior();

        // Generate an empty or placeholder order
        Order order = orderManager.generateOrder();

        // Use current in-game time as arrival minute
        int arrivalMinute = timeManager.getCurrentMinute();

        // Create the customer with full constructor
        Customer customer = new Customer(name, order, behavior, arrivalMinute);

        // Animate entry
        customerManager.spawnCustomer(customer, () -> {
            System.out.println("✅ " + name + " (" + behavior.getClass().getSimpleName() + ") arrived at " + arrivalMinute);
            uiManager.showSpeechBubble(customer.getSpeech(), null);
            waitForServe(customer);
        });
    }

    /**
     * Waits for player serve action (simulated delay for now).
     */
    private void waitForServe(Customer currentCustomer) {
        // Behavior can influence patience time
        int waitSeconds = currentCustomer.getBehavior().getPatienceSeconds();
        System.out.println("🕒 " + currentCustomer.getName() + " will wait " + waitSeconds + "s.");

        new Thread(() -> {
            try {
                Thread.sleep(waitSeconds * 1000L);
            } catch (InterruptedException ignored) {}

            javafx.application.Platform.runLater(() -> {
                serveCustomer(currentCustomer);
            });
        }).start();
    }

    /**
     * Called when serving an ice cream to current customer.
     */
    private void serveCustomer(Customer customer) {
        if (isServing) return;
        isServing = true;

        // Behavior decides reaction text
        String reaction = customer.getBehavior().getReactionPhrase();
        System.out.println("🍦 Served " + customer.getName() + " -> " + reaction);

        uiManager.showSpeechBubble(reaction, () -> {
            // Leave scene and spawn next one
            customerManager.leaveScene(() -> {
                isServing = false;
                spawnCustomerSequence();
            });
        });
    }
}
