/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.model;

import com.heycream.actor.*;
import java.util.*;

/**
 *
 * @author lenovo
 */
public class Cup {
    
    //attribute
    private CupType type;
    private CupSize size;
    private List<IceCream> scoops;
    private List<Topping> toppings;
    private Sauce sauce;
    private String name;
    
    //constructor
    public Cup(CupSize size,CupType type)
    {
        this.type = type;
        this.size = size;
        scoops = new ArrayList<>();
        toppings = new ArrayList<>();
    }
    
    //method
    public void addScoop(IceCream iceCream){ scoops.add(iceCream); }
    public void addTopping(Topping t) 
    {
        if (t == null) return;
        boolean exists = this.toppings.stream()
            .anyMatch(x -> x.getName().equalsIgnoreCase(t.getName()));
        if (!exists && this.toppings.size() < Order.MAX_TOPPINGS) 
        {
            this.toppings.add(t);
        }
    }
    public void addSauce(Sauce sauce){ this.sauce = sauce; }
    public void addName(String name){ this.name = name; }
    public String getDescription()
    {
        return  " Cup type: " + type + 
                " Cup size: " + size + 
                ", Scoop: " + scoops.size() + 
                ", Toppings: " + toppings.size() + 
                ", Sauce: " + (sauce != null ? sauce.getName() : "none"); 
    }
    public CupSize getSize() { return size; }
    public CupType getType() { return type; }
    public List<IceCream> getScoops() { return scoops; }
    public List<Topping> getToppings() { return toppings; }
    public Sauce getSauce() { return sauce; }
    public String getName() { return name; }
    
}
