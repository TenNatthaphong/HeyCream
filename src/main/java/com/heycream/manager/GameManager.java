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
    private Player player;
    private List<Customer> customer;
    private boolean isRunning;
    
    //constructor
    public GameManager(){}
    
    //method
    public void startGame(){}
    public void update(){}
    public void endGame(){}
}
