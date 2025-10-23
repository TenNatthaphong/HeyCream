/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.manager;

/**
 *
 * @author lenovo
 */
public class TimeManager {
    
    //attribute
    private int currentHour;
    private int currentMinute;
    private boolean isOpen;
    
    //constructor
    public TimeManager()
    {
        currentHour = 12;
        currentMinute = 0;
        isOpen = true;
    }
    
    //method
    public void tick()
    {
        currentMinute += 10;
        if(currentMinute >= 60)
        {
            currentMinute = 0;
            currentHour++;
        }
        setOpenClose();
    }
    public boolean isOpen() { return isOpen; }
    private void setOpenClose()
    {
        if (currentHour >= 21)
        {
            isOpen = false;
        }
    }
    
    public String getTime() {
        return String.format("%02d:%02d", currentHour, currentMinute);
    }
    
}
