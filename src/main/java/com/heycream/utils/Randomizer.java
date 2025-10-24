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
    public static String randomCustomerName(int index) {
        String base = NAMES[R.nextInt(NAMES.length)];
        return base + "#" + index;
    }
    
    public static Cup randomCup() {
        CupSize size = CupSize.values()[R.nextInt(CupSize.values().length)];
        CupType type = CupType.values()[R.nextInt(CupType.values().length)];
        return new Cup(size, type);
    }
    
    public static Order randomOrder() {
        Cup cup = randomCup();
        Order order = new Order(cup);

        int scoopCount = 1 + R.nextInt(MAX_SCOOPS);
        addDistinct(order, "IceCream", new ArrayList<>(FLAVORS.keySet()), scoopCount);

        int toppingCount = R.nextInt(MAX_TOPPINGS + 1);
        addDistinct(order, "Topping", TOPPINGS, toppingCount);

        if (R.nextBoolean())
        {
            List<String> sauceNames = new ArrayList<>(SAUCES.keySet());
            String s = sauceNames.get(R.nextInt(sauceNames.size()));
            String color = SAUCES.getOrDefault(s, "Unknown");
            order.addItem(new Sauce(s, color));
        }
        return order;
    }
    private static void addDistinct(Order order, String type, List<String> names, int count) 
    {
        List<String> bag = new ArrayList<>(names);
        Collections.shuffle(bag, R);
        for (int i = 0; i < count && i < bag.size(); i++) 
        {
            String name = bag.get(i);
            switch (type) 
            {
                case "IceCream":
                    String color = FLAVORS.getOrDefault(name, "Unknown");
                    order.addItem(new IceCream(name, color));
                    break;
                case "Topping":
                    order.addItem(new Topping(name));
                    break;
            }
        }
    }
    private static void addDistinct(Order order, String type, String[] pool, int count) 
    {
        addDistinct(order, type, Arrays.asList(pool), count);
    }

}
