package com.heycream.manager;

import com.heycream.actor.Customer;
import com.heycream.model.Cup;
import com.heycream.model.Order;
import com.heycream.utils.Randomizer;

public class OrderManager 
{
    // =====================
    // SECTION: Attributes
    // ===================== 
    private int totalServes = 0;   
    private int correctServes = 0; 
    private int totalOrders = 0;  

    // =====================
    // SECTION: Constructor
    // ===================== 
    public OrderManager() {}

    // =====================
    // SECTION: Methods
    // ===================== 
    public Order generateOrder()
    {
        totalOrders++;
        return Randomizer.randomOrder();
    }

    public boolean checkMatch(Cup servedCup, Order requested)
    {
        if (requested == null || servedCup == null) return false;
        boolean result = requested.checkMatch(servedCup);
        recordServe(result);
        return result;
    }
    
    public void recordServe(boolean correct)
    {
        totalServes++;
        if (correct) correctServes++;
    }

    public boolean canServe(Cup prepared)
    {
        if (prepared == null) return false;
        if (prepared.getType() == null) return false;
        if (prepared.getSize() == null) return false;

        int required = prepared.getSize().getMaxScoops();
        return prepared.getScoops() != null && prepared.getScoops().size() == required;
    }

    public void resetStats()
    {
        totalServes = 0;
        correctServes = 0;
        totalOrders = 0;
    }
    
    // Getter , Setter
    public int getTotalServeCount(){ return totalServes; }
    public int getCorrectServeCount(){ return correctServes; }
    public int getTotalOrderCount(){ return totalOrders; }
    
}
