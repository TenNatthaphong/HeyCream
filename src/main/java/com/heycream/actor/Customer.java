package com.heycream.actor;

import com.heycream.AbstractAndInterface.*;
import com.heycream.model.Order; 

public class Customer {
    private final String name;
    private final Order order;
    private final CustomerBehavior behavior;
    private final int arrivalMinute;

    public Customer(String name, Order order, CustomerBehavior behavior, int arrivalMinute) {
        this.name = name;
        this.order = order;
        this.behavior = behavior;
        this.arrivalMinute = arrivalMinute;
    }

    public String getName() { return name; }
    public Order getOrder() { return order; }  // <— คืน model.Order
    public CustomerBehavior getBehavior() { return behavior; }
    public int getArrivalMinute() { return arrivalMinute; }
    public String getSpeech() { return order != null ? order.describe(behavior) : "Hello!"; }
}
