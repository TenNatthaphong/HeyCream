package com.heycream.manager;

import com.heycream.actor.Customer;
import com.heycream.model.Cup;
import com.heycream.gui.*;

public class GameManager {

    private final TimeManager timeManager;
    private final UIManager uiManager;
    private final OrderManager orderManager;
    private final MoneyManager moneyManager;
    private final CustomerManager customerManager;
    private GameSceneController gameSceneController;

    

    public GameManager(TimeManager timeManager, UIManager uiManager, CustomerManager customerManager) {
        this.timeManager = timeManager;
        this.uiManager = uiManager;
        this.orderManager = new OrderManager();
        this.moneyManager = new MoneyManager();
        this.customerManager = customerManager;
    }

    public MoneyManager getMoneyManager() { return moneyManager; }

    public void resolveServe(Cup servedCup, Runnable onClear) {
    Customer current = customerManager.getCurrentCustomer();
    if (current == null) {
        System.out.println("⚠ No current customer to serve.");
        return;
    }

    // ✅ ตรวจว่าทำ order ถูกหรือไม่
    boolean correct = orderManager.checkMatch(servedCup, current.getOrder());

    // ✅ คำนวณรางวัล
    int delta = moneyManager.calculateReward(current, correct);
    moneyManager.addMoney(delta);

    // ✅ แสดงผลรางวัล
    uiManager.showCoinFloat(delta);
    uiManager.updateCoinLabel(moneyManager.getTotal());

    // ✅ ลูกค้าพูดตามผลลัพธ์ (ถูก/ผิด)
    String phrase = current.getBehavior().getReactionPhrase(correct);
    uiManager.showSpeechBubble(phrase, () -> {

        // ✅ หลังจากพูดเสร็จให้ลูกค้าเดินออก
        customerManager.leaveScene(() -> {
            if (onClear != null) onClear.run();

            // ✅ ป้องกันการ spawn ซ้ำ
            if (gameSceneController != null) {
                if (!gameSceneController.isSpawningCustomer()) {
                    gameSceneController.spawnCustomerSequence();
                } else {
                    System.out.println("⚠ Spawn blocked: already spawning a customer.");
                }
            } else {
                System.out.println("⚠ Controller not linked, cannot spawn next customer.");
            }
        });
    });
}



    public void setController(GameSceneController controller) {
        this.gameSceneController = controller;
    }

    private int estimateElapsedSeconds(Customer cust) {
       
        if (timeManager == null) return 0;
        int nowMin = timeManager.getCurrentMinute();
        int arrivalMin = cust.getArrivalMinute();
        int dMin = Math.max(0, nowMin - arrivalMin);
        return dMin * 60;
    }
}
