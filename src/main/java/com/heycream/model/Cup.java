package com.heycream.model;

import java.util.*;

public class Cup {
    private final CupType type;
    private final CupSize size;
    private final List<IceCream> scoops = new ArrayList<>();
    private Topping topping;
    private Sauce sauce;

    public Cup(CupType type, CupSize size) {
        this.type = type;
        this.size = size;
    }

    public CupType getType() { return type; }
    public CupSize getSize() { return size; }
    public List<IceCream> getScoops() { return Collections.unmodifiableList(scoops); }
    public Topping getTopping() { return topping; }
    public Sauce getSauce() { return sauce; }

    public void addScoop(IceCream iceCream) {
        if (iceCream != null && scoops.size() < 3) scoops.add(iceCream);
    }
    public void setTopping(Topping topping) { this.topping = topping; }
    public void setSauce(Sauce sauce) { this.sauce = sauce; }

    public boolean matches(Cup other) {
        if (other == null) return false;
        return this.type == other.type && this.size == other.size;
    }

    public String typeToString() { return type.name(); }
    public String sizeToString() { return size.name(); }

    @Override
    public String toString() {
        return size + " " + type + " with " + scoops.size() + " scoop(s)"
                + (topping != null ? ", topping=" + topping.getName() : "")
                + (sauce != null ? ", sauce=" + sauce.getName() : "");
    }
}
