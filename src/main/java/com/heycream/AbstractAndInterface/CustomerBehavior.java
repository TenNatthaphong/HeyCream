package com.heycream.AbstractAndInterface;

/**
 * Interface for customer behavior traits.
 * Each behavior defines tip bonus and attitude flags.
 */
public interface CustomerBehavior {
    /** Tip multiplier bonus. */
    default double getTipBonus() { return 1.0; }

    /** Whether this behavior is considered a VIP. */
    default boolean isVIP() { return false; }

    /** Whether this behavior is considered rude. */
    default boolean isRude() { return false; }

    default String getText() { return "with a calm demeanor"; }
    /** How long this type will wait before leaving (in seconds). */
    default int getPatienceSeconds() { return 6; }

    /** What they say when served. */
    public abstract String getReactionPhrase(boolean correct);
}
