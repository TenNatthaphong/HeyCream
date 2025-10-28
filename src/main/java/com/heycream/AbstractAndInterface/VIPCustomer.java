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
    public int getPatienceSeconds() { return 15; }

    @Override
public String getReactionPhrase(boolean correct) {
    return correct ? "Marvelous! You never disappoint!" : "Oh no… that’s not my order.";
}

}
