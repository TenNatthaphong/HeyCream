package com.heycream.gui;

import com.heycream.manager.GameManager;
import com.heycream.actor.Customer;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.application.Platform;
import java.net.URL;
import java.util.ResourceBundle;

public class GameSceneController implements Initializable {

    @FXML private AnchorPane rootPane;
    @FXML private Label timeLabel;
    @FXML private Label coinLabel;
    @FXML private TextArea orderArea;
    @FXML private TextArea consoleArea;
    @FXML private Button serveButton;
    @FXML private Button nextButton;
    @FXML private Button backButton;

    private GameManager manager;
    private Timeline clock;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            BackgroundBase.setupBase(rootPane);
            setupFoodTruck();
            setupGame();
        });
    }

    private void setupGame() 
    {
        manager = new GameManager();
        clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            manager.getTimeManager().tick();
            updateTime();
        }));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        consoleArea.appendText("🍦 Shop opened! 12:00\n");
        coinLabel.setText("💰 0");
        orderArea.setText("Press 'Next Order' to start!");
    }

    @FXML
    private void onNextOrder() 
    {
        Customer c = manager.getOrderManager().generateOrder(manager.getCustomers().size() + 1);
        manager.setCurrentCustomer(c);
        manager.getCustomers().add(c);

        orderArea.setText(c.getOrder().toString());
        consoleArea.appendText("🧍 Customer " + c.getName() + " has arrived!\n");
    }

    @FXML
    private void onServe() 
    {
        if (manager.getCurrentCustomer() == null) 
        {
            consoleArea.appendText("⚠️ No current customer!\n");
            return;
        }

        boolean result = manager.getOrderManager().checkOrder(manager.getPlayer().getCurrentCup(), manager.getCurrentCustomer());
        if (result) 
        {
            int earned = (int) (manager.getCurrentCustomer().getOrder().getTotalPrice() *
                    manager.getCurrentCustomer().getBehavior().getTipBonus());
            manager.getMoneyManager().addMoney(earned);
            showCoinGain(earned);
            consoleArea.appendText("✅ " + manager.getCurrentCustomer().getName() +
                    " is happy! +" + earned + " coins\n");
        } 
        else 
        {
            consoleArea.appendText("❌ Wrong order served!\n");
        }

        coinLabel.setText("💰 " + manager.getMoneyManager().getTotal());
    }

    @FXML
    private void onBackToMenu() 
    {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/com/heycream/gui/fxml/main_menu.fxml")
            );
            javafx.scene.Scene scene = new javafx.scene.Scene(loader.load());
            javafx.stage.Stage stage = (javafx.stage.Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("HeyCream 🍦 - Main Menu");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setupFoodTruck() {
        ImageView truck = new ImageView(
            new Image(getClass().getResource("/com/heycream/assets/FoodTruck.png").toExternalForm())
        );
        truck.setFitWidth(900);
        truck.setPreserveRatio(true);
        truck.setLayoutX(0);
        truck.setLayoutY(0);
        rootPane.getChildren().add(truck);
    }

    private void showCoinGain(int amount) {
        Text gainText = new Text("+" + amount + "💰");
        gainText.setStyle("-fx-font-size: 22px; -fx-fill: #00cc66; -fx-font-weight: bold;");
        gainText.setLayoutX(740);
        gainText.setLayoutY(45);

        rootPane.getChildren().add(gainText);

        TranslateTransition rise = new TranslateTransition(Duration.seconds(1.5), gainText);
        rise.setByY(-40);

        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), gainText);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);

        rise.setOnFinished(e -> rootPane.getChildren().remove(gainText));
        rise.play();
        fade.play();
    }
    private void updateTime() {
        timeLabel.setText("🕒 " + manager.getTimeManager().getTime());
    }
}
