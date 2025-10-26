package com.heycream.manager;

import com.heycream.AbstractAndInterface.*;
import com.heycream.model.*;
import com.heycream.actor.Customer;
import com.heycream.utils.Randomizer;
import java.util.*;

public class OrderManager {
    private final TimeManager timeManager;
    public OrderManager(TimeManager sharedTime) {
        this.timeManager = sharedTime;
    }
    public Order generateOrder() {
        return Randomizer.randomOrder();
    }

    public Customer generateCustomer() {
        Order order = generateOrder();
        String name = Randomizer.randomName();
        CustomerBehavior behavior;

        double roll = Math.random();
        if (roll < 0.6) behavior = new CalmCustomer();
        else if (roll < 0.85) behavior = new RudeCustomer();
        else behavior = new VIPCustomer();

        return new Customer(name, order, behavior, timeManager.getCurrentMinute());
    }

    public boolean checkOrder(Cup playerCup, Customer c) {
        return c.getOrder().checkMatch(playerCup);
    }
}
