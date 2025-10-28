package com.heycream.model;

/**
 * CupType represents the container type (CUP or CONE),
 * with its own serving rules independent of CupSize.
 */
public enum CupType {
    CUP,
    CONE;

    /** Return readable name */
    public String typeToString() {
        return switch (this) {
            case CUP -> "Cup";
            case CONE -> "Cone";
        };
    }

    /** Cone behaves like Medium cup: 2 scoops, ≤2 toppings, ≤1 sauce */
    public int getMaxScoops() {
        return this == CONE ? 2 : 0; // 0 หมายถึง "ใช้ CupSize แทน"
    }

    public int getMaxToppings() {
        return this == CONE ? 2 : 0;
    }

    public int getMaxSauces() {
        return this == CONE ? 1 : 0;
    }
}
