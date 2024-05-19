package com.github.dangelcrack;

import com.github.dangelcrack.view.AppController;
import com.github.dangelcrack.view.Scenes;
import com.github.dangelcrack.view.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main JavaFX application class that extends from Application.
 */
public class App extends Application {

    /** Static scene that can be accessed across the application. */
    public static Scene scene;
    /** Primary stage of the application. */
    public static Stage stage;
    /** Current controller in use. */
    public static AppController currentController;

    /**
     * The first method that gets executed when the application starts.
     * It initializes the primary stage with the ROOT scene.
     * @param stage The primary stage for this application, onto which
     *              the application scene can be set.
     * @throws IOException if loading the FXML file fails.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Load the root layout from the FXML file.
        View view = AppController.loadFXML(Scenes.ROOT);
        // Create a new scene with the loaded view and set dimensions.
        scene = new Scene(view.scene, 1105, 654);
        // Get the controller associated with the ROOT view.
        currentController = (AppController) view.controller;
        // Perform any actions required on opening the application.
        currentController.onOpen(null);
        // Set the scene on the primary stage.
        stage.setScene(scene);
        // Attempt to set the application icon.
        try {
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/github/dangelcrack/media/ModalImageUtils/iconPokemon.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Set the title of the primary stage window.
        stage.setTitle("Pokemon Calculator");
        // Display the primary stage.
        stage.show();
    }

    /**
     * Sets the root of the main scene to the specified FXML layout.
     * @param fxml The name of the FXML file to load.
     * @throws IOException if loading the FXML file fails.
     */
    public static void setRoot(String fxml) throws IOException {
        // scene.setRoot(loadFXML(fxml)); // Uncomment to enable functionality.
    }

    /**
     * The main entry point for all JavaFX applications.
     * @param args The command line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Launch the JavaFX application.
        launch();
    }
}