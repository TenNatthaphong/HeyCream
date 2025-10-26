package com.heycream.utils;

import com.heycream.AbstractAndInterface.*;
import com.heycream.model.*;
import java.util.Random;

public class Randomizer {
    private static final Random rand = new Random();

    public static String randomName() {
        String[] names = {"Tiger", "Elephant", "Pig", "Dog", "Cat"};
        return names[rand.nextInt(names.length)];
    }

    public static CustomerBehavior randomBehavior() {
        double roll = rand.nextDouble();
        if (roll < 0.6) {
            return new CalmCustomer();    
        } else if (roll < 0.85) {
            return new RudeCustomer();   
        } else {
            return new VIPCustomer(); 
        }
    }
    
    public static Order randomOrder() {
        Cup cup = randomCup();
        Order order = new Order(cup);

        int maxScoop = switch (cup.getSize()) {
            case Small -> 1;
            case Medium -> 2;
            case Large -> 3;
        };

        for (int i = 0; i < maxScoop; i++) {
            order.addIceCream(randomFlavor());
        }

        order.setTopping(randomTopping());
        order.setSauce(randomSauce());
        return order;
    }

    public static Cup randomCup() {
        CupType type = rand.nextBoolean() ? CupType.Cone : CupType.Cup;
        CupSize size = CupSize.values()[rand.nextInt(CupSize.values().length)];
        return new Cup(type, size);
    }

    public static IceCream randomFlavor() {
        String[] flavors = {"Vanilla", "Strawberry", "Chocolate", "Matcha", "Blueberry"};
        return new IceCream(flavors[rand.nextInt(flavors.length)]);
    }

    public static Topping randomTopping() {
        String[] toppings = {"Banana", "Cherrie", "Oreo", "Candy"};
        return new Topping(toppings[rand.nextInt(toppings.length)]);
    }

    public static Sauce randomSauce() {
        String[] sauces = {"Caramel", "Chocolate", "Strawberry", "Honey"};
        return new Sauce(sauces[rand.nextInt(sauces.length)]);
    }
}
