
package com.heycream.model;

import com.heycream.AbstractAndInterface.DessertItem;

public class Sauce extends DessertItem{
    
    //attribute
    
    //constructor
    public Sauce(String name)
    {
        super(name);
        this.price = 4.0;
    }
    
    //method
    @Override
    public String getName(){ return name; }
    @Override
    public String getType(){ return "Sauce"; }
    @Override
    public double getPrice() { return price; }
}
