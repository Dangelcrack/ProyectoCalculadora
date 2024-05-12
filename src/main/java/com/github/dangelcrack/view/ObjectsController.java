package com.github.dangelcrack.view;

import com.github.dangelcrack.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ObjectsController extends Controller implements Initializable {
    @Override
    public void onOpen(Object input) {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private void addObjet() throws IOException {
        App.currentController.openModal(Scenes.ADDOBJECT, "Agregando un Objeto...", this, null);
    }
    @FXML
    private void deleteObjet() throws IOException {
        App.currentController.openModal(Scenes.DELETEOBJECT, "Borrando un Objeto...", this, null);
    }

    @FXML
    private void editObjet() throws IOException {
        App.currentController.openModal(Scenes.EDITOBJECT, "Editando un Objeto...", this, null);
    }
}
