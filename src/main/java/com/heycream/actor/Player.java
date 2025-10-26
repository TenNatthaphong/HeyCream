package com.heycream.actor;

import com.heycream.model.*;

public class Player {
    private Cup currentCup;

    public void prepareOrder(Order order) {
        if (order == null) return;

        this.currentCup = new Cup(
            order.getRequestedCup().getType(),
            order.getRequestedCup().getSize()
        );

        for (IceCream ic : order.getScoops()) {
            currentCup.addScoop(ic);
        }

        if (order.getTopping() != null) {
            currentCup.addTopping(order.getTopping());
        }

        if (order.getSauce() != null) {
            currentCup.addSauce(order.getSauce());
        }
    }

    public void serveTo(Customer customer) {
        if (customer == null || currentCup == null) return;

        boolean correct = customer.getOrder().checkMatch(currentCup);
        customer.reactToOrder(correct);
    }

    public Cup getCurrentCup() { return currentCup; }
}
