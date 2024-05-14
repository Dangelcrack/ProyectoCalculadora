package com.github.dangelcrack.view;

import com.github.dangelcrack.model.dao.MoveDAO;
import com.github.dangelcrack.model.dao.PokemonDAO;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Pokemon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteMoveController extends Controller implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private ComboBox<Move> movesComboBox;
    private MovesController controller;
    @Override
    public void onOpen(Object input) throws IOException {
        this.controller = (MovesController) input;
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/ModalImageUtils/img.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imageUrl.toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, false, true)
        );
        vbox.setBackground(new Background(backgroundImage));
        List<Move> moves;
        moves = MoveDAO.build().findAll();
        ObservableList<Move> observableNames = FXCollections.observableArrayList(moves);
        movesComboBox.setItems(observableNames);
    }
    @FXML
    private void closeWindow(Event event) {
        String moveName = movesComboBox.getValue().getNameMove();
        if (moveName != null) {
            Move move = MoveDAO.build().findByName(moveName);
            if (move != null) {
                this.controller.deleteMove(move);
            }
        }
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
