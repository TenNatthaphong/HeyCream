package com.heycream.gui;

import com.heycream.gui.AnimationUtil;
import com.heycream.gui.AppTheme;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public final class SceneFactory {
    private SceneFactory() {}

    public static void show(Stage stage, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneFactory.class.getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root, AppTheme.WIDTH, AppTheme.HEIGHT);
            scene.getStylesheets().add(
                SceneFactory.class.getResource(AppTheme.CSS).toExternalForm()
            );

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setWidth(AppTheme.WIDTH);
            stage.setHeight(AppTheme.HEIGHT);

            Object controller = loader.getController();
            if (controller instanceof HasRootPane h) {
                AnchorPane rootPane = h.getRootPane();
                AnimationUtil.addBackgroundWithClouds(
                    rootPane, AppTheme.BG_IMAGE, AppTheme.CLOUD_IMAGE,
                    AppTheme.WIDTH, AppTheme.HEIGHT, AppTheme.CLOUD_COUNT
                );
            }

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
