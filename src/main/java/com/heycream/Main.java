package com.heycream;


import com.heycream.gui.SceneFactory;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("HeyCream 🍦");
        primaryStage.getIcons().add(new Image(
            getClass().getResource("/com/heycream/assets/Logo.png").toExternalForm()
        ));
        SceneFactory.show(primaryStage, "/com/heycream/gui/fxml/main_menu.fxml");
    }
    public static void main(String[] args)
    { 
        launch(args); 
    }
}
