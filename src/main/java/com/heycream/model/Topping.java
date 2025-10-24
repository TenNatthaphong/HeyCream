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
public class Topping extends DessertItem{
    
    //attribute from Super
    
    //constructor
    public Topping(String name)
    {
        super(name);
    }
    
    //method
    @Override
    public String getType(){ return "Topping"; }
    @Override
    public String getName(){ return name; }
    
}
