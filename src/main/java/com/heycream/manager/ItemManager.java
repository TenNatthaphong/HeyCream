package com.heycream.manager;

import javafx.scene.Node;

/**
 * ItemManager: now supports "spawn-at-prep" flow.
 * We keep exactly one prepared cup/cone (the active prepared item).
 */
public class ItemManager {

    // Adjust these coordinates to match your ServeZone visually
    private static final double PREP_X = 760; // near cashier
    private static final double PREP_Y = 460;

    // Single prepared cup state (model), to be served later
    private com.heycream.model.Cup preparedCup;

    // Single prepared visual Node (optional - if you separate visuals and model)
    private Node preparedVisual;

    public ItemManager() {}

    /** Lock an item Node at the prep position and disable dragging. */
    public Node spawnAtPrep(Node item) {
        if (item == null) return null;
        item.setLayoutX(PREP_X);
        item.setLayoutY(PREP_Y);
        // Disable dragging to avoid oversized-drag issues
        item.setOnMousePressed(null);
        item.setOnMouseDragged(null);
        item.setOnMouseReleased(null);
        item.toFront();
        this.preparedVisual = item;
        return item;
    }

    /** Replace the current prepared cup model (cup/cone). */
    public void setPreparedCup(com.heycream.model.Cup cup) {
        this.preparedCup = cup;
    }

    /** Retrieve the prepared cup model. */
    public com.heycream.model.Cup getPreparedCup() {
        return this.preparedCup;
    }

    /** Clear both prepared model and visual after serving. */
    public void clearPrepared() {
        this.preparedCup = null;
        if (this.preparedVisual != null) {
            // Optionally hide/remove from scene graph if you manage parents here.
            this.preparedVisual.setVisible(false);
            this.preparedVisual = null;
        }
    }

    /** Helper for bringing any node to front (if you still need it). */
    public void bringToFront(Node n) {
        if (n != null) n.toFront();
    }
}
