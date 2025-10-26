package com.heycream.gui;

import com.heycream.AbstractAndInterface.CalmCustomer;
import com.heycream.AbstractAndInterface.CustomerBehavior;
import com.heycream.actor.Customer;
import com.heycream.manager.CustomerManager;
import com.heycream.manager.InteractionManager;
import com.heycream.manager.ItemManager;
import com.heycream.manager.OrderManager;
import com.heycream.manager.TimeManager;
import com.heycream.utils.Randomizer;
import com.heycream.model.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class GameSceneController {

    @FXML private AnchorPane rootPane;     // 900 x 600
    @FXML private ImageView foodTruck;     // จะถูก set รูป + scale ที่นี่
    @FXML private Label timeLabel;
    @FXML private Label coinLabel;

    private CustomerManager customerManager;
    private TimeManager timeManager;
    private ItemManager itemManager;
    private OrderManager orderManager;
    private InteractionManager interactionManager;
    private ImageView heldItem = null; // currently held item
    private String heldKey = null;      

    @FXML
    public void initialize() {
        BackgroundBase.setupBase(rootPane);  
        rootPane.setPrefSize(900, 600);
        foodTruck.setImage(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/com/heycream/assets/FoodTruck.png"),
                "Missing: /com/heycream/assets/FoodTruck.png")));
        foodTruck.setFitWidth(900);  
        foodTruck.setPreserveRatio(true);
        foodTruck.setLayoutX(0);  
        foodTruck.setLayoutY(0);
        foodTruck.toBack();
        customerManager = new CustomerManager(rootPane);
        timeManager = new TimeManager(timeLabel);
        itemManager = new ItemManager(rootPane);
        timeManager.startAt(12, 0);
        timeManager.runGameClockRealtime(1.0);
        orderManager = new OrderManager(timeManager);
        setupItemInteractions();
        String name = Randomizer.randomName();            
        Order order = orderManager.generateOrder();      
        CustomerBehavior behavior = Randomizer.randomBehavior();    
        int arrivalMinute = timeManager.getCurrentMinute();
        Customer first = orderManager.generateCustomer();
        itemManager = new ItemManager(rootPane);
        interactionManager = new InteractionManager(rootPane, itemManager);

        rootPane.setOnMouseReleased(e -> {
    double x = e.getX(), y = e.getY();

        if (itemManager.isInServeZone(x, y)) {
            System.out.println("✅ ServeZone: serve order");
            serveOrder();
        } else if (itemManager.isInStackZone(x, y)) {
            System.out.println("🍦 StackZone: placed scoop");
            stackScoop();
        } else if (itemManager.isInCupZone(x, y)) {
            System.out.println("🧁 CupZone: placed cup or cone");
            placeCup();
        }else {
        System.out.println("🖱️ Clicked outside any defined zone: (" + x + ", " + y + ")");
        }
    });
        forceZOrder();
    }
    private void placeCup() 
    {
    System.out.println("🧁 [CupZone] -> Placed cup/cone at the counter.");
    }
    private void stackScoop() 
    {
    System.out.println("🍦 [StackZone] -> Added scoop layer on cup/cone.");
    // TODO: Later - check if cup exists, then add a new scoop layer (up to 3)
    }
    private void serveOrder() 
    {
    System.out.println("✅ [ServeZone] -> Served order to customer!");
    // TODO: Later - check order completeness, play sound, remove order from counter
    }
    private void setupItemInteractions() {
        rootPane.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            if (heldItem != null) {
                // allow placing only in ServeZone (cashier area)
                if (itemManager.isInServeZone(x, y)) {
                    System.out.println("✅ Placed " + heldKey + " in ServeZone.");
                    heldItem.setLayoutX(x - heldItem.getFitWidth() / 2);
                    heldItem.setLayoutY(y - heldItem.getFitHeight() / 2);
                    heldItem = null;
                    heldKey = null;
                } else {
                    // if click outside ServeZone, do nothing (still holding)
                    System.out.println("❌ Cannot place outside ServeZone.");
                }
                return;
            }
            String detected = itemManager.detectItemByPosition(x, y);
            if (detected == null) return; // clicked empty space

            // Handle scoop logic (special rule)
            if (detected.startsWith("ScoopWhen")) {
                if (heldKey != null && heldKey.equals("Scoop")) {
                    // scoop held -> allow flavor change
                    System.out.println("🍨 Scoop changed to " + detected);
                    heldKey = detected;
                    if (heldItem != null) rootPane.getChildren().remove(heldItem);
                    heldItem = itemManager.showItem(detected);
                    return;
                } else {
                    // not holding scoop, cannot take flavored scoop
                    System.out.println("⚠ Cannot take flavored scoop without holding Scoop.");
                    return;
                }
            }

            // If click Scoop area (washing scoop)
            if (detected.equals("Scoop")) {
                System.out.println("🧺 Picked up clean scoop.");
                heldKey = "Scoop";
                if (heldItem != null) rootPane.getChildren().remove(heldItem);
                heldItem = itemManager.showItem("Scoop");
                bindHeldItemToMouse();
                return;
            }
            System.out.println("🖐 Holding new item: " + detected);
            heldKey = detected;
            if (heldItem != null) rootPane.getChildren().remove(heldItem);
            heldItem = itemManager.showItem(detected);
            bindHeldItemToMouse();
        });
    }
    private void bindHeldItemToMouse() {
        rootPane.setOnMouseMoved(m -> {
            if (heldItem != null) {
                heldItem.setLayoutX(m.getX() - heldItem.getFitWidth() / 2);
                heldItem.setLayoutY(m.getY() - heldItem.getFitHeight() / 2);
            }
        });
    }
    private void forceZOrder() {
        if (customerManager.getCurrentCustomerView() != null) {
            customerManager.getCurrentCustomerView().toBack();
        }
        foodTruck.toFront();
        timeLabel.toFront();  
        coinLabel.toFront(); 
    }
}
