package com.heycream.model;

import com.heycream.AbstractAndInterface.*;
import javafx.scene.image.ImageView;

public class IceCream implements DessertItem {
    private final String flavor;
    private final int price;
    private ImageView imageView;



    public IceCream(String flavor, int price) {
        this.flavor = flavor;
        this.price = price;
    }

    public void setImageView(ImageView view) { this.imageView = view; }
    public ImageView getImageView() { return imageView; }
    public String getFlavor() { return flavor; }

    @Override
    public String getName() { return flavor; }

    @Override
    public int getPrice() { return price; }

    @Override
    public String toString() { return flavor; }
}
