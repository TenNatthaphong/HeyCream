/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.heycream.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.AnchorPane;

public class GameSceneController implements Initializable {

    @FXML
    private Label timeLabel;

    @FXML
    private Label coinLabel;

    @FXML
    private TextArea orderArea;

    @FXML
    private TextArea consoleArea;

    @FXML
    private Button serveButton, nextButton, backButton;

    @FXML
    private AnchorPane rootPane;

    private int coins = 0;
    private int orderNumber = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BackgroundManager.setupBackground(rootPane); // ใช้พื้นหลังเดียวกัน
        updateOrder();
    }

    @FXML
    private void onServe() {
        consoleArea.appendText("✅ Order " + orderNumber + " served successfully!\n");
        coins += 10;
        coinLabel.setText("Coins: " + coins);
    }

    @FXML
    private void onNextOrder() {
        orderNumber++;
        updateOrder();
        consoleArea.appendText("➡️ Next order loaded.\n");
    }

    private void updateOrder() {
        orderArea.setText("🍧 Order #" + orderNumber + "\n" +
                          "- 1 cup of Strawberry ice cream\n" +
                          "- Topping: Oreo + Cherrie\n" +
                          "- Sauce: Chocolate\n");
    }

    @FXML
    private void onBackToMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/heycream/gui/fxml/main_menu.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("HeyCream 🍦 - Main Menu");
        stage.centerOnScreen();
        stage.show();
    }
}
