package com.heycream.manager;

import com.heycream.model.*;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import java.util.*;

public class ItemManager {

    private final Pane itemLayer;
    private final Map<String, Image> itemImages = new HashMap<>();
    private Cup currentCup;
    private GameManager gameManager;

    // 📍 ตำแหน่งเสิร์ฟ
    private static final double SERVE_X = 740;
    private static final double SERVE_Y = 510;

    // 🔒 ป้องกันคลิกซ้ำใน 0.1 วินาที
    private long lastScoopTime = 0;

    public ItemManager(Pane itemLayer) {
        this.itemLayer = itemLayer;
        preloadItems();
    }

    public void setGameManager(GameManager gm) {
        this.gameManager = gm;
    }

    // =======================================================
    // 🔹 โหลดภาพทั้งหมด
    // =======================================================
    private void preloadItems() {
        addItem("Cone", "/com/heycream/assets/Cone.png");
        addItem("CupS", "/com/heycream/assets/CupS.png");
        addItem("CupM", "/com/heycream/assets/CupM.png");
        addItem("CupL", "/com/heycream/assets/CupL.png");

        addItem("ScoopVanilla", "/com/heycream/assets/ScoopVanilla.png");
        addItem("ScoopStrawberry", "/com/heycream/assets/ScoopStrawberry.png");
        addItem("ScoopMatcha", "/com/heycream/assets/ScoopMatcha.png");
        addItem("ScoopChocolate", "/com/heycream/assets/ScoopChocolate.png");
        addItem("ScoopBlueberry", "/com/heycream/assets/ScoopBlueberry.png");

        addItem("SauceCaramel", "/com/heycream/assets/SauceCaramel.png");
        addItem("SauceChocolate", "/com/heycream/assets/SauceChocolate.png");
        addItem("SauceStrawberry", "/com/heycream/assets/SauceStrawberry.png");
        addItem("SauceHoney", "/com/heycream/assets/SauceHoney.png");

        addItem("ToppingBanana", "/com/heycream/assets/ToppingBanana.png");
        addItem("ToppingOreo", "/com/heycream/assets/ToppingOreo.png");
        addItem("ToppingCherrie", "/com/heycream/assets/ToppingCherrie.png");
        addItem("ToppingCandy", "/com/heycream/assets/ToppingCandy.png");
    }

    private void addItem(String name, String path) {
        try {
            itemImages.put(name, new Image(path));
        } catch (Exception e) {
            System.err.println("⚠ Failed to load: " + path);
        }
    }

    // =======================================================
    // 🔹 ตรวจตำแหน่งคลิก
    // =======================================================
    public String detectItemByPosition(double x, double y) {
        if (in(x, y, 84, 435, 20, 50)) return "SauceCaramel";
        if (in(x, y, 118, 435, 20, 50)) return "SauceChocolate";
        if (in(x, y, 151, 435, 20, 50)) return "SauceStrawberry";
        if (in(x, y, 184, 435, 20, 50)) return "SauceHoney";

        if (in(x, y, 67, 497, 20, 50)) return "Cone";
        if (in(x, y, 100, 510, 20, 30)) return "CupS";
        if (in(x, y, 133, 507, 30, 35)) return "CupM";
        if (in(x, y, 167, 502, 30, 40)) return "CupL";

        if (in(x, y, 236, 390, 50, 40)) return "ToppingBanana";
        if (in(x, y, 306, 390, 50, 40)) return "ToppingOreo";
        if (in(x, y, 236, 450, 50, 40)) return "ToppingCherrie";
        if (in(x, y, 306, 450, 50, 40)) return "ToppingCandy";

        if (in(x, y, 390, 450, 60, 75)) return "ScoopVanilla";
        if (in(x, y, 470, 450, 40, 75)) return "ScoopStrawberry";
        if (in(x, y, 530, 450, 40, 75)) return "ScoopMatcha";
        if (in(x, y, 590, 450, 40, 75)) return "ScoopChocolate";
        if (in(x, y, 650, 450, 40, 75)) return "ScoopBlueberry";

        if (in(x, y, 750, 480, 40, 100)) return "ServeZone";
        return null;
    }

    private boolean in(double x, double y, double bx, double by, double w, double h) {
        return x >= bx && x <= bx + w && y >= by && y <= by + h;
    }

    // =======================================================
    // 🔹 Cup
    // =======================================================
    public void spawnCup(String type) {
        if (currentCup != null) {
            System.out.println("⚠ Already have a cup.");
            return;
        }

        CupType cupType = type.equals("Cone") ? CupType.CONE : CupType.CUP;
        CupSize cupSize = sizeFromName(type);
        currentCup = new Cup(cupType, cupSize);

        ImageView view = new ImageView(itemImages.get(type));
        view.setFitHeight(35);
        view.setPreserveRatio(true);
        view.setLayoutX(SERVE_X);
        view.setLayoutY(SERVE_Y);
        itemLayer.getChildren().add(view);
        currentCup.setImageView(view);

        System.out.println("🧁 Spawned cup: " + type);
    }

    private CupSize sizeFromName(String type) {
        if (type.endsWith("S")) return CupSize.S;
        if (type.endsWith("M")) return CupSize.M;
        if (type.endsWith("L")) return CupSize.L;
        return CupSize.M;
    }

    // =======================================================
    // 🔹 Scoop
    // =======================================================
    public void addScoopToCup(String scoopName) {
        if (currentCup == null) {
            System.out.println("❌ No cup selected!");
            return;
        }

        int count = currentCup.getScoops().size();
        int max = currentCup.getSize().getMaxScoops();
        if (count >= max) {
            System.out.println("⚠ Max scoops reached!");
            return;
        }

        long now = System.currentTimeMillis();
        if (now - lastScoopTime < 60) return;
        lastScoopTime = now;

        ImageView scoop = new ImageView(itemImages.get(scoopName));
        scoop.setFitHeight(28);
        scoop.setPreserveRatio(true);
        scoop.setLayoutX(SERVE_X );
        scoop.setLayoutY(SERVE_Y - (20 * (count + 1)));
        itemLayer.getChildren().add(scoop);
        scoop.toFront();

        String flavor = scoopName.replace("Scoop", "");
        IceCream ice = new IceCream(flavor, 10);
        ice.setImageView(scoop);
        currentCup.addScoop(ice);

        System.out.println("🍨 Added scoop: " + flavor);
    }

    // =======================================================
    // 🔹 Topping
    // =======================================================
    public void addToppingToCup(String toppingName) {
        if (currentCup == null || currentCup.getScoops().isEmpty()) {
            System.out.println("⚠ Need cup + scoop first!");
            return;
        }

        double offsetX = (Math.random() - 0.5) * 10;
        double offsetY = 10 + (Math.random() * 8);

        ImageView topping = new ImageView(itemImages.get(toppingName));
        topping.setFitHeight(20);
        topping.setPreserveRatio(true);
        topping.setLayoutX(SERVE_X + offsetX);
        topping.setLayoutY(SERVE_Y - (25 * currentCup.getScoops().size()) + offsetY);
        itemLayer.getChildren().add(topping);
        topping.toFront();

        Topping top = new Topping(toppingName.replace("Topping", ""), 5);
        top.setImageView(topping);
        currentCup.addTopping(top);

        System.out.println("🍒 Added topping: " + toppingName);
    }

    // =======================================================
    // 🔹 Sauce
    // =======================================================
    public void addSauceToCup(String sauceName) {
        if (currentCup == null || currentCup.getScoops().isEmpty()) {
            System.out.println("⚠ Need cup + scoop before sauce!");
            return;
        }

        ImageView bottle = new ImageView(itemImages.get(sauceName));
        bottle.setFitHeight(40);
        bottle.setPreserveRatio(true);
        bottle.setLayoutX(SERVE_X - 25);
        bottle.setLayoutY(SERVE_Y - 80);
        itemLayer.getChildren().add(bottle);

        RotateTransition pour = new RotateTransition(Duration.seconds(1), bottle);
        pour.setFromAngle(0);
        pour.setToAngle(120);
        pour.setAutoReverse(true);
        pour.setCycleCount(3);
        pour.setOnFinished(e -> itemLayer.getChildren().remove(bottle));
        pour.play();

        Sauce sauce = new Sauce(sauceName.replace("Sauce", ""), 8);
        sauce.setImageView(bottle);
        currentCup.setSauce(sauce);

        System.out.println("🍯 Added sauce: " + sauceName);
    }

    // =======================================================
    // 🔹 Serve / Clear
    // =======================================================
    public Cup getCurrentCup() {
        return currentCup;
    }

    public void serveCurrentCup() {
        if (currentCup == null) {
            System.out.println("⚠ No cup to serve!");
            return;
        }
        int scoops = currentCup.getScoops().size();
        int max = currentCup.getSize().getMaxScoops();
        if (scoops < max) {
            System.out.println("⏳ Not enough scoops yet! (" + scoops + "/" + max + ")");
            return;
        }

        if (gameManager != null) {
            gameManager.resolveServe(currentCup, this::clearAllPreparedVisuals);
        } else {
            System.out.println("⚠ GameManager not linked.");
        }
    }

    public void clearAllPreparedVisuals() {
    itemLayer.getChildren().clear();  
    currentCup = null;
    System.out.println("🧹 Cleared all visuals from itemLayer.");
}

}
