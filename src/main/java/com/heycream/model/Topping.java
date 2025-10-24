/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.model;

import com.heycream.AbstractAndInterface.DessertItem;
import java.util.*;

/**
 *
 * @author lenovo
 */
public class Topping extends DessertItem{
    
    //attribute
    private static final Map<String, Double> TOPPING_PRICES = Map.of(
        "Oreo", 7.0,
        "Almond", 6.0,
        "Walnut", 6.0,
        "ChocoChip", 5.0,
        "Sprinkles", 5.0,
        "Pocky", 5.0
    );
    //constructor
    public Topping(String name)
    {
        super(name);
        this.price = TOPPING_PRICES.getOrDefault(name,5.0);
    }
    
    //method
    @Override
    public String getType(){ return "Topping"; }
    @Override
    public String getName(){ return name; }
    @Override
    public double getPrice() { return price; }
    
}
