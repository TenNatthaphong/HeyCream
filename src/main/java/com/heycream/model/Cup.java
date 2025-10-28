package com.heycream.model;

import javafx.scene.image.ImageView;
import java.util.*;

/**
 * Cup represents the ice cream container.
 * Holds scoops, toppings, and sauce.
 * Provides helper methods for comparison and text representation.
 */
public class Cup {

    private final List<IceCream> scoops = new ArrayList<>();
    private final List<Topping> toppings = new ArrayList<>();
    private Sauce sauce;
    private final CupType type;
    private final CupSize size;

    // 👇 เพิ่มฟิลด์สำหรับเชื่อมกับภาพบนจอ
    private transient ImageView imageView;

    public Cup(CupType type, CupSize size) {
        this.type = type;
        this.size = size;
    }

    // =====================================================
    // 🔹 ImageView getter / setter (ใช้ใน ItemManager)
    // =====================================================
    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    // =====================================================
    // 🔹 Scoops / Toppings / Sauce
    // =====================================================
    public List<IceCream> getScoops() {
        return scoops;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public void addTopping(Topping t) {
        if (t != null) toppings.add(t);
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

    // =====================================================
    // 🔹 Readable text
    // =====================================================
    public String typeToString() {
        return type != null ? type.typeToString() : "Unknown";
    }

    public String sizeToString() {
        return size != null ? size.sizeToString() : "Unknown";
    }

    // =====================================================
    // 🔹 Matching logic
    // =====================================================
    public boolean matches(Cup other) {
        if (other == null) return false;
        return Objects.equals(this.type, other.type)
            && Objects.equals(this.size, other.size);
    }
}
