
package com.heycream.AbstractAndInterface;

public abstract class DessertItem implements NamedItem,PricedItem{
    
    protected String name;
    protected double price;
    
    public DessertItem(String name)
    {
        this.name = name;
    }
    @Override
    public String getName() { return name; }
    @Override
    public abstract double getPrice();
    public abstract String getType();
}
