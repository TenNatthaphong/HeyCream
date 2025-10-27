package com.heycream.manager;

import com.heycream.actor.*;
import com.heycream.AbstractAndInterface.*;

public class MoneyManager {
    private int total;

    public int getTotal() { return total; }
    public void addMoney(int amount) { total += amount; }

    public int calculateReward(Customer customer, boolean correct) {
        int base = 50;
        CustomerBehavior b = customer.getBehavior();
        double mult = 1.0;
        if (b.isVIP()) mult = 1.5;
        else if (b.isRude()) mult = 0.8;

        int reward = (int)(base * mult);
        if (!correct) reward = -reward / 2;
        return reward;
    }
}
