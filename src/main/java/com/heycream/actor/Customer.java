/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.actor;

import com.heycream.model.Order;

/**
 *
 * @author lenovo
 */
public class Customer {
    
    //attribute
    private String name;
    private Order order;
    private int patience;
    private boolean satisfied;
    
    //constructor
    public Customer(Order order) 
    {
        this.order = order;
        this.patience = 100;
        this.satisfied = false;
    }
    
    //method
    public void generateOrder(Order newOrder)
    {
        this.order = newOrder;
    }
    public void reactToOrder(boolean isCorrect)
    {
        satisfied = isCorrect;
        if(isCorrect) System.out.println(name + " is happy!");
        else System.out.println(name + " is upset!");
    }
    private void decreasePatience()
    {
        patience -= 10;
        if(patience < 0) patience = 0;
    }   
    public String getName(){ return name; }
    public Order getOrder(){ return order; }
    public int getPatience() { return patience; }
    public boolean isSatisfied() { return satisfied; }
}
