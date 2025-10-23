/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.model;

/**
 *
 * @author lenovo
 */
public class Sauce {
    
    //attribute
    private String name;
    private String color;
    
    //constructor
    public Sauce(String name, String color)
    {
        this.name = name;
        this.color = color;
    }
    
    //method
    public String getName(){ return name; }
    public String getColor(){ return color; }
    @Override
    public String toString() 
    {
        return name;
    }
}
