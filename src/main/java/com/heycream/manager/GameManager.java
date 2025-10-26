package com.heycream.manager;

import com.heycream.actor.Customer;
import com.heycream.actor.Player;
import java.util.*;
import javafx.scene.control.Label;

public class GameManager {

    private final OrderManager orderManager;
    private final TimeManager timeManager;
    private final MoneyManager moneyManager;
    private final Player player;
    private final List<Customer> customers;
    private Customer currentCustomer;
    private boolean isRunning;

    public GameManager(Label timeLabel) 
    {
        player = new Player();
        customers = new ArrayList<>();
        timeManager = new TimeManager(timeLabel);
        orderManager = new OrderManager(timeManager);
        moneyManager = new MoneyManager();
        isRunning = true;
    }
    public String serveCurrentCustomer() 
    {
        if (currentCustomer == null) 
        {
            return "⚠️ No current customer!";
        }

        boolean correct = orderManager.checkOrder(player.getCurrentCup(), currentCustomer);
        int waited = timeManager.getCurrentMinute() - currentCustomer.getArrivalMinute();
        double basePrice = currentCustomer.getOrder().getTotalPrice();
        double tipBonus = currentCustomer.getBehavior().getTipBonus();

        String message;
        if (waited > currentCustomer.getBehavior().getPatience()) 
        {
            message = currentCustomer.getName() + " already left! No money gained.";
        } 
        else if (correct) 
        {
            double earned = basePrice * tipBonus;
            moneyManager.addMoney((int) earned);
            message = currentCustomer.getName() + " is happy! +%.2f coins!".formatted(earned);
        } 
        else 
        {
            moneyManager.deduct((int) (basePrice * 0.5));
            message = currentCustomer.getName() + " didn't like it! (-50%)";
        }
        return message + "\nTotal: " + moneyManager.getTotal();
    }
    public String endGameSummary() 
    {
        int totalCustomers = customers.size();
        int starsEarned;
        if (totalCustomers == 0) starsEarned = 0;
        else if (moneyManager.getTotal() >= totalCustomers * 60) starsEarned = 3;
        else if (moneyManager.getTotal() >= totalCustomers * 30) starsEarned = 2;
        else starsEarned = 1;

        return """
            🏁 Shop closed at %s
            Total customers served: %d
            Total money: %d
            Stars earned: %d
            """.formatted(timeManager.getTime(), totalCustomers, moneyManager.getTotal(), starsEarned);
    }
    public OrderManager getOrderManager() { return orderManager; }
    public TimeManager getTimeManager() { return timeManager; }
    public MoneyManager getMoneyManager() { return moneyManager; }
    public Player getPlayer() { return player; }
    public List<Customer> getCustomers() { return customers; }
    public Customer getCurrentCustomer() { return currentCustomer; }
    public void setCurrentCustomer(Customer currentCustomer) { this.currentCustomer = currentCustomer; }
}
