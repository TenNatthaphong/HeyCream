/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.ui;

import com.heycream.model.Order;
/**
 *
 * @author lenovo
 */
public class OrderSlip {
    
    //attribute
    private Order currentOrder;

    //constructor
    public OrderSlip(Order order) {
        this.currentOrder = order;
    }
    
    //method
    public void displayOrder()
    {
        System.out.println("📋 ORDER SLIP:");
        System.out.println(currentOrder.describe());
        System.out.println("-------------------------");
    }
    public void setOrder(Order order) { this.currentOrder = order; }
    public Order getOrder() { return currentOrder; }
}
