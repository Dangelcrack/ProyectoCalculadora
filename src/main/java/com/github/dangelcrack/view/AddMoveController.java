package com.github.dangelcrack.view;

import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Pokemon;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddMoveController extends Controller implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private TextField name;
    @FXML
    private ComboBox<Types> type;
    @FXML
    private ComboBox<Category> category;
    @FXML
    private ComboBox<Pokemon> pokemonCanLearn;
    @FXML
    private Slider power;
    @FXML
    public Label powerValue;

    private MovesController controller;

    @Override
    public void onOpen(Object input){
        this.controller = (MovesController) input;
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/ModalImageUtils/imgAddPokemon.png");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imageUrl.toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        vbox.setBackground(new Background(backgroundImage));
        type.setItems(FXCollections.observableArrayList(Types.values()));
        category.setItems(FXCollections.observableArrayList(Category.values()));
        power.valueProperty().addListener((observable, oldValue, newValue) -> powerValue.setText(String.valueOf(newValue.intValue())));
    }
    @FXML
    private void closeWindow(Event event) {
        int powerValue = (int) power.getValue();
        Types typeMove = type.getValue();
        Move move = new Move(name.getText(),typeMove,category.getValue() ,powerValue,new ArrayList<Pokemon>());
        this.controller.saveMove(move);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
