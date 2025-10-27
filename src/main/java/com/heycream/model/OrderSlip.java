package com.heycream.model;

public class OrderSlip {
    private final Order order;

    public OrderSlip(Order order) { this.order = order; }
    public Order getOrder() { return order; }
    public String printLine() { return order != null ? order.describe() : "(empty order)"; }
}
