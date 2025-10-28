package com.heycream;

import com.heycream.gui.SceneFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("HeyCream 🍦");
        SceneFactory.show(primaryStage, "/com/heycream/gui/fxml/main_menu.fxml");
    }

    public static void main(String[] args) { launch(args); }
}
