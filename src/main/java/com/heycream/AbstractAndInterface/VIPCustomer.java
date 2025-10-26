
package com.heycream.AbstractAndInterface;

public class VIPCustomer  implements CustomerBehavior{
    @Override
    public int getPatience() { return 25; }
    @Override
    public double getTipBonus() { return 1.5; }
    @Override
    public String getMood() { return "excited"; }
}
