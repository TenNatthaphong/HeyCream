package com.heycream.actor;

import com.heycream.AbstractAndInterface.CustomerBehavior;
import com.heycream.model.Order;

public class Customer {
    
    //attribute
    private String name;
    private Order order;
    private int patience;
    private boolean satisfied;
    private boolean isWaiting;
    private CustomerBehavior behavior;
    private int arrivalMinute;
    private boolean left = false;
    //constructor
    public Customer(String name, Order order, CustomerBehavior behavior, int arrivalMinute) 
    {
        this.name = name;
        this.order = order;
        this.behavior = behavior;
        this.arrivalMinute = arrivalMinute;
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
    public CustomerBehavior getBehavior() { return behavior; }
    public int getArrivalMinute() { return arrivalMinute; }
    public void setArrivalMinute(int arrMinute) { arrivalMinute = arrMinute; }
    public boolean isOutOfTime() { return !isWaiting; }
    public String getName(){ return name; }
    public Order getOrder(){ return order; }
    public int getPatience() { return patience; }
    public boolean isSatisfied() { return satisfied; }
    public boolean hasLeft() { return left; }
    public void setLeft(boolean left) { this.left = left; }
}
