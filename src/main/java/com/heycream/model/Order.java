/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.model;

import com.heycream.AbstractAndInterface.DessertItem;
import java.util.*;
import java.util.stream.*;
/**
 *
 * @author lenovo
 */
public class Order {
    
    //attribute
    private final Cup requestedCup;
    private final List<DessertItem> items = new ArrayList<>();
    private boolean completed = false;
    public static final int MAX_SCOOPS   = 3;
    public static final int MAX_TOPPINGS = 3;
    public static final int MAX_SAUCE    = 1;
    
    //constructor
    public Order(Cup cup)
    {
        this.requestedCup = cup;
    }

    //method
    public boolean isCompleted() { return completed; }
    public Cup getRequestedCup() { return requestedCup; }
    public void setCompleted(boolean v) { completed = v; }
    public boolean addItem(DessertItem item) 
    {
        switch (item.getType()) 
        {
            case "IceCream":
                if (getScoops().size() >= MAX_SCOOPS) return false;
                break;
            case "Topping":
                if (getToppings().size() >= MAX_TOPPINGS) return false;
                break;
            case "Sauce":
                if (getSauce().isPresent()) return false;
                break;
            default: return false;
        }
        items.add(item);
        return true;
    }
    public List<IceCream> getScoops()
    {
        return items.stream()
                .filter(d -> "IceCream".equals(d.getType()))
                .map(d -> (IceCream) d)
                .collect(Collectors.toList());
    }
    public List<Topping> getToppings() 
    {
        return items.stream()
                .filter(d -> "Topping".equals(d.getType()))
                .map(d -> (Topping) d)
                .collect(Collectors.toList());
    }
    public Optional<Sauce> getSauce() 
    {
        return items.stream()
                .filter(d -> "Sauce".equals(d.getType()))
                .map(d -> (Sauce) d)
                .findFirst();
    }
    public List<DessertItem> getAllItems() 
    {
        return Collections.unmodifiableList(items);
    }
    
    //display
    public String describe() 
    {
        String scoopsStr   = getScoops().isEmpty() ? "none" : 
                getScoops().stream().map(IceCream::getFlavor).sorted().collect(Collectors.joining(", "));
        String toppingsStr = getToppings().isEmpty() ? "none" : 
                getToppings().stream().map(Topping::getName).sorted().collect(Collectors.joining(", "));
        String sauceStr = getSauce().map(Sauce::getName).orElse("none");

        StringBuilder sb = new StringBuilder();
        sb.append("Order Details\n")
          .append("--------------------\n")
          .append("Cup Size: ").append(requestedCup.getSize()).append("\n")
          .append("Ice Cream: ").append(scoopsStr).append("\n")
          .append("Toppings: ").append(toppingsStr).append("\n")
          .append("Sauce: ").append(sauceStr).append("\n")
          .append("--------------------\n")
          .append("Status: ").append(completed ? "Completed" : "In Progress").append("\n");
        return sb.toString();
    }
    
    //match
    public boolean checkMatch(Cup actualCup) 
    {
        if (actualCup == null || requestedCup == null) return false;

        //size and type same?
        boolean cupMatch = requestedCup.getType().equals(actualCup.getType())
                        && requestedCup.getSize().equals(actualCup.getSize());

        //map reqIceCream, actIceCream and compare
        List<String> reqScoops = getScoops().stream()
                .map(IceCream::getFlavor).map(String::toLowerCase).sorted().collect(Collectors.toList());
        List<String> actScoops = actualCup.getScoops().stream()
                .map(IceCream::getFlavor).map(String::toLowerCase).sorted().collect(Collectors.toList());
        boolean scoopMatch = reqScoops.equals(actScoops);

        //map reqTopping, actTopping and compare
        List<String> reqToppings = getToppings().stream()
                .map(Topping::getName).map(String::toLowerCase).sorted().collect(Collectors.toList());
        List<String> actToppings = actualCup.getToppings().stream()
                .map(Topping::getName).map(String::toLowerCase).sorted().collect(Collectors.toList());
        boolean toppingMatch = reqToppings.equals(actToppings);

        //map reqSauce, actSauce and compare
        String reqSauce = getSauce().map(Sauce::getName).orElse("none").toLowerCase();
        String actSauce = actualCup.getSauce() != null ? actualCup.getSauce().getName().toLowerCase() : "none";
        boolean sauceMatch = reqSauce.equals(actSauce);

        //compare Request and Actual
        boolean result = cupMatch && scoopMatch && toppingMatch && sauceMatch;

        System.out.println("DEBUG => cupMatch=" + cupMatch + ", scoopMatch=" + scoopMatch +
                ", toppingMatch=" + toppingMatch + ", sauceMatch=" + sauceMatch);
        System.out.println("Requested scoops=" + reqScoops);
        System.out.println("Actual scoops=" + actScoops);
        System.out.println("Requested toppings=" + reqToppings);
        System.out.println("Actual toppings=" + actToppings);
        System.out.println("Requested sauce=" + reqSauce + ", Actual sauce=" + actSauce);
        System.out.println("Result = " + result);

        return result;
    }
}
