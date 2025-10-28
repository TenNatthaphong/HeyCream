package com.heycream.AbstractAndInterface;

/**
 * VIP customers are generous and patient.
 */
public class VIPCustomer implements CustomerBehavior {

    @Override
    public double getTipBonus() { return 1.5; }

    @Override
    public boolean isVIP() { return true; }

    @Override
    public int getPatienceSeconds() { return 10; }

    @Override public String getText() { return "in an elegant, confident manner"; }
}
