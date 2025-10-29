package com.heycream.actor;

import com.heycream.AbstractAndInterface.*;
import com.heycream.model.Order;
import javafx.scene.image.ImageView;

public class Customer 
{
    // =====================
    // SECTION: Attributes
    // =====================
    private final String name;
    private final Order order;
    private final CustomerBehavior behavior;
    private final int arrivalMinute;
    private ImageView imageView;

    // =====================
    // SECTION: Constructor
    // =====================
    public Customer(String name, Order order, CustomerBehavior behavior, int arrivalMinute)
    {
        this.name = name;
        this.order = order;
        this.behavior = behavior;
        this.arrivalMinute = arrivalMinute;
    }
    
    // =====================
    // SECTION: Methods
    // =====================
    public ImageView getImageView()
    {
        return imageView;
    }

    public void setImageView(ImageView imageView)
    {
        this.imageView = imageView;
    }
    
    // Getter , Setter
    public String getName() { return name; }
    public Order getOrder() { return order; }
    public CustomerBehavior getBehavior() { return behavior; }
    public int getArrivalMinute() { return arrivalMinute; }
    public String getSpeech() { return order != null ? order.describe(behavior) : "Hello!"; }
}
