package com.heycream.manager;

import com.heycream.actor.Customer;
import com.heycream.model.Cup;

public class GameManager 
{
    // =====================
    // SECTION: Attributes
    // =====================    
    private final UIManager uiManager;
    private final OrderManager orderManager;
    private final CustomerManager customerManager;
    private final MoneyManager moneyManager;

    // =====================
    // SECTION: Constructor
    // =====================   
    public GameManager(UIManager uiManager, CustomerManager customerManager, MoneyManager moneyManager,OrderManager orderManager)
    {
        this.uiManager = uiManager;
        this.orderManager = orderManager;
        this.customerManager = customerManager;
        this.moneyManager = moneyManager;
    }

    // =====================
    // SECTION: Methods
    // =====================   
    public void resolveServe(Cup servedCup, Runnable onClear)
    {
        Customer current = customerManager.getCurrentCustomer();
        if (current == null)
        {
            System.out.println("⚠ No current customer to serve.");
            return;
        }
        
        boolean correct = orderManager.checkMatch(servedCup, current.getOrder());
        double patienceRatio = customerManager.getPatienceRatio();
        int delta = moneyManager.calculateReward(current, servedCup, correct, patienceRatio);
        moneyManager.addMoney(delta);
        uiManager.showCoinFloat(delta);
        uiManager.updateCoinLabel(moneyManager.getTotal());

        String phrase = current.getBehavior().getReactionPhrase(correct);
        uiManager.showSpeechBubble(phrase, () -> 
        {
            customerManager.leaveScene(() -> 
            {
                if (onClear != null) onClear.run();
            });
        });
    }
    
    // Getter , Setter
    public MoneyManager getMoneyManager() { return moneyManager; }
}
