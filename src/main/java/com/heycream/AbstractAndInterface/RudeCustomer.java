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
    public int getPatienceSeconds() { return 20; }

     @Override
public String getReactionPhrase(boolean correct) {
    return correct ? "Finally, about time!" : "Ugh, this is terrible!";
}

}
