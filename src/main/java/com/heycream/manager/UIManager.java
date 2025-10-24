/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.manager;

import com.heycream.actor.Customer;
import com.heycream.ui.OrderSlip;
import com.heycream.model.Order;
/**
 *
 * @author lenovo
 */
public class UIManager {
    
    //attribute
    private OrderSlip orderSlip;
    private int timeDisplay;
    
    //constructor
    public UIManager()
    {
        orderSlip = null;
        timeDisplay = 600;
    }
    
    //method
    public void setOrder(Order order) {
        this.orderSlip = new OrderSlip(order);
    }
    public void updateUI(int currentTime)
    {
        timeDisplay = currentTime;
        System.out.println("time for now : " + formatTime(currentTime));
    }
    public void showOrder()
    {
        orderSlip.displayOrder();
    }
    private String formatTime(int minute)
    {
        int hour = 12 + (minute/60);
        int min = minute%60;
        return String.format("%02d:%02d", hour,min);
    }
    public int getTimeDisplay() { return timeDisplay; }
    public OrderSlip getOrderSlip() { return orderSlip; }
}

