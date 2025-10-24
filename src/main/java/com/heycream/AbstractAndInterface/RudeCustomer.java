/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.AbstractAndInterface;

/**
 *
 * @author lenovo
 */
public class RudeCustomer implements CustomerBehavior{
    @Override
    public int getPatience() { return 15; }
    @Override
    public double getTipBonus() { return 0.8; }
    @Override
    public String getMood() { return "impatient"; }
}
