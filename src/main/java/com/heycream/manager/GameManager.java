package com.heycream.manager;

import com.heycream.actor.*;
import com.heycream.model.*;
import javafx.scene.layout.AnchorPane;

public class GameManager {
    private final AnchorPane rootPane;
    private final TimeManager timeManager;
    private final UIManager uiManager;
    private final OrderManager orderManager;
    private final MoneyManager moneyManager;
    private final CustomerManager customerManager;
    private Customer currentCustomer;

    public GameManager(AnchorPane rootPane, TimeManager timeManager, UIManager uiManager) {
        this.rootPane = rootPane;
        this.timeManager = timeManager;
        this.uiManager = uiManager;
        this.orderManager = new OrderManager();
        this.moneyManager = new MoneyManager();
        this.customerManager = new CustomerManager(rootPane, uiManager);
    }

    public void spawnNextCustomer() {
        currentCustomer = orderManager.generateCustomer();
        customerManager.spawnCustomer(currentCustomer, () -> {});
    }

    public void resolveServe(Cup playerCup, Runnable onAfterLeave) {
        if (currentCustomer == null) return;
        boolean correct = orderManager.isOrderCorrect(playerCup, currentCustomer);
        int delta = moneyManager.calculateReward(currentCustomer, correct);
        moneyManager.addMoney(delta);
        uiManager.showCoinGain(delta);
        customerManager.leaveScene(onAfterLeave);
    }

    public MoneyManager getMoneyManager() { return moneyManager; }
    public Customer getCurrentCustomer() { return currentCustomer; }
}
