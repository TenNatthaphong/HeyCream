package com.heycream.model;

import com.heycream.AbstractAndInterface.CustomerBehavior;
import java.util.*;
import java.util.stream.Collectors;

public class Order {
    private final Cup requestedCup;
    private final List<IceCream> requestedScoops;
    private final Topping requestedTopping;
    private final Sauce requestedSauce;

    public Order(Cup requestedCup, List<IceCream> scoops, Topping topping, Sauce sauce) {
        this.requestedCup = requestedCup;
        this.requestedScoops = new ArrayList<>(scoops != null ? scoops : Collections.emptyList());
        this.requestedTopping = topping;
        this.requestedSauce = sauce;
    }

    public Cup getRequestedCup() { return requestedCup; }
    public List<IceCream> getRequestedScoops() { return Collections.unmodifiableList(requestedScoops); }
    public Topping getRequestedTopping() { return requestedTopping; }
    public Sauce getRequestedSauce() { return requestedSauce; }

    public boolean checkMatch(Cup actualCup) {
        if (actualCup == null) return false;

        boolean sameCup = requestedCup.matches(actualCup);

        var expected = requestedScoops.stream().map(IceCream::getFlavor).sorted().collect(Collectors.toList());
        var actual   = actualCup.getScoops().stream().map(IceCream::getFlavor).sorted().collect(Collectors.toList());
        boolean sameScoops = expected.equals(actual);

        boolean sameTopping = (requestedTopping == null && actualCup.getTopping() == null) ||
                (requestedTopping != null && actualCup.getTopping() != null &&
                 requestedTopping.getName().equals(actualCup.getTopping().getName()));

        boolean sameSauce = (requestedSauce == null && actualCup.getSauce() == null) ||
                (requestedSauce != null && actualCup.getSauce() != null &&
                 requestedSauce.getName().equals(actualCup.getSauce().getName()));

        return sameCup && sameScoops && sameTopping && sameSauce;
    }

    public String describe(CustomerBehavior behavior) {
    StringBuilder sb = new StringBuilder("I’d like a ");

    String cupType = requestedCup.typeToString();
    if (!"CONE".equalsIgnoreCase(cupType)) {
        sb.append(requestedCup.sizeToString().toLowerCase())
          .append(" ")
          .append(cupType.toLowerCase());
    } else {
        sb.append("cone");
    }

    sb.append(" with ");
    for (int i = 0; i < requestedScoops.size(); i++) {
        sb.append(requestedScoops.get(i).getFlavor());
        if (i < requestedScoops.size() - 1) sb.append(", ");
    }

    if (requestedTopping != null) {
        sb.append(", topped with ").append(requestedTopping.getName());
    }
    if (requestedSauce != null) {
        sb.append(", and drizzled with ").append(requestedSauce.getName()).append(" sauce");
    }
    sb.append("!");

    // Add behavior text at the end
    if (behavior != null) {
        sb.append(" (").append(behavior.getText()).append(")");
    }

    return sb.toString();
}

}
