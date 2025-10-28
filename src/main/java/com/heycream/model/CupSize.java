package com.heycream.model;

/**
 * CupSize defines the size of a cup and its scoop limit.
 * S = 1 scoop, M = 2 scoops, L = 2 scoops (can be customized)
 */
public enum CupSize {
    S(1),
    M(2),
    L(2);

    private final int maxScoops;

    CupSize(int maxScoops) {
        this.maxScoops = maxScoops;
    }

    /** Return maximum allowed scoops for this size. */
    public int getMaxScoops() {
        return maxScoops;
    }

    /** Return size name as a readable string. */
    public String sizeToString() {
        return switch (this) {
            case S -> "Small";
            case M -> "Medium";
            case L -> "Large";
        };
    }
}
