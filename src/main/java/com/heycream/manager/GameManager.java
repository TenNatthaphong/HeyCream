package com.heycream.manager;

import com.heycream.actor.Customer;
import com.heycream.gui.GameSceneController;
import com.heycream.model.Cup;

public class GameManager {
    private final TimeManager timeManager;
    private final UIManager uiManager;
    private final OrderManager orderManager;
    private final CustomerManager customerManager;
    private final MoneyManager moneyManager;

    public GameManager(TimeManager timeManager, UIManager uiManager, CustomerManager customerManager, MoneyManager moneyManager,OrderManager orderManager) {
        this.timeManager = timeManager;
        this.uiManager = uiManager;
        this.orderManager = orderManager;
        this.customerManager = customerManager;
        this.moneyManager = moneyManager;
    }

    public MoneyManager getMoneyManager() {
        return moneyManager;
    }

    public void resolveServe(Cup servedCup, Runnable onClear) {
        Customer current = customerManager.getCurrentCustomer();
        if (current == null) {
            System.out.println("⚠ No current customer to serve.");
            return;
        }

        // ✅ ตรวจว่าทำ order ถูกหรือไม่
        boolean correct = orderManager.checkMatch(servedCup, current.getOrder());
        // ✅ คำนวณรางวัล
        double patienceRatio = customerManager.getPatienceRatio();
        int delta = moneyManager.calculateReward(current, servedCup, correct, patienceRatio);
        moneyManager.addMoney(delta);
        uiManager.showCoinFloat(delta);
        uiManager.updateCoinLabel(moneyManager.getTotal());

        String phrase = current.getBehavior().getReactionPhrase(correct);
        uiManager.showSpeechBubble(phrase, () -> {
            customerManager.leaveScene(() -> {
                if (onClear != null) onClear.run();
            });
        });
    }
}
