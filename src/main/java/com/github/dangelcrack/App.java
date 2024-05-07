package com.github.dangelcrack;

import com.github.dangelcrack.view.AppController;
import com.github.dangelcrack.view.Scenes;
import com.github.dangelcrack.view.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    public static Stage stage;
    public static AppController currentController;

    //este el es primer método que se ejecuta al abrir la primera ventana
    @Override
    public void start(Stage stage) throws IOException {
        //view/layout.fxml
        View view = AppController.loadFXML(Scenes.ROOT);
        scene = new Scene(view.scene, 1105, 647);
        currentController = (AppController) view.controller;
        currentController.onOpen(null);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        //  scene.setRoot(loadFXML(fxml));
    }


    public static void main(String[] args) {
        launch();
    }

}