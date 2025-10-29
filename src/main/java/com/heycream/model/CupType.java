package com.heycream.model;

public enum CupType 
{
    // ENUM
    CUP,
    CONE;

    // =====================
    // SECTION: Method
    // ===================== 
    public String typeToString()
    {
        return switch (this)
        {
            case CUP -> "Cup";
            case CONE -> "Cone";
        };
    }

    // Getter , Setter
    public int getMaxScoops() { return this == CONE ? 2 : 0; }
    public int getMaxToppings() { return this == CONE ? 2 : 0; }
    public int getMaxSauces() { return this == CONE ? 1 : 0; }
    
}
