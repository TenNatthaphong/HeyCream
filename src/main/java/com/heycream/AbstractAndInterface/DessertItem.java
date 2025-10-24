/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.AbstractAndInterface;

/**
 *
 * @author lenovo
 */
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
