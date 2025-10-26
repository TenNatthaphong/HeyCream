package com.heycream.model;

import com.heycream.AbstractAndInterface.DessertItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private Cup requestedCup;
    private List<IceCream> scoops;
    private Topping topping;
    private Sauce sauce;



    public Order(Cup cup) {
        this.requestedCup = cup;
        this.scoops = new ArrayList<>();
    }

    public Cup getRequestedCup() { return requestedCup; }
    public List<IceCream> getScoops() { return scoops; }

    public void addIceCream(IceCream scoop) { scoops.add(scoop); }

    public Topping getTopping() { return topping; }
    public void setTopping(Topping topping) { this.topping = topping; }

    public Sauce getSauce() { return sauce; }
    public void setSauce(Sauce sauce) { this.sauce = sauce; }
    public List<DessertItem> getAllItem() {
        List<DessertItem> items = new ArrayList<>();
        items.addAll(scoops);
        if (topping != null) items.add(topping);
        if (sauce != null) items.add(sauce);
        return items;
    }

    public double getTotalPrice() {
        double total = 0;
        total += (requestedCup.getType() == CupType.Cone) ? 10 : 15;
        total += scoops.size() * scoops.get(0).getPrice();
        if (topping != null) total += topping.getPrice();
        if (sauce != null) total += sauce.getPrice();
        return total;
    }

    public boolean checkMatch(Cup actualCup) {
        if (actualCup == null || requestedCup == null) return false;

        boolean cupMatch = requestedCup.getType() == actualCup.getType()
                && requestedCup.getSize() == actualCup.getSize();

        boolean scoopMatch = Objects.equals(
            requestedCup.getScoops().stream().map(IceCream::getFlavor).toList(),
            actualCup.getScoops().stream().map(IceCream::getFlavor).toList()
        );

        boolean toppingMatch = Objects.equals(
            (topping != null ? topping.getName() : null),
            (actualCup.getToppings().isEmpty() ? null : actualCup.getToppings().get(0).getName())
        );

        boolean sauceMatch = Objects.equals(
            (sauce != null ? sauce.getName() : null),
            (actualCup.getSauce() != null ? actualCup.getSauce().getName() : null)
        );

        return cupMatch && scoopMatch && toppingMatch && sauceMatch;
    }

    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append(requestedCup.toString()).append(" with ");
        for (int i = 0; i < scoops.size(); i++) {
            sb.append(scoops.get(i).getFlavor());
            if (i < scoops.size() - 1) sb.append(", ");
        }
        if (topping != null) sb.append(", topped with ").append(topping.getName());
        if (sauce != null) sb.append(", drizzled ").append(sauce.getName());
        return sb.toString();
    }
}
