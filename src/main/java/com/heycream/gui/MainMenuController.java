package com.heycream.gui;

import com.heycream.gui.BackgroundBase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class MainMenuController implements Initializable{

    @FXML
    private Button startButton;
    @FXML
    private Button exitButton;
    @FXML
    private AnchorPane rootPane; 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BackgroundBase.setupBase(rootPane);

    }

    @FXML
    private void onStartGame() throws IOException 
    {
        System.out.println("🎮 Starting game...");
 URL fxmlPath = getClass().getResource("/com/heycream/gui/fxml/game_scene.fxml");
    System.out.println("FXML Path: " + fxmlPath);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/heycream/gui/fxml/game_scene.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);
        Stage stage = (Stage) startButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("HeyCream 🍦 - Game");
        stage.centerOnScreen();
        stage.show();
    }
    
    @FXML
    private void onExit() {
        System.exit(0);
    }
}
