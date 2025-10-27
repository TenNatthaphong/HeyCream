package com.heycream.model;

import com.heycream.AbstractAndInterface.*;

public class IceCream implements DessertItem {
    private final String flavor;
    private final int price;

    public IceCream(String flavor, int price) {
        this.flavor = flavor;
        this.price = price;
    }

    public String getFlavor() { return flavor; }

    @Override
    public String getName() { return flavor; }

    @Override
    public int getPrice() { return price; }

    @Override
    public String toString() { return flavor; }
}
