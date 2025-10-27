package com.heycream.AbstractAndInterface;

public class RudeCustomer implements CustomerBehavior {
    @Override public double getTipBonus() { return 0.8; }
    @Override public boolean isRude() { return true; }
}
