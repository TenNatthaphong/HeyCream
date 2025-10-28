package com.heycream.model;

import com.heycream.AbstractAndInterface.*;
import javafx.scene.image.ImageView;

public class Topping implements DessertItem {
    private final String name;
    private final int price;
    private ImageView imageView;

    public Topping(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public void setImageView(ImageView view) { this.imageView = view; }
    public ImageView getImageView() { return imageView; }
    @Override
    public String getName() { return name; }

    @Override
    public int getPrice() { return price; }

    @Override
    public String toString() { return name; }
}
