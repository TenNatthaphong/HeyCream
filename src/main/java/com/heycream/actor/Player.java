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
public class Player {
    
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
        Cup readyCup = new Cup(currentCup.getSize());
        scoops.forEach(readyCup::addScoop);
        toppings.forEach(readyCup::addTopping);
        readyCup.addSauce(currentSauce);
        boolean correct = customer.getOrder().checkMatch(readyCup);
        customer.reactToOrder(correct);
    }
    
}
