package com.heycream.model;

import java.util.ArrayList;
import java.util.List;

public class Cup {
    private CupType type; // Cone or Cup
    private CupSize size; // S, M, L
    private List<IceCream> scoops;
    private List<Topping> toppings;
    private Sauce sauce;

    public Cup(CupType type, CupSize size) {
        this.type = type;
        this.size = size;
        this.scoops = new ArrayList<>();
        this.toppings = new ArrayList<>();
    }

    public CupType getType() { return type; }
    public CupSize getSize() { return size; }
    public List<IceCream> getScoops() { return scoops; }
    public List<Topping> getToppings() { return toppings; }
    public Sauce getSauce() { return sauce; }

    public void addScoop(IceCream scoop) { scoops.add(scoop); }
    public void addTopping(Topping topping) { toppings.add(topping); }
    public void addSauce(Sauce sauce) { this.sauce = sauce; }

    @Override
    public String toString() {
        return type + (type == CupType.Cone ? "" : " " + size);
    }
}
