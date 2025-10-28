package com.heycream.actor;

import com.heycream.model.Cup;

/**
 * Player: re-use currentCup as the prepared cup (no drag).
 */
public class Player {
    private Cup currentCup;

    public void setPreparedCup(Cup cup) { this.currentCup = cup; }
    public Cup getPreparedCup() { return this.currentCup; }
    public void clearPrepared() { this.currentCup = null; }

    public Cup getCurrentCup() { return currentCup; } // keep old API for compatibility
}
