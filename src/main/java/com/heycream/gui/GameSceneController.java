package com.heycream.gui;


import com.heycream.AbstractAndInterface.CustomerBehavior;
import com.heycream.actor.*;
import com.heycream.manager.*;
import com.heycream.model.Order;
import com.heycream.utils.Randomizer;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GameSceneController 
{
    // =====================
    // SECTION: Attribute
    // =====================
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
    
    // =====================
    // SECTION: Methods
    // =====================
    @FXML
    public void initialize()
    {
        BackgroundBase.setupBase(backgroundLayer);
        FoodTruckLayer.setupTruck(truckLayer);

        uiManager = new UIManager(uiLayer);
        moneyManager = new MoneyManager();
        orderManager = new OrderManager();
        itemManager = new ItemManager(itemLayer);
        customerManager = new CustomerManager(customerLayer, uiManager,itemManager);
        interactionManager = new InteractionManager(itemManager, uiManager);
        timeManager = new TimeManager(timeLabel);
        gameManager = new GameManager(uiManager, customerManager, moneyManager, orderManager);
        
        timeManager.startAt(10, 0);
        timeManager.runGameClockRealtime(0.5);
        timeManager.setOnCloseShop(this::onShopClosed);
        uiManager.setCoinLabelNode(coinLabel);
        itemManager.setGameManager(gameManager);
        customerManager.setPatienceHost(itemLayer);
        customerManager.setController(this);
        interactionManager.attachToLayer(itemLayer);

        itemLayer.prefWidthProperty().bind(rootPane.widthProperty());
        itemLayer.prefHeightProperty().bind(rootPane.heightProperty());
        itemLayer.setPickOnBounds(true);
        itemLayer.setMouseTransparent(false);

        spawnCustomerSequence();

        customerManager.setOnCustomerExit(() -> 
        {
            isSpawningCustomer = false;
            spawnCustomerSequence(); 
        });
        itemLayer.setOnMouseClicked(e -> 
        {
            String item = itemManager.detectItemByPosition(e.getX(), e.getY());
            if (item != null) handleItemClick(item);
        });

        backgroundLayer.toBack();
        customerLayer.toFront();
        truckLayer.toFront();
        itemLayer.toFront();
        uiLayer.toFront();

        System.out.println("Game initialized successfully!");
    }
    
    private void handleItemClick(String itemName)
    {
        if (itemName == null) return;

        if (itemName.startsWith("Cup") || itemName.equals("Cone"))
        {
            if (itemManager.getCurrentCup() == null) itemManager.spawnCup(itemName);
            else System.out.println("Cup already placed!");
            return;
        }

        if (itemName.startsWith("Scoop")) { itemManager.addScoopToCup(itemName); return; }
        if (itemName.startsWith("Topping")) { itemManager.addToppingToCup(itemName); return; }
        if (itemName.startsWith("Sauce")) { itemManager.addSauceToCup(itemName); return; }
        if (itemName.equals("ServeZone")) { itemManager.serveCurrentCup(); return; }
        System.out.println("Unhandled item: " + itemName);
    }

    public void spawnCustomerSequence()
    {
        if (isSpawningCustomer) return;
        isSpawningCustomer = true;

        String name = Randomizer.randomName();
        CustomerBehavior behavior = Randomizer.randomBehavior();
        Order order = orderManager.generateOrder();
        int arrivalMinute = timeManager.getCurrentMinute();

        Customer customer = new Customer(name, order, behavior, arrivalMinute);
        customerManager.spawnCustomer(customer, () ->
        {
            uiManager.showSpeechBubble(customer.getSpeech(), () -> isSpawningCustomer = false);
        });
    }

    public boolean isSpawningCustomer()
    {
        return isSpawningCustomer;
    }
    
    public void onShopClosed()
    {
        System.out.println("The shop is now closed!");

        itemLayer.setMouseTransparent(true);
        isSpawningCustomer = true;

        customerManager.forceCloseAndClear(itemManager, () -> 
        {
            int totalMoney = moneyManager.getTotal();
            int totalOrders = orderManager.getTotalOrderCount();
            int correctOrders = orderManager.getCorrectServeCount();

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

            dimFade.setOnFinished(ev -> 
            {
                javafx.application.Platform.runLater(() -> 
                {
                    ResultPopup popup = new ResultPopup(
                        correctOrders,
                        totalOrders,
                        totalMoney,
                        this::restartGame,
                        this::goToMainMenu
                    );
                    popup.setLayoutX((rootPane.getWidth() - popup.getPrefWidth()) / 2);
                    popup.setLayoutY((rootPane.getHeight() - popup.getPrefHeight()) / 2);
                    rootPane.getChildren().add(popup);
                    popup.toFront();
                    System.out.println("ResultPopup displayed!");
                });
            });
        });
    }

    private void restartGame()
    {
        System.out.println("Restarting game...");

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.8), rootPane);
        fadeOut.setFromValue(0.6); 
        fadeOut.setToValue(1.0);
        fadeOut.setInterpolator(Interpolator.EASE_OUT);

        fadeOut.setOnFinished(ev -> 
        {
            rootPane.getChildren().removeIf(node ->
                (node instanceof ResultPopup) ||
                (node instanceof Pane &&
                 node.getStyle() != null &&
                 node.getStyle().toLowerCase().contains("rgba(0,0,0,0.5)"))
            );
            
            moneyManager.addMoney(-moneyManager.getTotal());
            orderManager.resetStats();
            customerManager.clearCustomer();
            itemManager.clearAllPreparedVisuals();
            timeManager.stop();
            timeManager.startAt(10, 0);
            timeManager.runGameClockRealtime(0.5);
            isSpawningCustomer = false;
            spawnCustomerSequence();
            itemLayer.setMouseTransparent(false);
            System.out.println("Game restarted successfully!");
        });
        fadeOut.play();
    }

    private void goToMainMenu()
    {
        System.out.println("Going back to main menu...");
        try
        {
            javafx.stage.Stage stage = (javafx.stage.Stage) rootPane.getScene().getWindow();

            timeManager.stop();
            customerManager.clearCustomer();
            itemManager.clearAllPreparedVisuals();

            SceneFactory.show(stage, "/com/heycream/gui/fxml/main_menu.fxml");
            System.out.println("Returned to main menu.");
        }
        catch (Exception e)
        {
            System.err.println("Failed to return to main menu: " + e.getMessage());
        }
    }
    
}
