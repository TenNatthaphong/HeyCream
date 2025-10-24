/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.model;

import com.heycream.AbstractAndInterface.DessertItem;

/**
 *
 * @author lenovo
 */
public class IceCream extends DessertItem{
    
    //attribute
    private final String color;
    
    //constructor
    public IceCream(String flavor, String color)
    {
        super(flavor);
        this.color = color;
    }
    
    //method
    public String getColor(){ return color; }
    public String getFlavor(){ return super.name; }
    @Override
    public String getType(){ return "Ice Cream"; }
}
