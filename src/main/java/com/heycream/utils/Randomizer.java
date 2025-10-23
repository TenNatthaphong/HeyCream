/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.utils;

import java.util.*;
import com.heycream.model.*;
import static com.heycream.model.CupSize.Large;
import static com.heycream.model.CupSize.Medium;
import static com.heycream.model.CupSize.Small;
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
    public static String randomName() {
        List<String> name = Arrays.asList(
                ("Jiew"),
                ("Ten"),
                ("Nick"),
                ("Makham")
        );
        return name.get(random.nextInt(name.size()));
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
    public static CupType randomCupType() {
        CupType[] types = CupType.values();
        return types[random.nextInt(types.length)];
    }
    public static CupSize randomCupSize() {
        CupSize[] sizes = CupSize.values();
        return sizes[random.nextInt(sizes.length)];
    }
    public static Cup randomCup()
    {
        CupType type = randomCupType();
        CupSize size = randomCupSize();
        Cup cup = new Cup(size,type);
        int maxScoops = 0;
        int maxToppings = 0;
        
        if(type.getLabel()=="Cup")
        {
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
            defualt:
            maxScoops = 3;
            maxToppings = 3;
            break;
            }
        }
        else
        {
            maxScoops = 2;
            maxToppings = 2;
        }
        int scoopCount = 1 + random.nextInt(maxScoops);
        for (int i = 0; i < scoopCount; i++) {
           cup.addScoop(randomIceCream());
         }
        int toppingCount = random.nextInt(maxToppings + 1);
         for (int i = 0; i < toppingCount; i++) {
            cup.addTopping(randomTopping());
        }
        cup.addName(randomName());
        cup.addSauce(randomSauce());
        return cup;
    }
}
