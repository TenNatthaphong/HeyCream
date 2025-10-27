package com.heycream.AbstractAndInterface;

public class VIPCustomer implements CustomerBehavior {
    @Override public double getTipBonus() { return 1.5; }
    @Override public boolean isVIP() { return true; }
}
