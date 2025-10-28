package com.heycream.AbstractAndInterface;

/**
 * Calm customers are polite and patient.
 */
public class CalmCustomer implements CustomerBehavior {

    @Override
    public double getTipBonus() { return 1.0; }

    @Override
    public int getPatienceSeconds() { return 8; }

     @Override
    public String getReactionPhrase(boolean correct) {
        return correct ? "Thank you! It looks perfect!" : "Hmm… not quite right.";
    }
}
