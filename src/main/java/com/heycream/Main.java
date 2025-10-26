
package com.heycream;

import com.heycream.manager.GameManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{
    private static final double WIDTH = 900;
    private static final double HEIGHT = 600;
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(getClass().getResource("/com/heycream/gui/fxml/main_menu.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/heycream/gui/fxml/main_menu.fxml"));
        Scene scene = new Scene(loader.load(), WIDTH, HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/com/heycream/gui/css/style.css").toExternalForm());
        primaryStage.setTitle("HeyCream 🍦");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
