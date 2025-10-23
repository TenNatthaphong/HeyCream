/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.model;

import java.util.*;
import java.util.stream.*;
/**
 *
 * @author lenovo
 */
public class Order {
    
    //attribute
    private Cup requestedCup;
    private boolean completed;
    
    //constructor
    public Order(Cup requestedCup) {
        this.requestedCup = requestedCup;
        this.completed = false;
    }
    
    //method
    public String describe()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("Order Details\n");
        sb.append("--------------------\n");
        sb.append("Cup Size: ").append(requestedCup.getSize()).append("\n");

        // 🟣 Scoops (รสไอติม)
        if (requestedCup.getScoops().isEmpty()) {
            sb.append("Ice Cream: none\n");
        } else {
            String scoopsList = requestedCup.getScoops()
                    .stream()
                    .map(IceCream::getFlavor)
                    .collect(Collectors.joining(", "));
            sb.append("Ice Cream: ").append(scoopsList).append("\n");
        }

        // 🟢 Toppings
        if (requestedCup.getToppings().isEmpty()) {
            sb.append("Toppings: none\n");
        } else {
            String toppingList = requestedCup.getToppings()
                    .stream()
                    .map(Topping::getName)
                    .collect(Collectors.joining(", "));
            sb.append("Toppings: ").append(toppingList).append("\n");
        }

        // 🟠 Sauce
        sb.append("Sauce: ");
        if (requestedCup.getSauce() != null) {
            sb.append(requestedCup.getSauce().getName()).append("\n");
        } else {
            sb.append("none\n");
        }

        sb.append("--------------------\n");
        sb.append("Status: ").append(completed ? "✅ Completed" : "🕒 In Progress").append("\n");

        return sb.toString();
    }
    public boolean checkMatch(Cup actualCup) {
        if (actualCup == null) return false;
        return requestedCup.getDescription().equals(actualCup.getDescription());
    }

    public void markComplete() {
        this.completed = true;
    }

    public Cup getRequestedCup() {
        return requestedCup;
    }

    public boolean isCompleted() {
        return completed;
    }
    
}
