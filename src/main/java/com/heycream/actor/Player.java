/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.actor;

import com.heycream.model.*;
import java.util.*;

/**
 *
 * @author lenovo
 */
public class Player{
    
    //attribute
    private Cup currentCup;
    private List<IceCream> scoops;
    private List<Topping> toppings;
    private Sauce currentSauce;
    private int comboScore;
    
    //constructor
    public Player()
    {
        scoops = new ArrayList<>();
        toppings = new ArrayList<>();
        comboScore = 0;
    }
    
    //method
    public Cup getCurrentCup(){ return currentCup; }
    public void pickCup(Cup cup) { this.currentCup = cup; }
    public void scoopIceCream(IceCream flavor) { scoops.add(flavor); }
    public void addTopping(Topping topping) {toppings.add(topping); }
    public void pourSauce(Sauce sauce) { this.currentSauce = sauce; }
    public void serve(Customer customer)
    {
        if (currentCup == null) {
        System.out.println("Error: Player has no current cup!");
        return;
        }
        Cup readyCup = new Cup(currentCup.getSize(),currentCup.getType());
        scoops.forEach(readyCup::addScoop);
        toppings.forEach(readyCup::addTopping);
        readyCup.addSauce(currentSauce);
    }
    
    //simulator
    public void prepareOrder(Order order) {
    if (order == null) return;
    this.currentCup = new Cup(order.getRequestedCup().getSize(), order.getRequestedCup().getType());
    this.scoops = new ArrayList<>(order.getScoops());
    this.toppings = new ArrayList<>(order.getToppings());
    for (IceCream ic : scoops) {
        currentCup.addScoop(ic);
    }
    for (Topping tp : toppings) {
        currentCup.addTopping(tp);
    }
    this.currentSauce = order.getSauce();
}
}
