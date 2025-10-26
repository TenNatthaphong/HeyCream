
package com.heycream.AbstractAndInterface;

public class CalmCustomer implements CustomerBehavior{
    @Override
    public int getPatience() { return 40; }
    @Override
    public double getTipBonus() { return 1.0; }
    @Override
    public String getMood() { return "calm"; }
}
