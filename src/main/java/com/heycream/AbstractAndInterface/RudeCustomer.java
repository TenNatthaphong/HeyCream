package com.heycream.AbstractAndInterface;

/**
 * Rude customers are impatient and complain often.
 */
public class RudeCustomer implements CustomerBehavior {

    @Override
    public double getTipBonus() { return 0.8; }

    @Override
    public boolean isRude() { return true; }

    @Override
    public int getPatienceSeconds() { return 3; }

     @Override public String getText() { return "with a rude tone, clearly annoyed"; }
}
