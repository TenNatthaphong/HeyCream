package com.heycream.gui;

import com.heycream.gui.util.HasRootPane;
import com.heycream.gui.util.SceneFactory;
import com.heycream.manager.SoundManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainMenuController implements HasRootPane {

    @FXML private AnchorPane rootPane;
    @FXML private Button startButton;
    @FXML private Button exitButton;
    @FXML private ToggleButton muteButton;

    @FXML
    public void initialize() {
        startButton.setOnAction(e ->
            SceneFactory.show((Stage) startButton.getScene().getWindow(),
                    "/com/heycream/gui/fxml/game_scene.fxml"));

        exitButton.setOnAction(e -> Platform.exit());

        muteButton.setOnAction(e -> {
            SoundManager.toggleMute();
            muteButton.setText(SoundManager.isMuted() ? "🔇" : "🔈");
        });
        muteButton.setText(SoundManager.isMuted() ? "🔇" : "🔈");
    }

    @Override
    public AnchorPane getRootPane() { return rootPane; }
}
