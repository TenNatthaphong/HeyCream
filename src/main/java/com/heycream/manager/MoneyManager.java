package com.heycream.manager;

import com.heycream.actor.Customer;
import com.heycream.model.*;

public class MoneyManager 
{
    // =====================
    // SECTION: Attributes
    // ===================== 
    private int total = 0;

    // =====================
    // SECTION: Constructor
    // ===================== 
    public MoneyManager(){}
    
    // =====================
    // SECTION: Methods
    // ===================== 
    public void addMoney(int amount)
    {
        total += amount;
        System.out.println("💰 addMoney called: +" + amount + " → total = " + total);
        if (total < 0) total = 0;
    }

    public int calculateReward(Customer customer, Cup cup, boolean correct, double patienceRatio)
    {
        if (cup == null) return 0;
        double base = 0;
        for (IceCream scoop : cup.getScoops()) base += scoop.getPrice();
        for (Topping top : cup.getToppings()) base += top.getPrice();
        if (cup.getSauce() != null) base += cup.getSauce().getPrice();

        double result = correct ? base : base * 0.2;
        double patienceWeight = 0.4;
        double patienceEffect = (patienceWeight * Math.max(0.5, patienceRatio)) + (1 - patienceWeight);
        result *= patienceEffect;
        double tip = customer.getBehavior().getTipBonus();
        double softenedTip = 1.0 + (tip - 1.0) * 0.5;
        result *= softenedTip;

        if (!correct && patienceRatio < 0.3)
        {
            double penalty = base * (0.3 -patienceRatio) * 2;
            result = -penalty;
            System.out.println("🚨 Penalty applied! -" + penalty);
        }
        return (int) Math.round(result);
    }

    // Getter , Setter
    public int getTotal(){ return total; }
    
}
