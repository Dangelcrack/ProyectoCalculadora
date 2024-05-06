package com.github.dangelcrack.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddMoveController extends Controller implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private ChoiceBox<String> category;
    @FXML
    private Slider power;
    private MainController controller;

    @Override
    public void onOpen(Object input){
        this.controller = (MainController) input;
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
