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

    // constructor
    public GameManager() {
        player = new Player();
        customers = new ArrayList<>();
        orderManager = new OrderManager();
        timeManager = new TimeManager();
        moneyManager = new MoneyManager();
        uiManager = new UIManager();
        isRunning = true;
    }

    // method
    public void startGame() 
    {
        System.out.println("HeyCream Game Starting...");
        System.out.println("Shop opens at 12:00 and closes at 21:00\n");

        while (timeManager.isOpen()) 
        {
            timeManager.tick(); 
            if(timeManager.isOpen())
            {
                if (Math.random() < 0.3) 
                { 
                    int id = customers.size() + 1;
                    System.out.println("Customer " + id + " enters at " + timeManager.getTime() + "...");
                    Customer c = orderManager.generateOrder(id);
                    customers.add(c);

                    uiManager.setOrder(c.getOrder());
                    uiManager.showOrder();

                    System.out.println("Player preparing order...");
                    player.prepareOrder(c.getOrder());
                    player.serve(c);

                    boolean correct = orderManager.checkOrder(player.getCurrentCup(), c);
                    if (correct) {
                        uiManager.showResult(true);
                        moneyManager.addMoney(100);
                    } else {
                        uiManager.showResult(false);
                        moneyManager.deduct(50);
                    }
                    System.out.println("Current total: " + moneyManager.getTotal() + "\n");
                }
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
         else if (moneyManager.getTotal() >= totalCustomers * 100) starsEarned = 3;
         else if (moneyManager.getTotal() >= totalCustomers * 50) starsEarned = 2;
         else starsEarned = 1;

        System.out.println("Stars earned: " + starsEarned);
    }
}
