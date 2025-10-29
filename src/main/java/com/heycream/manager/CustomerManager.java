package com.heycream.manager;

import com.heycream.actor.Customer;
import com.heycream.gui.GameSceneController;
import javafx.animation.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CustomerManager 
{
    // =====================
    // SECTION: Attributes
    // =====================
    private final Pane customerLayer;
    private final UIManager uiManager;
    private ImageView currentCustomerView;
    private Customer currentCustomer;
    private ItemManager itemManager;
    private GameSceneController controller;

    private static final double PAT_W = 20;
    private static final double PAT_H = 50;
    private static final double PAT_X = 530; 
    private static final double PAT_Y = 280;

    private Pane patienceHost; 
    private Rectangle patienceBg;
    private Rectangle patienceFill;
    private Timeline patienceTicker;
    private double patienceRatio = 1.0;
    private Label personalityLabel = new Label();

    // =====================
    // SECTION: Constructor
    // =====================
    public CustomerManager(Pane customerLayer, UIManager uiManager)
    {
        this.customerLayer = customerLayer;
        this.uiManager = uiManager;
    }

    // =====================
    // SECTION: Methods
    // =====================
    
   public void spawnCustomer(Customer customer, Runnable onFinish)
   {
        if (currentCustomerView != null)
        {
            System.out.println("⚠ Customer already exists, skipping spawn");
            return;
        }

        currentCustomer = customer;
        String path = "/com/heycream/assets/Customer" + customer.getName() + ".png";
        Image image = new Image(path);
        ImageView view = new ImageView(image);
        view.setFitHeight(200);
        view.setPreserveRatio(true);

        double startX = -200;
        double endX   = 420;
        view.setLayoutX(startX);
        view.setLayoutY(280);

        customerLayer.getChildren().add(view);
        currentCustomerView = view;
        currentCustomer.setImageView(view);

        TranslateTransition enter = new TranslateTransition(Duration.seconds(2), view);
        enter.setFromX(0);
        enter.setToX(endX - startX);
        enter.setInterpolator(Interpolator.EASE_OUT);

        enter.setOnFinished(e -> 
        {
            System.out.println(customer.getName() + " entered the scene.");
            createPatienceBar();
            int baseSeconds = customer.getBehavior().getPatienceSeconds();
            startPatience(baseSeconds);
            if (onFinish != null) onFinish.run();
        });
        enter.play();
    }

   public void clearCustomer()
   {
        if (currentCustomerView != null)
        {
            customerLayer.getChildren().remove(currentCustomerView);
            currentCustomerView = null;
        }
        currentCustomer = null;
        stopPatience();
        disposePatienceBar();
        System.out.println("Cleared current customer.");
    }
   
    public void leaveScene(Runnable onExit)
    {
        if (currentCustomerView == null)
        {
            if (onExit != null) onExit.run();
            return;
        }
        stopPatience();
        disposePatienceBar();
        itemManager.clearAllPreparedVisuals();
        TranslateTransition exit = new TranslateTransition(Duration.seconds(1.0), currentCustomerView);
        exit.setToX(900 - currentCustomerView.getLayoutX());
        exit.setInterpolator(Interpolator.EASE_IN);
        exit.setOnFinished(e -> 
        {
            customerLayer.getChildren().remove(currentCustomerView);
            currentCustomerView = null;
            currentCustomer = null;

            if (onExit != null) onExit.run();
            if (controller != null && !controller.isSpawningCustomer())
            {
                controller.spawnCustomerSequence();
            }
        });
        exit.play();
    }

    private void createPatienceBar()
    {
        disposePatienceBar(); 
        patienceBg = new Rectangle(PAT_W, PAT_H);
        patienceBg.setFill(Color.web("#ffd6d6"));
        patienceBg.setStroke(Color.web("#7a2121"));
        patienceBg.setStrokeWidth(2.4);
        patienceBg.setArcWidth(10);
        patienceBg.setArcHeight(10);
        patienceBg.setLayoutX(PAT_X);
        patienceBg.setLayoutY(PAT_Y);

        patienceFill = new Rectangle(PAT_W, PAT_H);
        patienceFill.setFill(Color.web("#34c759"));
        patienceFill.setArcWidth(10);
        patienceFill.setArcHeight(10);
        patienceFill.setLayoutX(PAT_X);
        patienceFill.setLayoutY(PAT_Y);

        if (personalityLabel == null)
        {
            personalityLabel = new Label();
        }
        personalityLabel.setLayoutX(PAT_X - 5);
        personalityLabel.setLayoutY(PAT_Y - 25);
        personalityLabel.getStyleClass().clear();
        personalityLabel.getStyleClass().add("personality-label");

        if (currentCustomer != null && currentCustomer.getBehavior() != null)
        {
            var b = currentCustomer.getBehavior();
            if (b.isVIP())
            {
                personalityLabel.setText("VIP");
                personalityLabel.getStyleClass().add("personality-vip");
            } else if (b.isRude())
            {
                personalityLabel.setText("RUDE");
                personalityLabel.getStyleClass().add("personality-rude");
            } else {
                personalityLabel.setText("CALM");
                personalityLabel.getStyleClass().add("personality-calm");
            }
        } else {
            personalityLabel.setText("");
        }

        Pane host = (patienceHost != null ? patienceHost : customerLayer);
        host.getChildren().addAll(patienceBg, patienceFill, personalityLabel);
        patienceBg.toFront();
        patienceFill.toFront();
        personalityLabel.toFront();
    }
    
    private void startPatience(int totalSeconds)
    {
        stopPatience();
        if (totalSeconds <= 0) totalSeconds = 1;

        final int fps = 20;
        final double dt = 1.0 / (totalSeconds * fps);

        patienceTicker = new Timeline(new KeyFrame(Duration.millis(1000.0 / fps), ev -> 
        {
            patienceRatio -= dt;
            if (patienceRatio < 0.0) patienceRatio = 0.0;
            double h = PAT_H * patienceRatio;
            patienceFill.setHeight(h);
            patienceFill.setLayoutY(PAT_Y + (PAT_H - h));

            if (patienceRatio <= 0.0)
            {
                stopPatience();
                String phrase = (currentCustomer != null)
                        ? currentCustomer.getBehavior().getReactionPhrase(false)
                        : "Time's up!";
                uiManager.showSpeechBubble(phrase, () -> leaveScene(null));
            }
        }));
        patienceTicker.setCycleCount(Animation.INDEFINITE);
        patienceTicker.play();
    }

    private void stopPatience()
    {
        if (patienceTicker != null)
        {
            patienceTicker.stop();
            patienceTicker = null;
        }
    }

    private void disposePatienceBar()
    {
        stopPatience();
        if (patienceHost != null)
        {
            if (patienceFill != null) patienceHost.getChildren().remove(patienceFill);
            if (patienceBg != null) patienceHost.getChildren().remove(patienceBg);
            if (personalityLabel != null) patienceHost.getChildren().remove(personalityLabel);
        } else {
            if (patienceFill != null) customerLayer.getChildren().remove(patienceFill);
            if (patienceBg != null) customerLayer.getChildren().remove(patienceBg);
            if (personalityLabel != null) customerLayer.getChildren().remove(personalityLabel);
        }
        patienceFill = null;
        patienceBg = null;
        personalityLabel = null;
        patienceRatio = 1.0;
    }

    public void forceCloseAndClear(ItemManager itemManager, Runnable after)
    {
        if (uiManager != null)
        {
            try
            { uiManager.closeActiveBubble(); } catch (Exception ignore) {}
        }
        stopPatience();
        disposePatienceBar();
        if (currentCustomerView == null)
        {
            if (itemManager != null) itemManager.clearAllPreparedVisuals();
            if (after != null) after.run();
            return;
        }
        leaveScene(() -> 
        {
            if (itemManager != null) itemManager.clearAllPreparedVisuals();
            if (after != null) after.run();
        });
    }
    
    // Getter , Setter
    public ImageView getCurrentCustomerView() { return currentCustomerView; }
    public Customer getCurrentCustomer() { return currentCustomer; }
    public void setPatienceHost(Pane host) { this.patienceHost = (host != null ? host : customerLayer); }
    public void setItemManager(ItemManager item) { itemManager = item; }
    public double getPatienceRatio() { return patienceRatio; }
    public void setController(GameSceneController controller) { this.controller = controller; }
}
