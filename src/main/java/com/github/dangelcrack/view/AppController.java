package com.github.dangelcrack.view;

import com.github.dangelcrack.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController extends Controller implements Initializable {
    @FXML
    private BorderPane borderPane;
    private Controller centerController;

    @Override
    public void onOpen(Object input) throws IOException {
        //Al abrir este controlador que cargue main en el centro
        changeScene(Scenes.MAIN, null);
    }

    public void changeScene(Scenes scene, Object data) throws IOException {
        View view = loadFXML(scene);
        borderPane.setCenter(view.scene);
        this.centerController = view.controller;
        this.centerController.onOpen(data);
    }

    public void openModal(Scenes scene, String title, Controller parent, Object data) throws IOException {
        View view = loadFXML(scene);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initOwner(App.stage);
        Scene _scene = new Scene(view.scene);
        stage.setScene(_scene);
        view.controller.onOpen(parent);
        stage.showAndWait();
    }


    @Override
    public void onClose(Object output) {
        //nothing to do
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static View loadFXML(Scenes scenes) throws IOException {
        String url = scenes.getURL();
        FXMLLoader loader = new FXMLLoader(App.class.getResource(url));
        Parent p = loader.load();
        Controller c = loader.getController();
        View view = new View();
        view.scene = p;
        view.controller = c;
        return view;
    }

    @FXML
    private void closeApp() {
        System.exit(0);
    }

    @FXML
    private void goToAbout() throws IOException {
        changeScene(Scenes.ABOUT, null);
    }
    @FXML
    private void addPokemon() throws IOException {
        App.currentController.openModal(Scenes.ADDPOKEMON, "Agregando un Pokemon...", this, null);
    }
    @FXML
    private void addMove() throws IOException {
        App.currentController.openModal(Scenes.ADDMOVE, "Agregando un Movimiento...", this, null);
    }
    @FXML
    private void addObjet() throws IOException {
        App.currentController.openModal(Scenes.ADDOBJECT, "Agregando un Objeto...", this, null);
    }
    @FXML
    private void deletePokemon() throws IOException {
        App.currentController.openModal(Scenes.DELETEPOKEMON, "Borrando un Pokemon...", this, null);
    }
    @FXML
    private void deleteMove() throws IOException {
        App.currentController.openModal(Scenes.DELETEMOVE, "Borrando un Movimiento...", this, null);
    }
    @FXML
    private void deleteObjet() throws IOException {
        App.currentController.openModal(Scenes.DELETEOBJECT, "Borrando un Objeto...", this, null);
    }
    @FXML
    private void editPokemon() throws IOException {
        App.currentController.openModal(Scenes.EDITPOKEMON, "Editando un Pokemon...", this, null);
    }
    @FXML
    private void editMove() throws IOException {
        App.currentController.openModal(Scenes.EDITMOVE, "Editando un Movimiento...", this, null);
    }
    @FXML
    private void editObjet() throws IOException {
        App.currentController.openModal(Scenes.EDITOBJECT, "Editando un Objeto...", this, null);
    }

}