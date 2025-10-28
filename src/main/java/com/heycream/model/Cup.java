package com.heycream.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Cup represents the ice cream container.
 * Holds scoops, toppings, and sauce.
 * Provides helper methods for comparison and text representation.
 */
public class Cup {

    private List<IceCream> scoops = new ArrayList<>();
    private List<Topping> toppings = new ArrayList<>();
    private Sauce sauce;
    private Topping topping; // optional main topping (if you treat it separately)
    private CupType type;
    private CupSize size;

    public Cup(CupType type, CupSize size) {
        this.type = type;
        this.size = size;
    }

    // =========================
    // Getters & Setters
    // =========================

    public List<IceCream> getScoops() {
        return scoops;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public void addTopping(Topping t) {
        if (t != null) toppings.add(t);
    }

    /** Optional single topping shortcut (if used by checkMatch) */
    public Topping getTopping() {
        // return the first topping or explicit field if you prefer single-topping logic
        if (topping != null) return topping;
        if (!toppings.isEmpty()) return toppings.get(0);
        return null;
    }

    public void setTopping(Topping t) {
        this.topping = t;
        if (t != null && !toppings.contains(t)) toppings.add(t);
    }

    public Sauce getSauce() {
        return sauce;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    public CupType getType() {
        return type;
    }

    public CupSize getSize() {
        return size;
    }

    /** Shortcut for readable cup type. */
    public String typeToString() {
        return type != null ? type.typeToString() : "Unknown";
    }

    /** Shortcut for readable cup size. */
    public String sizeToString() {
        return size != null ? size.sizeToString() : "Unknown";
    }

    // =========================
    // Matching logic
    // =========================

    /**
     * Compare this cup with another cup to see if they are the same container type and size.
     * Ignores scoops and toppings.
     */
    public boolean matches(Cup other) {
        if (other == null) return false;
        return Objects.equals(this.type, other.type)
            && Objects.equals(this.size, other.size);
    }
}
