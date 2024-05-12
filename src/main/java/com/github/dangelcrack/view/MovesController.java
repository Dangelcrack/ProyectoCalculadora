package com.github.dangelcrack.view;

import com.github.dangelcrack.App;
import com.github.dangelcrack.model.dao.MoveDAO;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Pokemon;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MovesController extends Controller implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private TableView<Move> tableView;
    @FXML
    private TableColumn<Move, String> columnName;
    @FXML
    private TableColumn<Move, Image> columnType;
    @FXML
    private TableColumn<Move, Image> columnCategory;
    @FXML
    private TableColumn<Move,Integer> columnPower;
    @FXML
    private TableColumn<Move, String> columnListPokemonCanLearn;

    public ObservableList<Move> moves;
    @Override
    public void onOpen(Object input) {
        List<Move> moves = MoveDAO.build().findAll();
        this.moves = FXCollections.observableArrayList(moves);
        tableView.setItems(this.moves);
    }
    public void deleteOldMove(Move oldMove){
        this.moves.remove(oldMove);
    }
    public void saveMove(Move newMove) {
        MoveDAO.build().save(newMove);
        this.moves.add(newMove);

    }
    public void deleteMove(Move deleteMove) throws SQLException {
        MoveDAO.build().delete(deleteMove);
        this.moves.remove(deleteMove);
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/ModalImageUtils/FondoMain.png");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imageUrl.toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        vbox.setBackground(new Background(backgroundImage));
        columnName.setCellValueFactory(move -> new SimpleStringProperty(move.getValue().getNameMove()));
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnName.setOnEditCommit(event ->

        {
            if (event.getNewValue() == event.getOldValue()) {
                return;
            }

            if (event.getNewValue().length() <= 60) {
                Move move = event.getRowValue();
                move.setNameMove(event.getNewValue());
                MoveDAO.build().save(move);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("¡Te has pasao!!!!");
                alert.show();
            }
        });
        columnType.setCellValueFactory(move -> {
            Types Type = move.getValue().getTypeMove();
            Image firstTypeImage = new Image(getClass().getResourceAsStream("/com/github/dangelcrack/media/TypesImage/" + Type + ".png"));
            return new SimpleObjectProperty<>(firstTypeImage);
        });
        columnType.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            {
                imageView.setFitWidth(75);
                imageView.setPreserveRatio(true);
            }

            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item);
                    setGraphic(imageView);
                }
            }
        });
        columnCategory.setCellValueFactory(move -> {
            Category category = move.getValue().getCategory();
            String imagePath = "/com/github/dangelcrack/media/ModalImageUtils/" + category.toString() + ".png";
            Image categoryImage = new Image(getClass().getResourceAsStream(imagePath));
            return new SimpleObjectProperty<>(categoryImage);
        });

        columnCategory.setCellFactory(column -> new TableCell<>() {
            private final HBox hbox = new HBox(10);
            private final ImageView imageView = new ImageView();
            private final Label label = new Label();

            {
                imageView.setFitWidth(25);
                imageView.setPreserveRatio(true);
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.getChildren().addAll(imageView, label);
            }
            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item);
                    Move move = getTableView().getItems().get(getIndex());
                    label.setText(move.getCategory().toString());
                    setGraphic(hbox);
                }
            }
        });

        columnPower.setCellValueFactory(new PropertyValueFactory<>("power"));
    }
    @FXML
    private void addMove() throws IOException {
        App.currentController.openModal(Scenes.ADDMOVE, "Agregando un Movimiento...", this, null);
    }
    @FXML
    private void deleteMove() throws IOException {
        App.currentController.openModal(Scenes.DELETEMOVE, "Borrando un Movimiento...", this, null);
    }
    @FXML
    private void editMove() throws IOException {
        App.currentController.openModal(Scenes.EDITMOVE, "Editando un Movimiento...", this, null);
    }
}
