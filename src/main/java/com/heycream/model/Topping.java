
package com.heycream.model;

import com.heycream.AbstractAndInterface.DessertItem;
import java.util.*;


public class Topping extends DessertItem{
    
    //attribute
    //constructor
    public Topping(String name)
    {
        super(name);
        this.price = 5;
    }
    
    //method
    @Override
    public String getType(){ return "Topping"; }
    @Override
    public String getName(){ return name; }
    @Override
    public double getPrice() { return price; }
    
}
