package com.github.dangelcrack.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the "About" view. This class initializes the view
 * and sets the background image for the VBox layout.
 */
public class AboutController extends Controller implements Initializable {

    @FXML
    private VBox vbox;

    /**
     * Called when the view is opened. This method is currently empty and can be
     * customized to perform actions upon opening the view.
     *
     * @param input an input object for the view
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void onOpen(Object input) throws IOException {
        // Custom actions when the view is opened can be added here
    }

    /**
     * Called when the view is closed. This method is currently empty and can be
     * customized to perform actions upon closing the view.
     *
     * @param output an output object for the view
     */
    @Override
    public void onClose(Object output) {
        // Custom actions when the view is closed can be added here
    }

    /**
     * Initializes the controller class. This method sets the background image for the VBox layout.
     *
     * @param location  the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/ModalImageUtils/FondoAbout.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imageUrl.toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, false, true)
        );
        vbox.setBackground(new Background(backgroundImage));
    }
}
