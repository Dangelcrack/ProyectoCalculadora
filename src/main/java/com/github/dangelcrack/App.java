package com.github.dangelcrack;

import com.github.dangelcrack.view.AppController;
import com.github.dangelcrack.view.Scenes;
import com.github.dangelcrack.view.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    public static Stage stage;
    public static AppController currentController;

    //este el es primer método que se ejecuta al abrir la primera ventana
    @Override
    public void start(Stage stage) throws IOException{
        //view/layout.fxml
        View view = AppController.loadFXML(Scenes.ROOT);
        scene = new Scene(view.scene, 1105, 654);
        currentController = (AppController) view.controller;
        currentController.onOpen(null);
        stage.setScene(scene);
        try {
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/github/dangelcrack/media/ModalImageUtils/iconPokemon.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.setTitle("Pokemon Calculator");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        //  scene.setRoot(loadFXML(fxml));
    }


    public static void main(String[] args) {
        launch();
    }

}