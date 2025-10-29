package com.heycream.model;

import com.heycream.AbstractAndInterface.*;
import javafx.scene.image.ImageView;

public class IceCream implements DessertItem 
{
    // =====================
    // SECTION: Attributes
    // ===================== 
    private final String flavor;
    private final int price;
    private ImageView imageView;

    // =====================
    // SECTION: Constructor
    // ===================== 
    public IceCream(String flavor, int price)
    {
        this.flavor = flavor;
        this.price = price;
    }

    // =====================
    // SECTION: Methods
    // ===================== 
    
    // Getter , Setter
    public ImageView getImageView() { return imageView; }
    public String getFlavor() { return flavor; }
    @Override public String getName() { return flavor; }
    @Override public int getPrice() { return price; }
    
    public void setImageView(ImageView view) { this.imageView = view; }
}
