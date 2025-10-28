package com.heycream.manager;

import com.heycream.actor.Customer;
import com.heycream.model.*;

public class MoneyManager {
    private int total = 0;

    public int getTotal() {
        return total;
    }

    public void addMoney(int amount) {
        total += amount;
        if (total < 0) total = 0;
    }

    // ✅ คำนวณรางวัล
    public int calculateReward(Customer customer, Cup cup, boolean correct, double patienceRatio) {
        if (cup == null) return 0;

        double base = 0;

        for (IceCream scoop : cup.getScoops()) base += scoop.getPrice();
        for (Topping top : cup.getToppings()) base += top.getPrice();
        if (cup.getSauce() != null) base += cup.getSauce().getPrice();

        // พิจารณาถูก/ผิด
        double result = correct ? base : base * -0.3;

        // พิจารณาความอดทนของลูกค้า
        result *= Math.max(0.2, patienceRatio);

        // พิจารณา behavior (VIP, Rude, Calm)
        result *= customer.getBehavior().getTipBonus();

        return (int) Math.round(result);
    }
}
