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
    private boolean isWaiting;
    //constructor
    public Customer(Order order,String name) 
    {
        this.name = name;
        this.order = order;
        this.patience = 100;
    }
    
    //method
    public void startTimer() 
    {
        this.patience = 60;  
        this.isWaiting = true;
    }
    public void tick() 
    {
        if (!isWaiting) return;
        patience--;
        if (patience <= 0) 
        {
            isWaiting = false;
            System.out.println(name + " ran out of patience!");
        }
    }
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
    public boolean isOutOfTime() { return !isWaiting; }
    public String getName(){ return name; }
    public Order getOrder(){ return order; }
    public int getPatience() { return patience; }
    public boolean isSatisfied() { return satisfied; }
}
