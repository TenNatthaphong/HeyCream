/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.actor;

import com.heycream.model.*;

/**
 *
 * @author lenovo
 */
public class Scoop {
    
    //attribute
    private IceCream flavor;
    private Cup cup;
    private boolean isPlaced;
    
    //constructor
    public Scoop(){}
    
    //method
    public void take(IceCream flavor)
    {
        this.flavor = flavor;
        this.isPlaced = false;
    }
    public void dropInto(Order order)
    {
        cup = order.getRequestedCup();
        isPlaced = true;
    } 
    
}
