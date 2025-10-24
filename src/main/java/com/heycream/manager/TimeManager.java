/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.manager;

import java.util.*;
import java.time.*;
import java.time.format.*;
/**
 *
 * @author lenovo
 */
public class TimeManager {

    // attribute
    private static int currentHour ;
    private static int currentMinute;
    private boolean isOpen;

    // configuration
    private final int openHour = 12;
    private final int closeHour = 21;

    // constructor
    public TimeManager() {
        currentHour = openHour;
        currentMinute = 0;
        isOpen = true;
    }

    // method
    public void tick() 
    {
        currentMinute += 10;
        if (currentMinute >= 60) 
        {
            currentMinute = 0;
            currentHour++;
        }
        System.out.println("Time: " + getTime());
        setOpenClose();
        
    }
    public boolean isOpen()
    {
        return isOpen;
    }
    private void setOpenClose()
    {
        if (currentHour >= closeHour)
        {
            isOpen = false;
            System.out.println("The shop is now closed!");
        }
    }
    public String getTime() 
    {
        return String.format("%02d:%02d", currentHour, currentMinute);
    }
    public void reset() 
    {
        currentHour = openHour;
        currentMinute = 0;
        isOpen = true;
    }
    public int getCurrentMinute() 
    {
    return (currentHour - 12) * 60 + currentMinute;
    }
    public int getCurrentHour()
    { 
        return currentHour-12; 
    }
}

