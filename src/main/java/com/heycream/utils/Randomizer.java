package com.heycream.utils;

import com.heycream.model.*;
import com.heycream.AbstractAndInterface.*;
import com.heycream.actor.*;
import java.util.*;

public class Randomizer {
    private static final Random R = new Random();

    public static String randomName() {
        String[] names = {"Tiger", "Elephant", "Pig", "Cat", "Dog"};
        return names[R.nextInt(names.length)];
    }

    public static CustomerBehavior randomBehavior() {
        double roll = R.nextDouble();
        if (roll < 0.6) return new CalmCustomer();
        if (roll < 0.85) return new RudeCustomer();
        return new VIPCustomer();
    }

    /** ✅ Random order that strictly follows cup/size limits */
    public static Order randomOrder() {
        // 1️⃣ เลือกประเภทถ้วย
        CupType type = R.nextDouble() < 0.7 ? CupType.CUP : CupType.CONE;
        CupSize size = (type == CupType.CUP)
                ? CupSize.values()[R.nextInt(CupSize.values().length)]
                : CupSize.M; // cone behaves like Medium

        Cup cup = new Cup(type, size);

        // 2️⃣ กำหนด limit จาก enum
        int maxScoops, maxToppings, maxSauces;
        if (type == CupType.CONE) {
            maxScoops = type.getMaxScoops();
            maxToppings = type.getMaxToppings();
            maxSauces = type.getMaxSauces();
        } else {
            maxScoops = size.getMaxScoops();
            maxToppings = size.getMaxToppings();
            maxSauces = size.getMaxSauces();
        }
        int scoopCount = maxScoops;
        List<IceCream> scoops = new ArrayList<>();
        for (int i = 0; i < scoopCount; i++) {
            scoops.add(randomFlavor());
        }
        List<Topping> toppings = new ArrayList<>();
        int toppingCount = R.nextInt(maxToppings + 1); 
        for (int i = 0; i < toppingCount; i++) {
            toppings.add(randomTopping());
        }
        Sauce sauce = null;
        if (R.nextDouble() < 0.5 && maxSauces > 0) {
            sauce = randomSauce();
        }
        return new Order(cup, scoops, toppings, sauce);
    }

    private static IceCream randomFlavor() {
        String[] flavors = {"Vanilla", "Strawberry", "Matcha", "Chocolate", "Blueberry"};
        String f = flavors[R.nextInt(flavors.length)];
        return new IceCream(f, 20);
    }

    private static Topping randomTopping() {
        String[] toppings = {"Cherry", "Oreo", "Banana", "Candy", "Peanut"};
        String t = toppings[R.nextInt(toppings.length)];
        return new Topping(t, 10);
    }

    private static Sauce randomSauce() {
        String[] sauces = {"Caramel", "Chocolate", "Strawberry", "Honey"};
        String s = sauces[R.nextInt(sauces.length)];
        return new Sauce(s, 8);
    }

    private static String randomFrom(String... arr) {
        return arr[R.nextInt(arr.length)];
    }
}
