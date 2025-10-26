
package com.heycream.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class HowToPlayController implements Initializable{

    @FXML
    private Button backButton;

    @FXML
    private TextArea instructionsText;
    @FXML
    private AnchorPane rootPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BackgroundBase.setupBase(rootPane);
        String text = """
                🎮 Instructions:
                1. Click 'Start Game' to open your HeyCream shop.
                2. Customers will appear and order ice creams, toppings, and sauces.
                3. Match the order exactly to make your customers happy! (calm / impatient / excited)
                4. The happier they are, the more coins you earn.
                5. Serve as many customers as possible before the shop closes!

                🕒 Shop Hours: 12:00 - 21:00

                ⭐ Goal:
                Earn as many coins as possible before closing time!
                """;

        instructionsText.setText(text);
    }
    @FXML
    private void onBackToMenu() throws IOException 
    {
        System.out.println("Returning to menu...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/heycream/gui/fxml/main_menu.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("HeyCream 🍦 - Main Menu");
        stage.setWidth(900); 
        stage.setHeight(600);
        stage.centerOnScreen(); 
        stage.show();
    }
}

