package com.heycream.model;

import com.heycream.AbstractAndInterface.CustomerBehavior;
import java.util.*;

public class Order
{
    // =====================
    // SECTION: Attributes
    // ===================== 
    private final Cup requestedCup;
    private final List<IceCream> requestedScoops;
    private final List<Topping> requestedToppings;
    private final Sauce requestedSauce;

    // =====================
    // SECTION: Constructor
    // ===================== 
    public Order(Cup requestedCup, List<IceCream> scoops, List<Topping> toppings, Sauce sauce)
    {
        this.requestedCup = requestedCup;
        this.requestedScoops = new ArrayList<>(scoops != null ? scoops : Collections.emptyList());
        this.requestedToppings = new ArrayList<>(toppings != null ? toppings : Collections.emptyList());
        this.requestedSauce = sauce;
    }

    // =====================
    // SECTION: Methods
    // ===================== 
    public boolean checkMatch(Cup actualCup)
    {
        if (actualCup == null) return false;
        boolean sameCup = requestedCup.matches(actualCup);
        
        // Compare scoops
        var expectedScoops = requestedScoops.stream().map(IceCream::getFlavor).sorted().toList();
        var actualScoops   = actualCup.getScoops().stream().map(IceCream::getFlavor).sorted().toList();
        boolean sameScoops = expectedScoops.equals(actualScoops);
        
        // Compare toppings
        var expectedToppings = requestedToppings.stream().map(Topping::getName).sorted().toList();
        var actualToppings   = actualCup.getToppings().stream().map(Topping::getName).sorted().toList();
        boolean sameToppings = expectedToppings.equals(actualToppings);
        
        // Compare sauce
        boolean sameSauce = (requestedSauce == null && actualCup.getSauce() == null) ||
                (requestedSauce != null && actualCup.getSauce() != null &&
                        requestedSauce.getName().equals(actualCup.getSauce().getName()));
        
        return sameCup && sameScoops && sameToppings && sameSauce;
    }
    
    public String describe(CustomerBehavior behavior)
    {
        StringBuilder sb = new StringBuilder("I’d like a ");

        String cupType = requestedCup.typeToString();
        if (!"CONE".equalsIgnoreCase(cupType))
        {
            sb.append(requestedCup.sizeToString().toLowerCase())
              .append(" ")
              .append(cupType.toLowerCase());
        } else {
            sb.append("cone");
        }
        sb.append(" with ");
        for (int i = 0; i < requestedScoops.size(); i++)
        {
            sb.append(requestedScoops.get(i).getFlavor());
            if (i < requestedScoops.size() - 1) sb.append(", ");
        }
        if (requestedToppings != null && !requestedToppings.isEmpty())
        {
            sb.append(", topped with ");
            for (int i = 0; i < requestedToppings.size(); i++)
            {
                sb.append(requestedToppings.get(i).getName());
                if (i < requestedToppings.size() - 1) sb.append(", ");
            }
        }
        if (requestedSauce != null)
        {
            sb.append(", and drizzled with ").append(requestedSauce.getName()).append(" sauce");
        }
        sb.append("!");
        return sb.toString();
    }

    // Getter , Setter
    public Cup getRequestedCup() { return requestedCup; }
    public List<IceCream> getRequestedScoops() { return Collections.unmodifiableList(requestedScoops); }
    public List<Topping> getRequestedToppings() { return Collections.unmodifiableList(requestedToppings); }
    public Sauce getRequestedSauce() { return requestedSauce; }
    
}
