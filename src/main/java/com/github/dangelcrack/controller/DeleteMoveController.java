package com.github.dangelcrack.controller;
import com.github.dangelcrack.model.dao.MoveDAO;
import com.github.dangelcrack.model.entity.Move;
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
    /**
     * This method is called when the view is opened.
     * @param input The MovesController instance passed as input.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void onOpen(Object input) throws IOException {
        this.controller = (MovesController) input;
    }
    /**
     * This method is called when the view is closed.
     * @param output The output data to be passed, not used in this implementation.
     */
    @Override
    public void onClose(Object output) {
    }
    /**
     * Initializes the controller class and sets up the background image and moves combo box.
     * @param location The location used to resolve relative paths for the root object, or null if unknown.
     * @param resources The resources used to localize the root object, or null if not localized.
     */
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
        List<Move> moves = MoveDAO.build().findAll();
        ObservableList<Move> observableNames = FXCollections.observableArrayList(moves);
        movesComboBox.setItems(observableNames);
    }

    /**
     * Handles the close window event, deletes the selected move, and hides the window.
     * @param event The event that triggered the method call.
     */
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