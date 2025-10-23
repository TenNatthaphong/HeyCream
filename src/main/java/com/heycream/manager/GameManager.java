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
    
    //attribute
    private OrderManager orderManager;
    private TimeManager timeManager;
    private MoneyManager moneyManager;
    private UIManager uiManager;
    private Player player;
    private List<Customer> customers;
    private boolean isRunning;
    
    //constructor
    public GameManager()
    {
        player = new Player();
        customers = new ArrayList<>();
        orderManager = new OrderManager();
        timeManager = new TimeManager();
        moneyManager = new MoneyManager();
        uiManager = new UIManager();
        isRunning = true;
    }
    
    //method
    public void startGame()
    {
        System.out.println("HeyCream Game Starting...");
        while(isRunning)
        {
            update();
        }
        endGame();
    }
    public void update()
    {
        timeManager.tick();
        if(!timeManager.isOpen())
        {
            isRunning = false;
            return;
        }
        Customer c= orderManager.generateOrder();
        customers.add(c);
        System.out.println(c.getName() + " wants : " + c.getOrder());
        player.serve(c);
        
        boolean isCorrect = orderManager.checkOrder(player.getCurrentCup(),c);
        if(isCorrect)
        {
            uiManager.showResult(true);
            moneyManager.addMoney(100);
        }
        else
        {
            uiManager.showResult(false);
            moneyManager.deduct(50);
        }
    }
    public void endGame()
    {
        moneyManager.calculateStars();
        System.out.println("\n🏁 Shop closed!");
        System.out.println("💰 Total money: " + moneyManager.getTotal());
        System.out.println("⭐ Stars earned: " + moneyManager.getStars());
    }
}
