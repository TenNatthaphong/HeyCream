/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.manager;

import com.heycream.actor.Customer;
import com.heycream.actor.Player;
import java.util.*;
/**
 *
 * @author lenovo
 */
public class GameManager {

    // attribute
    private OrderManager orderManager;
    private TimeManager timeManager;
    private MoneyManager moneyManager;
    private UIManager uiManager;
    private Player player;
    private List<Customer> customers;
    private boolean isRunning;
    private Customer currentCustomer;
    private int customerStartMinute; 

    // constructor
    public GameManager() {
        player = new Player();
        customers = new ArrayList<>();
        orderManager = new OrderManager();
        timeManager = new TimeManager();
        moneyManager = new MoneyManager();
        uiManager = new UIManager();
        isRunning = true;
        currentCustomer = null;
    }

    // method
    public void startGame() 
    {
        System.out.println("HeyCream Game Starting...");
        System.out.println("Shop opens at 12:00 and closes at 21:00\n");

        while (timeManager.isOpen()) 
        {
           timeManager.tick();
           for (Customer c : customers) 
           {
                if (!c.hasLeft()) 
                {
                    int waited = timeManager.getCurrentMinute() - c.getArrivalMinute();                 
                    if (waited > c.getBehavior().getPatience()) 
                    {
                        c.setLeft(true);
                        System.out.println(c.getName() + " (" + c.getBehavior().getMood() + ") got tired and left!");
                    }
                }
            }

            if (Math.random() < 0.3) 
            {
                int id = customers.size() + 1;
                Customer c = orderManager.generateOrder(id);
                customers.add(c);

                uiManager.setOrder(c.getOrder());
                uiManager.showOrder();

                System.out.println("Player preparing order...");
                player.prepareOrder(c.getOrder());

                boolean correct = orderManager.checkOrder(player.getCurrentCup(), c);
                int waited = timeManager.getCurrentMinute() - c.getArrivalMinute();
                double basePrice = c.getOrder().getTotalPrice();
                double tipBonus = c.getBehavior().getTipBonus();

                if (waited > c.getBehavior().getPatience()) 
                {
                    System.out.println(c.getName() + " already left! No money gained.");
                } 
                else if (correct) 
                {
                    System.out.println(c.getName() + " is happy! (" + c.getBehavior().getMood() + ")");
                    double earned = basePrice * tipBonus;
                    moneyManager.addMoney((int)earned);
                    System.out.println("+%.2f coins!".formatted(earned));
                } 
                else 
                {
                    System.out.println(c.getName() + " didn't like it! (-50%)");
                    moneyManager.deduct((int)(basePrice * 0.5));
                }
                System.out.println("Current total: " + moneyManager.getTotal());
            }
        }
        endGame();
    }

    public void endGame() {
        System.out.println("\nShop closed at " + timeManager.getTime());
        System.out.println("Total customers served: " + customers.size());
        System.out.println("Total money: " + moneyManager.getTotal());

         int totalCustomers = customers.size();
         int starsEarned;
         if (totalCustomers == 0) starsEarned = 0;
         else if (moneyManager.getTotal() >= totalCustomers * 60) starsEarned = 3;
         else if (moneyManager.getTotal() >= totalCustomers * 30) starsEarned = 2;
         else starsEarned = 1;

        System.out.println("Stars earned: " + starsEarned);
    }
}
