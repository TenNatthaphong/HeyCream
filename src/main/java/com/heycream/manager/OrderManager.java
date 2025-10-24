/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.manager;

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

    //method
    public Customer generateOrder(int index) {
        Order order = Randomizer.randomOrder();
        String name = Randomizer.randomCustomerName(index);
        return new Customer(order,name);
    }

    public boolean checkOrder(Cup playerCup, Customer c) {
        return c.getOrder().checkMatch(playerCup);
    }
}
