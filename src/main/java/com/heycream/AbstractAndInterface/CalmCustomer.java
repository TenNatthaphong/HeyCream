/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.AbstractAndInterface;

/**
 *
 * @author lenovo
 */
public class CalmCustomer implements CustomerBehavior{
    @Override
    public int getPatience() { return 40; }
    @Override
    public double getTipBonus() { return 1.0; }
    @Override
    public String getMood() { return "calm"; }
}
