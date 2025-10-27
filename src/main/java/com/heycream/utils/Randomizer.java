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

    public static Order randomOrder() {
        CupType type = R.nextDouble() < 0.7 ? CupType.CUP : CupType.CONE;
        CupSize size = type == CupType.CUP ? CupSize.values()[R.nextInt(CupSize.values().length)] : CupSize.M;
        Cup base = new Cup(type, size);

        int n = 1 + R.nextInt(3);
        List<IceCream> scoops = new ArrayList<>();
        for (int i = 0; i < n; i++) scoops.add(randomFlavor());

        Topping topping = R.nextDouble() < 0.6 ? new Topping(randomFrom("Cherrie","Oreo","Banana","Candy"), 10) : null;
        Sauce sauce = R.nextDouble() < 0.5 ? new Sauce(randomFrom("Caramel","Chocolate","Strawberry","Honey"), 8) : null;

        return new Order(base, scoops, topping, sauce);
    }

    private static IceCream randomFlavor() {
        String[] flavors = {"vanilla","strawberry","matcha","chocolate","blueberry"};
        String f = flavors[R.nextInt(flavors.length)];
        return new IceCream(f, 20);
    }

    private static String randomFrom(String... arr) { return arr[R.nextInt(arr.length)]; }
}
