package com.heycream.model;

import com.heycream.AbstractAndInterface.*;
import javafx.scene.image.ImageView;

public class Sauce implements DessertItem 
{
    // =====================
    // SECTION: Attributes
    // ===================== 
    private final String name;
    private final int price;
    private ImageView imageView;

    // =====================
    // SECTION: Constructor
    // ===================== 
    public Sauce(String name, int price)
    {
        this.name = name;
        this.price = price;
    }

    // =====================
    // SECTION: Methods
    // ===================== 
    public ImageView getImageView() { return imageView; }
    @Override public String getName() { return name; }
    @Override public int getPrice() { return price; }
    
    public void setImageView(ImageView view) { this.imageView = view; }

}
