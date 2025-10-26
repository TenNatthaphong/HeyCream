package com.heycream.manager;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.util.HashMap;

public class ItemManager {
    private final AnchorPane rootPane;
    private final HashMap<String, ImageView> items = new HashMap<>();

    public ItemManager(AnchorPane rootPane) {
        this.rootPane = rootPane;
        preloadItems();
    }

    private void preloadItems() {
        addItem("SauceCaramel", "/com/heycream/assets/SauceCaramel.png");
        addItem("SauceChocolate", "/com/heycream/assets/SauceChocolate.png");
        addItem("SauceStrawberry", "/com/heycream/assets/SauceStrawberry.png");
        addItem("SauceHoney", "/com/heycream/assets/SauceHoney.png");

        addItem("Cone", "/com/heycream/assets/Cone.png");
        addItem("CupS", "/com/heycream/assets/CupS.png");
        addItem("CupM", "/com/heycream/assets/CupM.png");
        addItem("CupL", "/com/heycream/assets/CupL.png");

        addItem("Banana", "/com/heycream/assets/ToppingBanana.png");
        addItem("Oreo", "/com/heycream/assets/ToppingOreo.png");
        addItem("Cherrie", "/com/heycream/assets/ToppingCherrie.png");
        addItem("Candy", "/com/heycream/assets/ToppingCandy.png");

        addItem("Scoop", "/com/heycream/assets/Scoop.png");
        addItem("ScoopWhenVanilla", "/com/heycream/assets/ScoopWhenVanilla.png");
        addItem("ScoopWhenStrawberry", "/com/heycream/assets/ScoopWhenStrawberry.png");
        addItem("ScoopWhenMatcha", "/com/heycream/assets/ScoopWhenMatcha.png");
        addItem("ScoopWhenChocolate", "/com/heycream/assets/ScoopWhenChocolate.png");
        addItem("ScoopWhenBlueberry", "/com/heycream/assets/ScoopWhenBlueberry.png");
   
        addItem("ScoopVanilla", "/com/heycream/assets/ScoopVanilla.png");
        addItem("ScoopStrawberry", "/com/heycream/assets/ScoopStrawberry.png");
        addItem("ScoopMatcha", "/com/heycream/assets/ScoopMatcha.png");
        addItem("ScoopChocolate", "/com/heycream/assets/ScoopChocolate.png");
        addItem("ScoopBlueberry", "/com/heycream/assets/ScoopBlueberry.png");
    }

    private void addItem(String key, String path) {
        try {
            Image img = new Image(getClass().getResourceAsStream(path));
            ImageView view = new ImageView(img);
            view.setFitWidth(40);
            view.setPreserveRatio(true);
            view.setVisible(false);
            items.put(key, view);
        } catch (Exception e) {
            System.err.println("❌ Failed to load: " + key);
        }
    }

    public ImageView showItem(String key) 
    {
        ImageView item = items.get(key);
        if (item != null) {
            if (!rootPane.getChildren().contains(item)) {
                rootPane.getChildren().add(item);
            }
            item.setVisible(true);
            item.toFront();
            return item;
        }
        System.out.println("? Item not found: " + key);
        return null;
    }


    public String detectItemByPosition(double x, double y) {
        // sauces
        if (in(x, y, 84, 435, 20, 50)) return "SauceCaramel";       // 1
        if (in(x, y, 118, 435, 20, 50)) return "SauceChocolate";    // 2
        if (in(x, y, 151, 435, 20, 50)) return "SauceStrawberry";   // 3
        if (in(x, y, 184, 435, 20, 50)) return "SauceHoney";        // 4

        // cups & cones
        if (in(x, y, 67, 497, 20, 50)) return "Cone";              // 5
        if (in(x, y, 100, 510, 20, 30)) return "CupS";              // 6
        if (in(x, y, 133, 507, 30, 35)) return "CupM";              // 7
        if (in(x, y, 167, 502, 30, 40)) return "CupL";              // 8

        // toppings (upper row)
        if (in(x, y, 236, 390, 50, 40)) return "Banana";            // 9
        if (in(x, y, 306, 390, 50, 40)) return "Oreo";              // 10
        // toppings (lower row, y +20)
        if (in(x, y, 236, 450, 50, 40)) return "Cherrie";           // 11
        if (in(x, y, 306, 450, 50, 40)) return "Candy";             // 12

        // scoop area
        if (in(x, y, 236, 510, 45, 20)) return "Scoop";             // 13

        // ice cream bins (flavors)
        if (in(x, y, 390, 450, 60, 75)) return "ScoopWhenVanilla";  // 14
        if (in(x, y, 470, 450, 40, 75)) return "ScoopWhenStrawberry"; // 15
        if (in(x, y, 530, 450, 40, 75)) return "ScoopWhenMatcha";   // 16
        if (in(x, y, 590, 450, 40, 75)) return "ScoopWhenChocolate";// 17
        if (in(x, y, 650, 450, 40, 75)) return "ScoopWhenBlueberry";// 18
        // cashier zone
        if (in(x, y, 780, 480, 40, 100)) return "ServeZone";       // 19

        return null;
    }

    private boolean in(double x, double y, double left, double top, double w, double h) {
        return x >= left && x <= left + w && y >= top && y <= top + h;
    }

    public ImageView makeItemAt(double x, double y) {
        String key = detectItemByPosition(x, y);
        if (key != null && !key.equals("ServeZone")) {
            System.out.println("🧊 Make item: " + key);
            return showItem(key);
        }
        return null;
    }
    public boolean isInCupZone(double x, double y) 
    { 
        return in(x, y, 770, 540, 100, 45); 
    } 
    public boolean isInStackZone(double x, double y) 
    { 
        return in(x, y, 770, 470, 100, 80); 
    } 
    public boolean isInServeZone(double x, double y) 
    { 
        return in(x, y, 770, 400, 100, 160); 
    }

}
