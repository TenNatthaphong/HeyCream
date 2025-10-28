package com.heycream.model;

/**
 * CupSize defines the size of a cup and its scoop, topping, and sauce limits.
 */
public enum CupSize {
    S(1, 1, 1),   // 1 scoop, ≤1 topping, ≤1 sauce
    M(2, 2, 1),   // 2 scoops, ≤2 toppings, ≤1 sauce
    L(3, 3, 1);   // 3 scoops, ≤3 toppings, ≤1 sauce

    private final int maxScoops;
    private final int maxToppings;
    private final int maxSauces;

    CupSize(int maxScoops, int maxToppings, int maxSauces) {
        this.maxScoops = maxScoops;
        this.maxToppings = maxToppings;
        this.maxSauces = maxSauces;
    }

    public int getMaxScoops() {
        return maxScoops;
    }

    public int getMaxToppings() {
        return maxToppings;
    }

    public int getMaxSauces() {
        return maxSauces;
    }

    public String sizeToString() {
        return switch (this) {
            case S -> "Small";
            case M -> "Medium";
            case L -> "Large";
        };
    }
}
