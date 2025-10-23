/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.heycream;

import com.heycream.manager.GameManager;
/**
 *
 * @author lenovo
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("HeyCream Game Starting...");
        GameManager game = new GameManager();
        game.startGame();
    }
}
