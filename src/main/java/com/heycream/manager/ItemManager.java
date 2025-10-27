package com.heycream.manager;

import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import java.util.*;

public class ItemManager {
    private final AnchorPane rootPane;
    private final Map<String, ImageView> items = new HashMap<>();

    public ItemManager(AnchorPane rootPane) {
        this.rootPane = rootPane;
        preloadItems();
    }

    private void addItem(String key, String path) {
        Image img;
        try { img = new Image(path); } catch (Exception e) { img = null; }
        ImageView iv = new ImageView(img);
        iv.setVisible(false);
        items.put(key, iv);
    }

    private void preloadItems() {
        addItem("Cone", "/com/heycream/assets/Cone.png");
        addItem("CupS", "/com/heycream/assets/CupS.png");
        addItem("CupM", "/com/heycream/assets/CupM.png");
        addItem("CupL", "/com/heycream/assets/CupL.png");

        addItem("Scoop", "/com/heycream/assets/Scoop.png");
        addItem("ScoopWhenVanilla", "/com/heycream/assets/ScoopWhenVanilla.png");
        addItem("ScoopWhenStrawberry", "/com/heycream/assets/ScoopWhenStrawberry.png");
        addItem("ScoopWhenMatcha", "/com/heycream/assets/ScoopWhenMatcha.png");
        addItem("ScoopWhenChocolate", "/com/heycream/assets/ScoopWhenChocolate.png");
        addItem("ScoopWhenBlueberry", "/com/heycream/assets/ScoopWhenBlueberry.png");

        addItem("SauceCaramel", "/com/heycream/assets/SauceCaramel.png");
        addItem("SauceChocolate", "/com/heycream/assets/SauceChocolate.png");
        addItem("SauceStrawberry", "/com/heycream/assets/SauceStrawberry.png");
        addItem("SauceHoney", "/com/heycream/assets/SauceHoney.png");

        addItem("Banana", "/com/heycream/assets/Banana.png");
        addItem("Oreo", "/com/heycream/assets/Oreo.png");
        addItem("Cherrie", "/com/heycream/assets/Cherrie.png");
        addItem("Candy", "/com/heycream/assets/Candy.png");
    }

    public ImageView showItem(String key) {
        ImageView item = items.get(key);
        if (item != null) {
            if (!rootPane.getChildren().contains(item)) rootPane.getChildren().add(item);
            item.setVisible(true);
            item.toFront();
        }
        return item;
    }

    private boolean in(double x, double y, double zoneX, double zoneY, double w, double h) {
        return x >= zoneX && x <= zoneX + w && y >= zoneY && y <= zoneY + h;
    }

    public boolean isInCupZone(double x, double y) { return in(x, y, 770, 540, 100, 45); }
    public boolean isInStackZone(double x, double y) { return in(x, y, 770, 470, 100, 80); }
    public boolean isInServeZone(double x, double y) { return in(x, y, 770, 400, 100, 160); }

    public String detectItemByPosition(double x, double y) {
        if (in(x, y, 84, 435, 20, 50)) return "SauceCaramel";
        if (in(x, y, 118, 435, 20, 50)) return "SauceChocolate";
        if (in(x, y, 151, 435, 20, 50)) return "SauceStrawberry";
        if (in(x, y, 184, 435, 20, 50)) return "SauceHoney";
        if (in(x, y, 67, 497, 20, 50)) return "Cone";
        if (in(x, y, 100, 510, 20, 30)) return "CupS";
        if (in(x, y, 133, 507, 30, 35)) return "CupM";
        if (in(x, y, 167, 502, 30, 40)) return "CupL";
        if (in(x, y, 236, 390, 50, 40)) return "Banana";
        if (in(x, y, 306, 390, 50, 40)) return "Oreo";
        if (in(x, y, 236, 450, 50, 40)) return "Cherrie";
        if (in(x, y, 306, 450, 50, 40)) return "Candy";
        if (in(x, y, 236, 510, 45, 20)) return "Scoop";
        if (in(x, y, 390, 450, 60, 75)) return "ScoopWhenVanilla";
        if (in(x, y, 470, 450, 40, 75)) return "ScoopWhenStrawberry";
        if (in(x, y, 530, 450, 40, 75)) return "ScoopWhenMatcha";
        if (in(x, y, 590, 450, 40, 75)) return "ScoopWhenChocolate";
        if (in(x, y, 650, 450, 40, 75)) return "ScoopWhenBlueberry";
        if (in(x, y, 780, 480, 40, 100)) return "ServeZone";
        return null;
    }
}
