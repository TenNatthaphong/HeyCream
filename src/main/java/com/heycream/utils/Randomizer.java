/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.utils;

import java.util.*;
import com.heycream.model.*;
import static com.heycream.model.Order.*;
/**
 *
 * @author lenovo
 */
public class Randomizer {
    
    //attribute
    private static final Random R = new Random();
    private static final String[] NAMES = {
        "Piggy", "Molly", "Max", "Luna", "Milo", "Bella",
        "Coco", "Rocky", "Daisy", "Charlie", "Nana", "Poppy",
        "Ruby", "Oreo", "Pumpkin", "Pancake"
    };

    private static final Map<String, String> FLAVORS = Map.of
    (
        "Vanilla", "White",
        "Chocolate", "Brown",
        "Strawberry", "Pink",
        "Matcha", "Green",
        "Mango", "Yellow",
        "Blueberry", "Purple"
    );
    private static final Map<String, String> SAUCES = Map.of
     (
        "Caramel", "Brown",
        "Chocolate", "DarkBrown",
        "Strawberry", "Red",
        "Honey", "Golden"
    );
    private static final String[] TOPPINGS =
    {
        "Oreo", "Almond", "Walnut", "ChocoChip", "Sprinkles", "Pocky"
    };
    
    //method
    public static String randomName() 
    {
        return NAMES[R.nextInt(NAMES.length)];
    }   
    private static IceCream randomIceCream() 
    {
        List<String> flavorNames = new ArrayList<>(FLAVORS.keySet());
        String flavor = flavorNames.get(R.nextInt(flavorNames.size()));
        String color = FLAVORS.getOrDefault(flavor, "Unknown");
        return new IceCream(flavor, color);
    }

    private static Topping randomTopping() 
    {
        String name = TOPPINGS[R.nextInt(TOPPINGS.length)];
        return new Topping(name);
    }

    private static Sauce randomSauce() 
    {
        List<String> sauceNames = new ArrayList<>(SAUCES.keySet());
        String s = sauceNames.get(R.nextInt(sauceNames.size()));
        String color = SAUCES.getOrDefault(s, "Unknown");
        return new Sauce(s, color);
    }

    private static CupType randomCupType() 
    {
        double chance = R.nextDouble(); // random 0.0 - 1.0

        if (chance < 0.25)
        {
            return CupType.Cone; // 25%
        } 
        else 
        {
            return CupType.Cup;  // 75%
        }
    }
    private static CupSize randomCupSize() 
    {
        CupSize[] sizes = CupSize.values();
        return sizes[R.nextInt(sizes.length)];
    }
    
    public static Cup randomCup() 
    {
        CupType type = randomCupType();
        CupSize size = randomCupSize();
        if (type == CupType.Cone) 
        {
            size = CupSize.Medium;
        }
        Cup cup = new Cup(size, type);

        int fixedScoops;
        int maxToppings;

        // fixed scoop by cup size / type
        if (type == CupType.Cup) 
        {
            switch (size) {
                case Small -> { fixedScoops = 1; maxToppings = 1; }
                case Medium -> { fixedScoops = 2; maxToppings = 2; }
                case Large -> { fixedScoops = 3; maxToppings = 3; }
                default -> { fixedScoops = 1; maxToppings = 1; }
            }
        } 
        else
        {
            fixedScoops = 2;
            maxToppings = 2;
        }
        for (int i = 0; i < fixedScoops; i++) 
        {
            cup.addScoop(randomIceCream());
        }
        int toppingCount = R.nextInt(maxToppings + 1);
        if (toppingCount > 0) 
        {
            List<String> bag = new ArrayList<>(Arrays.asList(TOPPINGS));
            Collections.shuffle(bag, R);
            for (int i = 0; i < toppingCount && i < bag.size(); i++) 
            {
                cup.addTopping(new Topping(bag.get(i)));
            }
        }
        if (type == CupType.Cup && R.nextBoolean()) 
        {
            cup.addSauce(randomSauce());
        }
        cup.addName(randomName());
        return cup;
    }
    public static Order randomOrder() 
    {
    Cup cup = randomCup();
    return new Order(cup);    

    }


}
