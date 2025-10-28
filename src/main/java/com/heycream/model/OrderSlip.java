package com.heycream.model;

import com.heycream.AbstractAndInterface.*;

public class OrderSlip {
    private final Order order;
    public OrderSlip(Order order) { this.order = order; }
    public Order getOrder() { return order; }
    public String printLine(CustomerBehavior behavior) { return order != null ? order.describe(behavior) : "(empty order)"; }
}
