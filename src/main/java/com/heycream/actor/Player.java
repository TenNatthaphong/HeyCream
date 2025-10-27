package com.heycream.actor;

import com.heycream.model.*;

public class Player {
    private Cup currentCup;

    public void newCup(CupType type, CupSize size) { currentCup = new Cup(type, size); }
    public Cup getCurrentCup() { return currentCup; }
}
