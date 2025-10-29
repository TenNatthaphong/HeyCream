package com.heycream.model;

import java.util.*;
import javafx.scene.image.ImageView;

public class Cup 
{
    // =====================
    // SECTION: Attributes
    // ===================== 
    private final List<IceCream> scoops = new ArrayList<>();
    private final List<Topping> toppings = new ArrayList<>();
    private Sauce sauce;
    private final CupType type;
    private final CupSize size;
    private ImageView imageView;

    // =====================
    // SECTION: Constructor
    // ===================== 
    public Cup(CupType type, CupSize size)
    {
        this.type = type;
        this.size = size;
    }

    // =====================
    // SECTION: Methods
    // =====================
    public boolean matches(Cup other)
    {
        if (other == null) return false;
        return Objects.equals(this.type, other.type)
            && Objects.equals(this.size, other.size);
    }
    
    public String typeToString() 
    { 
        return type != null ? type.typeToString() : "Unknown";
    }
    public String sizeToString() 
    { 
        return size != null ? size.sizeToString() : "Unknown";
    }
    
    // Getter , Setter
    public ImageView getImageView() { return imageView; }
    public List<IceCream> getScoops() { return scoops; }
    public List<Topping> getToppings(){ return toppings; }
    public Sauce getSauce() { return sauce; }
    public CupType getType() { return type; }
    public CupSize getSize() { return size; }
    
    public void setImageView(ImageView imageView) { this.imageView = imageView; }
    public void addScoop(IceCream i) { if (i != null) scoops.add(i); }
    public void addTopping(Topping t) {if (t != null) toppings.add(t);}
    public void setSauce(Sauce sauce) { this.sauce = sauce; }
    
}
