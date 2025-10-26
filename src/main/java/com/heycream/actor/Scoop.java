
package com.heycream.actor;

import com.heycream.model.*;

public class Scoop {
    
    //attribute
    private IceCream flavor;
    private Cup cup;
    private boolean isPlaced;
    
    //constructor
    public Scoop(){}
    
    //method
    public void take(IceCream flavor)
    {
        this.flavor = flavor;
        this.isPlaced = false;
    }
    public void dropInto(Order order)
    {
        cup = order.getRequestedCup();
        isPlaced = true;
    } 
    public IceCream getFlavor(){ return flavor; }
}
