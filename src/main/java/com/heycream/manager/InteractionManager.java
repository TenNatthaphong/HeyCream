package com.heycream.manager;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.List;

public class InteractionManager {
    private final AnchorPane rootPane;
    private final ItemManager itemManager;

    private ImageView heldItem = null;
    private double offsetX, offsetY;

    // สถานะ order ปัจจุบัน
    private ImageView placedCup = null;
    private final List<ImageView> scoops = new ArrayList<>();
    private final List<ImageView> toppings = new ArrayList<>();
    private String sauce = null;

    public InteractionManager(AnchorPane rootPane, ItemManager itemManager) {
        this.rootPane = rootPane;
        this.itemManager = itemManager;

        rootPane.setOnMousePressed(this::handlePress);
        rootPane.setOnMouseDragged(this::handleDrag);
        rootPane.setOnMouseReleased(this::handleRelease);
    }

    private void handlePress(MouseEvent e) {
        double x = e.getX(), y = e.getY();

        if (heldItem == null) {
            String key = itemManager.detectItemByPosition(x, y);
            if (key != null && !key.equals("ServeZone")) {
                heldItem = itemManager.showItem(key);
                if (heldItem != null) {
                    heldItem.setLayoutX(x - heldItem.getFitWidth() / 2);
                    heldItem.setLayoutY(y - heldItem.getFitHeight() / 2);
                    offsetX = heldItem.getLayoutX() - x;
                    offsetY = heldItem.getLayoutY() - y;
                    System.out.println("🖐 Picked: " + key);
                }
            }
        }
    }

    private void handleDrag(MouseEvent e) {
        if (heldItem != null) {
            heldItem.setLayoutX(e.getX() + offsetX);
            heldItem.setLayoutY(e.getY() + offsetY);
        }
    }

    private void handleRelease(MouseEvent e) {
        if (heldItem == null) return;
        double x = e.getX(), y = e.getY();

        // 🧁 วางถ้วยหรือโคน
        if (itemManager.isInCupZone(x, y) && (heldItem.getId() == null || heldItem.getId().contains("Cup") || heldItem.getId().contains("Cone"))) {
            if (placedCup == null) {
                placedCup = heldItem;
                placedCup.setLayoutX(800);
                placedCup.setLayoutY(550);
                System.out.println("✅ Cup placed");
            } else {
                System.out.println("⚠ Already has a cup!");
                heldItem.setVisible(false);
            }
            heldItem = null;
            return;
        }
        if (itemManager.isInStackZone(x, y) && heldItem.getId() != null && heldItem.getId().contains("ScoopWhen")) {
            if (placedCup == null) {
                System.out.println("❌ No cup placed yet!");
            } else if (scoops.size() < 3) {
                heldItem.setLayoutX(placedCup.getLayoutX() + 15);
                heldItem.setLayoutY(placedCup.getLayoutY() - (scoops.size() * 20) - 40);
                scoops.add(heldItem);
                System.out.println("🍦 Added scoop " + (scoops.size()));
            } else {
                System.out.println("⚠ Already 3 scoops!");
                heldItem.setVisible(false);
            }
            heldItem = null;
            return;
        }

        // 🍒 topping
        if (itemManager.isInStackZone(x, y) && heldItem.getId() != null && (
                heldItem.getId().contains("Banana") ||
                heldItem.getId().contains("Oreo") ||
                heldItem.getId().contains("Cherrie") ||
                heldItem.getId().contains("Candy"))) {
            if (placedCup == null) {
                System.out.println("❌ Place cup first!");
            } else {
                heldItem.setLayoutX(placedCup.getLayoutX() + 20);
                heldItem.setLayoutY(placedCup.getLayoutY() - (scoops.size() * 25) - 60);
                toppings.add(heldItem);
                System.out.println("🍒 Added topping");
            }
            heldItem = null;
            return;
        }

        // 🍯 sauce
        if (itemManager.isInStackZone(x, y) && heldItem.getId() != null && heldItem.getId().contains("Sauce")) {
            if (placedCup == null) {
                System.out.println("❌ Place cup first!");
            } else if (sauce == null) {
                sauce = heldItem.getId();
                System.out.println("🍯 Added " + sauce);
                heldItem.setVisible(false);
            } else {
                System.out.println("⚠ Already has sauce");
                heldItem.setVisible(false);
            }
            heldItem = null;
            return;
        }

        // 💰 เสิร์ฟ
        if (itemManager.isInServeZone(x, y)) {
            if (placedCup != null) {
                System.out.println("✅ Order served! Scoops=" + scoops.size() + " Toppings=" + toppings.size() + " Sauce=" + sauce);
                rootPane.getChildren().removeAll(scoops);
                rootPane.getChildren().removeAll(toppings);
                rootPane.getChildren().remove(placedCup);
                placedCup = null;
                scoops.clear();
                toppings.clear();
                sauce = null;
            } else {
                System.out.println("⚠ Nothing to serve!");
            }
            heldItem = null;
            return;
        }
        System.out.println("❌ Dropped outside any zone");
        heldItem.setVisible(false);
        heldItem = null;
    }
}
