package com.heycream.manager;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * InteractionManager:
 * จัดการการคลิกของผู้เล่นบน item layer แล้วสั่งให้ ItemManager สร้างของจริง
 */
public class InteractionManager {

    private final ItemManager itemManager;
    private final UIManager uiManager;
    private boolean clickLocked = false;

    public InteractionManager(ItemManager itemManager, UIManager uiManager) {
        this.itemManager = itemManager;
        this.uiManager = uiManager;
    }

    public void attachToLayer(Pane itemLayer) {
        itemLayer.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (clickLocked) return; // ป้องกัน spam คลิกเร็ว
            clickLocked = true;
            new Thread(() -> {
                try { Thread.sleep(120); } catch (InterruptedException ignored) {}
                clickLocked = false;
            }).start();

            double x = e.getX();
            double y = e.getY();
            String detected = itemManager.detectItemByPosition(x, y);
            if (detected == null) return;

            System.out.println("🎯 Clicked: " + detected);

            if (detected.equals("ServeZone") || detected.equals("CupArea")) {
                itemManager.serveCurrentCup();
                uiHint("✅ Served!");
                return;
            }

            if (detected.startsWith("Cup") || detected.equals("Cone")) {
                itemManager.spawnCup(detected);
                uiHint("🧁 Cup ready!");
                return;
            }

            if (detected.startsWith("Scoop")) {
                itemManager.addScoopToCup(detected);
                uiHint("🍨 Scoop added!");
                return;
            }

            if (detected.startsWith("Topping")) {
                itemManager.addToppingToCup(detected);
                uiHint("🍒 Topping added!");
                return;
            }

            if (detected.startsWith("Sauce")) {
                itemManager.addSauceToCup(detected);
                uiHint("🍯 Sauce poured!");
                return;
            }
        });
    }

    private void uiHint(String msg) {
        if (uiManager != null) uiManager.flashHint(msg);
        else System.out.println(msg);
    }
}
