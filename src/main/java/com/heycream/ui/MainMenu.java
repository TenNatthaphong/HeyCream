/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.ui;

import java.util.*;
import com.heycream.manager.GameManager;

public class MainMenu {
    private Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        while (true) {
            System.out.println("\n=== HeyCream 🍦 ===");
            System.out.println("1. Start Game");
            System.out.println("2. How to Play");
            System.out.println("3. Settings");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    startGame();
                    break;
                case 2:
                    showHowToPlay();
                    break;
                case 3:
                    showSettings();
                    break;
                case 4:
                    System.out.println("Goodbye 👋");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice, try again!");
            }
        }
    }

    private void startGame() {
        GameManager gm = new GameManager();
        gm.startGame();
    }

    private void showHowToPlay() {
        System.out.println("\n🧾 How to Play:");
        System.out.println("- Serve ice cream as requested by the customer.");
        System.out.println("- Match cup size, flavor, topping, and sauce!");
        System.out.println("- Earn coins and stars to progress levels.\n");
    }

    private void showSettings() {
        System.out.println("\n⚙️ Settings (coming soon!)\n");
    }
}
