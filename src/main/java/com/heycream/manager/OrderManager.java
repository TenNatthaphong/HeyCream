/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.manager;

import com.heycream.model.Order;
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

    //simulator
    public Customer generateOrder(int index) {
        CupType type = random.nextBoolean() ? CupType.Cone: CupType.Cup;
        CupSize size;
        if(type.getLabel().equals("Cup"))
        {
            size = switch (random.nextInt(3)) 
            {
            case 0 -> CupSize.Small;
            case 1 -> CupSize.Medium;
            default -> CupSize.Large;
            };
        }
        else
        {
            size = CupSize.Medium;
        }
        Cup cup = new Cup(size,type);
        List<IceCream> scoops = new ArrayList<>();
        scoops.add(new IceCream("Vanilla","White"));
        if (random.nextBoolean()) scoops.add(new IceCream("Chocolate","Black"));
        if (random.nextBoolean()) scoops.add(new IceCream("Strawberry", "Pink"));

        List<Topping> toppings = new ArrayList<>();
        if (random.nextBoolean()) toppings.add(new Topping("Almond"));
        if (random.nextBoolean()) toppings.add(new Topping("Oreo"));
        Sauce sauce = null;
        for (IceCream ic : scoops) cup.addScoop(ic);
        for (Topping tp : toppings) cup.addTopping(tp);
        cup.addSauce(sauce);
        Order order = new Order(cup, scoops, toppings, null);
        Customer c = new Customer(order,"Piggy#" + index);

        return c;
    }

    public boolean checkOrder(Cup playerCup, Customer customer) {
        Order target = customer.getOrder();
        return target.checkMatch(playerCup);
    }
    //method
//    public Customer generateOrder() {
//        Cup cup = Randomizer.randomCup();
//        Order order = new Order(cup); 
//        Customer customer = new Customer(order,order.getName()); 
//
//        System.out.println("Customer Name : " + cup.getName() +" order generated:");
//        System.out.println(order.describe());
//        return customer;
//    }
//
//    public boolean checkOrder(Cup playerCup, Customer customer) {
//        Order target = customer.getOrder();
//        return target.checkMatch(playerCup);
//    }
}
