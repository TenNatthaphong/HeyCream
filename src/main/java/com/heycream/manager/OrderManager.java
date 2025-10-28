package com.heycream.manager;

import com.heycream.actor.Customer;
import com.heycream.model.Cup;
import com.heycream.model.Order;
import com.heycream.utils.Randomizer;


public class OrderManager {

    public OrderManager() {}
    /** Simple equality check (can be customized later). */
    public boolean isOrderCorrect(Cup playerCup, Customer customer) {
        if (playerCup == null || customer == null) return false;
        Order order = customer.getOrder();
        if (order == null) return false;
        return order.checkMatch(playerCup);
    }

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
}
