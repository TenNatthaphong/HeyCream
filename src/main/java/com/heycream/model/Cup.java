package com.heycream.model;

import java.util.ArrayList;
import java.util.List;

public class Cup {
    private final CupType type;
    private final CupSize size;
    private final List<IceCream> scoops;
    private Topping topping;
    private Sauce sauce;

    public Cup(CupType type, CupSize size) {
        this.type = type;
        this.size = size;
        this.scoops = new ArrayList<>();
    }

    // ----- Accessors -----
    public CupType getType() { return type; }
    public CupSize getSize() { return size; }
    public List<IceCream> getScoops() { return scoops; }
    public Topping getTopping() { return topping; }
    public Sauce getSauce() { return sauce; }

    // ----- Modifiers -----
    public void addScoop(IceCream iceCream) {
        if (scoops.size() < 3) { // limit max 3 scoops
            scoops.add(iceCream);
        }
    }

    public void setTopping(Topping topping) {
        this.topping = topping;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    // ----- Helpers -----
    public String typeToString() {
        return type.name();
    }

    public String sizeToString() {
        return size.name();
    }

    // Compare if two cups have same structure
    public boolean matches(Cup other) {
        if (other == null) return false;
        boolean sameType = this.type == other.type;
        boolean sameSize = this.size == other.size;
        boolean sameScoops = this.scoops.size() == other.scoops.size();
        boolean sameTopping = (this.topping == null && other.topping == null) ||
                              (this.topping != null && other.topping != null &&
                               this.topping.getName().equals(other.topping.getName()));
        boolean sameSauce = (this.sauce == null && other.sauce == null) ||
                            (this.sauce != null && other.sauce != null &&
                             this.sauce.getName().equals(other.sauce.getName()));
        return sameType && sameSize && sameScoops && sameTopping && sameSauce;
    }

    @Override
    public String toString() {
        return size + " " + type + " with " + scoops.size() + " scoop(s)";
    }
}
