/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.model;

/**
 *
 * @author lenovo
 */
public class IceCream {
    
    //attribute
    private String flavor;
    private String color;
    
    //constructor
    public IceCream(String flavor, String color)
    {
        this.flavor = flavor;
        this.color = color;
    }
    
    //method
    public String getFlavor(){ return flavor; }
    public String getColor(){ return color; }
    @Override
    public String toString() 
    {
        return flavor;
    }

}
