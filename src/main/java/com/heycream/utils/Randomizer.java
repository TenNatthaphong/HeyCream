/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.utils;

import java.util.*;
import com.heycream.model.*;
/**
 *
 * @author lenovo
 */
public class Randomizer {
    
    //attribute
    private static final Random random = new Random();
    
    //method
    public static IceCream randomIceCream()
    {
        List<IceCream> iceCream = Arrays.asList(
                new IceCream("Vanilla", "white"),
                new IceCream("Chocolate", "brown"),
                new IceCream("Strawberry", "pink"),
                new IceCream("Matcha", "green")
        );
        return iceCream.get(random.nextInt(iceCream.size()));
    }
    public static Topping randomTopping() {
        List<Topping> toppings = Arrays.asList(
                new Topping("Oreo"),
                new Topping("Brownie"),
                new Topping("Banana"),
                new Topping("Marshmallow")
        );
        return toppings.get(random.nextInt(toppings.size()));
    }
    public static Sauce randomSauce() {
        List<Sauce> sauces = Arrays.asList(
                new Sauce("Chocolate", "dark brown"),
                new Sauce("Strawberry", "red"),
                new Sauce("Caramel", "golden"),
                new Sauce("Honey", "yellow")
        );
        return sauces.get(random.nextInt(sauces.size()));
    }
    public static CupSize randomCupSize() {
        CupSize[] sizes = CupSize.values();
        return sizes[random.nextInt(sizes.length)];
    }
    public static Cup randomCup()
    {
        CupSize size = randomCupSize();
        Cup cup = new Cup(size);
        int maxScoops;
        int maxToppings;

        switch (size) {
        case Small:
            maxScoops = 1;
            maxToppings = 1;
            break;
        case Medium:
            maxScoops = 2;
            maxToppings = 2;
            break;
        case Large:
            maxScoops = 3;
            maxToppings = 3;
            break;
        case Cone:
        default:
            maxScoops = 2;
            maxToppings = 2;
            break;
        
        }
        int scoopCount = 1 + random.nextInt(maxScoops);
        for (int i = 0; i < scoopCount; i++) {
            cup.addScoop(randomIceCream());
        }

        int toppingCount = random.nextInt(maxToppings + 1); // อาจไม่มี topping ก็ได้
        for (int i = 0; i < toppingCount; i++) {
            cup.addTopping(randomTopping());
        }

        cup.addSauce(randomSauce());
        return cup;
    }
}
