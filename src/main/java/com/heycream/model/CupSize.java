package com.heycream.model;

public enum CupSize 
{
    // ENUM
    S(1, 1, 1),  
    M(2, 2, 1),   
    L(3, 3, 1);  
    
    // =====================
    // SECTION: Attributes
    // ===================== 
    private final int maxScoops;
    private final int maxToppings;
    private final int maxSauces;

    // =====================
    // SECTION: Constructor
    // ===================== 
    CupSize(int maxScoops, int maxToppings, int maxSauces)
    {
        this.maxScoops = maxScoops;
        this.maxToppings = maxToppings;
        this.maxSauces = maxSauces;
    }

    // =====================
    // SECTION: Methods
    // ===================== 
    public String sizeToString()
    {
        return switch (this)
        {
            case S -> "Small";
            case M -> "Medium";
            case L -> "Large";
        };
    }
    
    // Getter , Setter
    public int getMaxScoops() { return maxScoops; }
    public int getMaxToppings() { return maxToppings; }
    public int getMaxSauces() { return maxSauces; }

}
