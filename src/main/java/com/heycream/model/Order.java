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
    private String name;
    private Cup requestedCup;
    private List<IceCream> scoops;
    private List<Topping> toppings;
    private Sauce sauce;
    private boolean completed;
    
    
    //constructor
//    public Order(Cup requestedCup) {
//        this.name = requestedCup.getName();
//        this.requestedCup = requestedCup;
//        this.completed = false;
//    }
    
    //method
    public String describe() {
    StringBuilder sb = new StringBuilder();
    sb.append("Order Details\n");
    sb.append("--------------------\n");
    sb.append("Cup Size: ").append(requestedCup.getSize()).append("\n");

    if (scoops == null || scoops.isEmpty())
        sb.append("Ice Cream: none\n");
    else
        sb.append("Ice Cream: ")
          .append(scoops.stream().map(IceCream::getFlavor).sorted().collect(Collectors.joining(", ")))
          .append("\n");

    if (toppings == null || toppings.isEmpty())
        sb.append("Toppings: none\n");
    else
        sb.append("Toppings: ")
          .append(toppings.stream().map(Topping::getName).sorted().collect(Collectors.joining(", ")))
          .append("\n");

    sb.append("Sauce: ").append(sauce != null ? sauce.getName() : "none").append("\n");
    sb.append("--------------------\n");
    sb.append("Status: ").append(completed ? "Completed" : "In Progress").append("\n");
    return sb.toString();
}

        public boolean checkMatch(Cup actualCup) {
         if (actualCup == null || requestedCup == null) return false;
         boolean cupMatch = requestedCup.getType().equals(actualCup.getType()) &&
                            requestedCup.getSize().equals(actualCup.getSize());
         List<String> reqScoops = requestedCup.getScoops().stream()
                 .map(IceCream::getFlavor)
                 .sorted()
                 .collect(Collectors.toList());
         List<String> actScoops = actualCup.getScoops().stream()
                 .map(IceCream::getFlavor)
                 .sorted()
                 .collect(Collectors.toList());
         boolean scoopMatch = reqScoops.equals(actScoops);
         List<String> reqToppings = requestedCup.getToppings().stream()
                 .map(Topping::getName)
                 .sorted()
                 .collect(Collectors.toList());
         List<String> actToppings = actualCup.getToppings().stream()
                 .map(Topping::getName)
                 .sorted()
                 .collect(Collectors.toList());
         boolean toppingMatch = reqToppings.equals(actToppings);
         String reqSauce = requestedCup.getSauce() != null ? requestedCup.getSauce().getName() : "none";
         String actSauce = actualCup.getSauce() != null ? actualCup.getSauce().getName() : "none";
         boolean sauceMatch = reqSauce.equalsIgnoreCase(actSauce);

         boolean result = cupMatch && scoopMatch && toppingMatch && sauceMatch;

         // debug log
         System.out.println("DEBUG => cupMatch=" + cupMatch + 
                            ", scoopMatch=" + scoopMatch + 
                            ", toppingMatch=" + toppingMatch + 
                            ", sauceMatch=" + sauceMatch);
         System.out.println("Requested scoops=" + reqScoops);
         System.out.println("Actual scoops=" + actScoops);
         System.out.println("Requested toppings=" + reqToppings);
         System.out.println("Actual toppings=" + actToppings);
         System.out.println("Requested sauce=" + reqSauce + ", Actual sauce=" + actSauce);
         System.out.println("Result = " + result);

         return result;
     }




    public void markComplete() { this.completed = true; }
    public Cup getRequestedCup() { return requestedCup; }
    public List<IceCream> getScoops() { return scoops; }
    public List<Topping> getToppings() { return toppings; }
    public Sauce getSauce() { return sauce; }
    public boolean isCompleted() { return completed; }
    public String getName() { return name; }
    
    
    //simulator
    public void setName(String name) 
    {
    this.name = name;
    }
    public Order(Cup requestedCup, List<IceCream> scoops, List<Topping> toppings, Sauce sauce) {
        this.requestedCup = requestedCup;
        this.scoops = (scoops != null) ? scoops : new ArrayList<>();
        this.toppings = (toppings != null) ? toppings : new ArrayList<>();
        this.sauce = sauce;
        this.completed = false;
    }
    public Order(Cup requestedCup) {
    this(requestedCup, new ArrayList<>(), new ArrayList<>(), 
         requestedCup != null ? requestedCup.getSauce() : null);
}

    

}
