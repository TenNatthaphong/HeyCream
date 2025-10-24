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
public class Sauce extends DessertItem{
    
    //attribute
    private final String color;
    
    //constructor
    public Sauce(String name, String color)
    {
        super(name);
        this.color = color;
    }
    
    //method
    public String getColor(){ return color; }
    @Override
    public String getName(){ return name; }
    @Override
    public String getType(){ return "Sauce"; }
}
