package com.heycream.manager;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Single-click pickup / place interaction.
 * - Click inventory zone -> pick/replace held item.
 * - Click ServeZone while holding -> place & lock (cannot pick again).
 * - ScoopWhen* can be picked only if currently holding Scoop or another ScoopWhen*.
 * - Placed items are marked by userData="placed".
 */
public class InteractionManager {

    private final AnchorPane rootPane;
    private final ItemManager itemManager;

    private ImageView heldItem;   // item currently following the cursor
    private String heldKey;       // key of heldItem

    public InteractionManager(AnchorPane rootPane, ItemManager itemManager) {
        this.rootPane = rootPane;
        this.itemManager = itemManager;
    }

    /**
     * Called from mouse click: pick a new item or place the held one.
     */
    public void pickOrPlace(double x, double y) {
        // If holding something now:
        if (heldItem != null) {
            // 1) Try to place only in ServeZone
            if (itemManager.isInServeZone(x, y)) {
                placeAt(x, y);
                lockPlaced(heldItem); // cannot pick again later
                clearHold();
                return;
            }

            // 2) Not in ServeZone -> check if user clicked another inventory cell to switch held item
            String clickedKey = itemManager.detectItemByPosition(x, y);
            if (clickedKey != null && !"ServeZone".equals(clickedKey)) {
                // flavored scoop rule: must be holding Scoop or ScoopWhen* to switch/take ScoopWhen*
                if (clickedKey.startsWith("ScoopWhen")) {
                    if (!"Scoop".equals(heldKey) && (heldKey == null || !heldKey.startsWith("ScoopWhen"))) {
                        // not allowed to take flavored scoop if not holding scoop
                        return;
                    }
                }
                // Switch held
                take(clickedKey, x, y);
            }
            // 3) Otherwise ignore (keep holding)
            return;
        }

        // Not holding anything -> try to pick something from inventory zones.
        String key = itemManager.detectItemByPosition(x, y);
        if (key == null || "ServeZone".equals(key)) {
            return; // clicked empty area or just zone
        }

        // flavored scoop rule: cannot pick ScoopWhen* if not holding Scoop (we hold nothing here)
        if (key.startsWith("ScoopWhen")) {
            return;
        }

        take(key, x, y);
    }

    /* -------------------- helpers -------------------- */

    /** Take an item into hand (follow cursor) if it is not locked/placed. */
    private void take(String key, double x, double y) {
        ImageView item = itemManager.showItem(key);
        if (item == null) return;

        // If already placed (locked), do not allow picking again
        Object tag = item.getUserData();
        if (tag != null && "placed".equals(tag.toString())) {
            return;
        }

        // hide previous held (if any) to avoid visual junk
        if (heldItem != null && heldItem != item) {
            heldItem.setVisible(false);
        }

        heldKey = key;
        heldItem = item;
        heldItem.setVisible(true);
        heldItem.toFront();

        // snap to cursor now and bind follow
        moveTo(heldItem, x, y);
        bindMouseFollow();
    }

    /** Place current held item at click position (centered). */
    private void placeAt(double x, double y) {
        if (heldItem == null) return;
        moveTo(heldItem, x, y);
        // Once placed, we leave it visible where it is.
    }

    /** Mark an ImageView as placed/locked (cannot be picked again). */
    private void lockPlaced(ImageView iv) {
        if (iv != null) {
            iv.setUserData("placed");
        }
    }

    /** Clear the current held state. */
    private void clearHold() {
        heldItem = null;
        heldKey = null;
        // keep mouse move handler; it does nothing if heldItem is null
    }

    /** Move an item to mouse-centered position. */
    private void moveTo(ImageView iv, double x, double y) {
        double w = iv.getFitWidth() > 0 ? iv.getFitWidth() : (iv.getImage() != null ? iv.getImage().getWidth() : 0);
        double h = iv.getFitHeight() > 0 ? iv.getFitHeight() : (iv.getImage() != null ? iv.getImage().getHeight() : 0);
        iv.setLayoutX(x - w / 2.0);
        iv.setLayoutY(y - h / 2.0);
    }

    /** Bind mouse move so that a held item follows cursor smoothly. */
    private void bindMouseFollow() {
        rootPane.setOnMouseMoved(m -> {
            if (heldItem != null) {
                moveTo(heldItem, m.getX(), m.getY());
            }
        });
    }
}
