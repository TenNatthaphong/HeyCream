/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.actor;

import com.heycream.model.*;
import java.util.*;

/**
 *
 * @author lenovo
 */
public class Player {
    
    //attribute
    private Cup currentCup;
    private List<IceCream> scoops;
    private List<Topping> toppings;
    private Sauce currentSauce;
    private int comboScore;
    
    //constructor
    public Player(){}
    
    //method
    public void pickCup(Cup cup){}
    public void scoopIceCream(IceCream flavor){}
    public void addTopping(Topping topping){}
    public void pourSauce(Sauce sauce){}
    public void serve(Customer customer){}
    
}
