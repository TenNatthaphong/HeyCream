package com.heycream.manager;

import com.heycream.actor.Customer;
import com.heycream.model.Cup;
import com.heycream.model.Order;
import com.heycream.utils.Randomizer;


public class OrderManager {

    private int totalServes = 0;
    private int correctServes = 0;
    public OrderManager() {}
    /** Simple equality check (can be customized later). */
    public boolean isOrderCorrect(Cup playerCup, Customer customer) {
        if (playerCup == null || customer == null) return false;
        Order order = customer.getOrder();
        if (order == null) return false;
        return order.checkMatch(playerCup);
    }

    public void recordServe(boolean correct) {
    totalServes++;
    if (correct) correctServes++;
    }

    public int getTotalServeCount() { return totalServes; }
    public int getCorrectServeCount() { return correctServes; }
   public Order generateOrder() {
        return Randomizer.randomOrder();
    }
    /** Simplified readiness check (already added earlier). */
    public boolean canServe(Cup prepared) {
        if (prepared == null) return false;
        if (prepared.getType() == null) return false;
        int required = prepared.getSize() != null ? prepared.getSize().getMaxScoops() : 0;
        if (required <= 0) return false;
        return prepared.getScoops() != null && prepared.getScoops().size() >= required;
    }
    public boolean checkMatch(Cup servedCup, Order order) {
    if (order == null || servedCup == null) return false;
    return order.checkMatch(servedCup);
}

}
