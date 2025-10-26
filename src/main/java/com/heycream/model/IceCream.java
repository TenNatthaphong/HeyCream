
package com.heycream.model;

import com.heycream.AbstractAndInterface.DessertItem;

public class IceCream extends DessertItem{
    
    //attribute
    
    //constructor
    public IceCream(String flavor)
    {
        super(flavor);
        this.price = 15;
    }
    
    //method
    public String getFlavor(){ return super.name; }
    @Override
    public String getType(){ return "IceCream"; }
    @Override
    public double getPrice(){ return price; }
}
