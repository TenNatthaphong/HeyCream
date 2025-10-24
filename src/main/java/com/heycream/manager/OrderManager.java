/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.manager;

import com.heycream.AbstractAndInterface.*;
import com.heycream.model.*;
import com.heycream.actor.Customer;
import com.heycream.utils.Randomizer;
import java.util.*;
/**
 *
 * @author lenovo
 */
public class OrderManager {
    
    //attribute
    private Random random = new Random();
    private TimeManager timeManager = new TimeManager();

    //method
    public Customer generateOrder(int index) {
        Order order = Randomizer.randomOrder();
        String name = Randomizer.randomName();
        CustomerBehavior behavior;
        double roll = Math.random();
        if (roll < 0.6) behavior = new CalmCustomer();
        else if (roll < 0.85) behavior = new RudeCustomer();
        else behavior = new VIPCustomer();
        return new Customer(name,order,behavior, timeManager.getCurrentMinute());
    }

    public boolean checkOrder(Cup playerCup, Customer c) {
        return c.getOrder().checkMatch(playerCup);
    }
}
