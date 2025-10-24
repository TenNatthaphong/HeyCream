package com.heycream.gui;

import com.heycream.gui.BackgroundManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MainMenuController implements Initializable{

    @FXML
    private Button startButton;
    @FXML
    private Button howToPlayButton;
    @FXML
    private Button settingButton;
    @FXML
    private Button exitButton;
    @FXML
    private AnchorPane rootPane; 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BackgroundManager.setupBackground(rootPane);
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
    private void onHowToPlay() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/heycream/gui/fxml/how_to_play.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600); 
        Stage stage = (Stage) howToPlayButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("How to Play 🍨");
        stage.setWidth(900);  
        stage.setHeight(600);
        stage.centerOnScreen(); 
        stage.show();
    }



    @FXML
    private void onSetting() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/heycream/gui/fxml/settings.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) settingButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Settings ⚙️");
        stage.show();
    }
    
    @FXML
    private void onExit() {
        System.exit(0);
    }
}
