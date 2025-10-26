
package com.heycream.AbstractAndInterface;

public class RudeCustomer implements CustomerBehavior{
    @Override
    public int getPatience() { return 15; }
    @Override
    public double getTipBonus() { return 0.8; }
    @Override
    public String getMood() { return "impatient"; }
}
