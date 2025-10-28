package com.heycream.manager;

import javafx.scene.Node;
import javafx.scene.control.Button;
import java.util.function.Supplier;
import com.heycream.model.Cup;
import com.heycream.model.IceCream;
import com.heycream.model.Topping;
import com.heycream.model.Sauce;

/**
 * InteractionManager: click-to-spawn flow.
 * Each palette button calls a factory that builds the visual & updates model state.
 */
public class InteractionManager {

    private final ItemManager itemManager;
    private final UIManager uiManager;

    public InteractionManager(ItemManager itemManager, UIManager uiManager) {
        this.itemManager = itemManager;
        this.uiManager = uiManager;
    }

    /** Bind a generic palette button to spawn a ready-made visual node at prep spot. */
    public void bindPaletteButton(Button btn, Supplier<Node> visualFactory) {
        btn.setOnAction(e -> {
            Node node = visualFactory.get();
            itemManager.spawnAtPrep(node);
            if (uiManager != null) uiManager.flashHint("Placed at prep area.");
        });
    }

    /** Example: click Cup/Cone button. Create cup model & visual, then pin at prep. */
    public void bindCupButton(Button cupBtn, Supplier<Cup> cupModelFactory, Supplier<Node> cupVisualFactory) {
        cupBtn.setOnAction(e -> {
            Cup cup = cupModelFactory.get();
            itemManager.setPreparedCup(cup);
            Node visual = cupVisualFactory.get();
            itemManager.spawnAtPrep(visual);
            if (uiManager != null) uiManager.flashHint("Cup is ready at prep.");
        });
    }

    /** Example: click Scoop button. Requires a prepared cup; add scoop to the model. */
    public void bindScoopButton(Button scoopBtn, Supplier<IceCream> scoopFactory, Supplier<Node> scoopVisualFactory) {
        scoopBtn.setOnAction(e -> {
            Cup prepared = itemManager.getPreparedCup();
            if (prepared == null) {
                if (uiManager != null) uiManager.flashHint("Place a cup/cone first.");
                return;
            }
            IceCream scoop = scoopFactory.get();
            prepared.getScoops().add(scoop);
            Node visual = scoopVisualFactory.get();
            itemManager.spawnAtPrep(visual); // visually keep at prep area (layering handled by your scene)
            if (uiManager != null) uiManager.flashHint("Added a scoop.");
        });
    }

    /** Example: click Topping. */
    public void bindToppingButton(Button toppingBtn, Supplier<Topping> toppingFactory, Supplier<Node> toppingVisualFactory) {
        toppingBtn.setOnAction(e -> {
            Cup prepared = itemManager.getPreparedCup();
            if (prepared == null) {
                if (uiManager != null) uiManager.flashHint("Place a cup/cone first.");
                return;
            }
            Topping top = toppingFactory.get();
            prepared.getToppings().add(top);
            Node visual = toppingVisualFactory.get();
            itemManager.spawnAtPrep(visual);
            if (uiManager != null) uiManager.flashHint("Added a topping.");
        });
    }

    /** Example: click Sauce. */
    public void bindSauceButton(Button sauceBtn, Supplier<Sauce> sauceFactory, Supplier<Node> sauceVisualFactory) {
        sauceBtn.setOnAction(e -> {
            Cup prepared = itemManager.getPreparedCup();
            if (prepared == null) {
                if (uiManager != null) uiManager.flashHint("Place a cup/cone first.");
                return;
            }
            Sauce sauce = sauceFactory.get();
            prepared.setSauce(sauce);
            Node visual = sauceVisualFactory.get();
            itemManager.spawnAtPrep(visual);
            if (uiManager != null) uiManager.flashHint("Added sauce.");
        });
    }
}
