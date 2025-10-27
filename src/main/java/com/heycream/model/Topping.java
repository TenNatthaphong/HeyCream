package com.heycream.model;

import com.heycream.AbstractAndInterface.*;

public class Topping implements DessertItem {
    private final String name;
    private final int price;

    public Topping(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() { return name; }

    @Override
    public int getPrice() { return price; }

    @Override
    public String toString() { return name; }
}
