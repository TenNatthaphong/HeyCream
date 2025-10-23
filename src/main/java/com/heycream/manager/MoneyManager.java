/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.manager;

/**
 *
 * @author lenovo
 */
public class MoneyManager {
    
    //attribute
    private int total;
    private int stars;
    
    //constructor
    public MoneyManager()
    {
        total = 0;
        stars = 0;
    }
    
    //method
    public void addMoney(int amount)
    {
        total += amount;
    }
    public void deduct(int amount)
    {
        if(total > 0) total -= amount;
    }
    public void calculateStars()
    {
        stars = total / 500;
    }
    public int getTotal() { return total; }
    public int getStars() { return stars; }
}
