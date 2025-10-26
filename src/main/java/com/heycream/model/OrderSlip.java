
package com.heycream.model;

import com.heycream.AbstractAndInterface.DessertItem;
import com.heycream.model.Order;
import com.heycream.manager.MoneyManager;

public class OrderSlip {
    
    //attribute
    private Order currentOrder;
    private MoneyManager moneyManager = new MoneyManager();

    //constructor
    public OrderSlip(Order order) {
        this.currentOrder = order;
    }
    
    //method
    public void displayOrder()
    {
        if (currentOrder == null) return;
        System.out.println("ORDER SLIP:");
//        for (DessertItem item : currentOrder.getAllItems()) 
//        {
//            System.out.printf("%s (%s) - %.2f\n", 
//            item.getType(), item.getName(), item.getPrice());
//        }

        System.out.println("-----------------");
        System.out.printf("Total: %.2f\n", moneyManager.calculateOrderPrice(currentOrder));
        System.out.println(currentOrder.description());
        System.out.println("-------------------------");
    }
    public void setOrder(Order order) { this.currentOrder = order; }
    public Order getOrder() { return currentOrder; }
}
