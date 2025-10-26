
package com.heycream.manager;


import com.heycream.AbstractAndInterface.DessertItem;
import com.heycream.model.Order;
import java.util.List;

public class MoneyManager {
    
    // attribute
    private int total;
    private int stars;
    
    // constructor
    public MoneyManager() {
        total = 0;
        stars = 0;
    }
    public double calculateOrderPrice(Order order) 
    {
        double totalPrice = 0.0;
        List<DessertItem> items = order.getAllItem();
        for (DessertItem item : items) 
        {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }
    public void addOrderIncome(Order order) 
    {
        int price = (int) Math.round(calculateOrderPrice(order));
        addMoney(price);
    }
    public void addMoney(int amount) 
    {
        total += amount;
    }
    public void deduct(int amount)
    {
        if (total > 0) total -= amount;
    }
    public void calculateStars()
    {
        stars = total / 500;
    }
    public int getTotal() { return total; }
    public int getStars() { return stars; }
}