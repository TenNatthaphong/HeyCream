package com.heycream.AbstractAndInterface;

public interface CustomerBehavior {
    default double getTipBonus() { return 1.0; }
    default boolean isVIP() { return false; }
    default boolean isRude() { return false; }
}
