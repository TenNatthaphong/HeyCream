/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.AbstractAndInterface;

/**
 *
 * @author lenovo
 */
public class VIPCustomer  implements CustomerBehavior{
    @Override
    public int getPatience() { return 25; }
    @Override
    public double getTipBonus() { return 1.5; }
    @Override
    public String getMood() { return "excited"; }
}
