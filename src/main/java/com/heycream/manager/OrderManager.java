/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.manager;

import com.heycream.model.Order;
import com.heycream.model.Cup;
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
    public Customer generateOrder() {
        Cup cup = Randomizer.randomCup();
        Order order = new Order(cup); 
        Customer customer = new Customer(order); 

        System.out.println("Customer order generated:");
        System.out.println(order.describe());
        return customer;
    }

    public boolean checkOrder(Cup playerCup, Customer customer) {
        Order target = customer.getOrder();
        return target.checkMatch(playerCup);
    }
}
