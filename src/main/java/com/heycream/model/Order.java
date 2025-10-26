package com.heycream.model;

import java.util.List;

public class Order {
    private final Cup requestedCup;
    private final List<IceCream> requestedScoops;
    private final Topping requestedTopping;
    private final Sauce requestedSauce;

    public Order(Cup requestedCup, List<IceCream> scoops, Topping topping, Sauce sauce) {
        this.requestedCup = requestedCup;
        this.requestedScoops = scoops;
        this.requestedTopping = topping;
        this.requestedSauce = sauce;
    }

    // ----- Accessors -----
    public Cup getRequestedCup() { return requestedCup; }
    public List<IceCream> getRequestedScoops() { return requestedScoops; }
    public Topping getRequestedTopping() { return requestedTopping; }
    public Sauce getRequestedSauce() { return requestedSauce; }

    // ----- Matching -----
    public boolean checkMatch(Cup actualCup) {
        if (actualCup == null) return false;

        boolean sameCup = requestedCup.matches(actualCup);

        boolean sameScoops = requestedScoops.size() == actualCup.getScoops().size()
                && requestedScoops.stream().map(IceCream::getFlavor).sorted().toList()
                   .equals(actualCup.getScoops().stream().map(IceCream::getFlavor).sorted().toList());

        boolean sameTopping = (requestedTopping == null && actualCup.getTopping() == null) ||
                (requestedTopping != null && actualCup.getTopping() != null &&
                 requestedTopping.getName().equals(actualCup.getTopping().getName()));

        boolean sameSauce = (requestedSauce == null && actualCup.getSauce() == null) ||
                (requestedSauce != null && actualCup.getSauce() != null &&
                 requestedSauce.getName().equals(actualCup.getSauce().getName()));

        return sameCup && sameScoops && sameTopping && sameSauce;
    }

    // ----- Order description -----
    public String description() {
        StringBuilder sb = new StringBuilder("I’d like a ");

        String cupType = requestedCup.typeToString();
        if (!cupType.equals("Cone")) {
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
        return sb.toString();
    }
}
