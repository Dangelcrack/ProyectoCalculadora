package com.github.dangelcrack.controller;

import com.github.dangelcrack.App;
import com.github.dangelcrack.model.dao.MoveDAO;
import com.github.dangelcrack.model.dao.PokemonDAO;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Pokemon;
import com.github.dangelcrack.model.entity.Category;
import com.github.dangelcrack.model.entity.Scenes;
import com.github.dangelcrack.model.entity.Types;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
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
    private TableColumn<Move, Integer> columnPower;
    @FXML
    private TableView<Pokemon> tableView2;
    @FXML
    private TableColumn<Pokemon, Image> imageViewTableColumn;
    @FXML
    private TableColumn<Pokemon, Image> columnTypesPokemon;
    @FXML
    private TableColumn<Pokemon, Image> columnTypesPokemon2;
    @FXML
    private TableColumn<Pokemon, String> pokemonNames;
    public ObservableList<Move> moves;

    /**
     * Called when the controller is opened.
     * Retrieves all moves from the database, updates the observable list, and refreshes the table view.
     *
     * @param input The input object, not used in this method.
     */
    @Override
    public void onOpen(Object input) {
        List<Move> moves = MoveDAO.build().findAll();
        this.moves = FXCollections.observableArrayList(moves);
        tableView.refresh();
        tableView.setItems(this.moves);
    }

    /**
     * Deletes the specified old move from the observable list.
     *
     * @param oldMove The move to be deleted.
     */
    public void deleteOldMove(Move oldMove) {
        this.moves.remove(oldMove);
    }

    /**
     * Saves the new move to the database and adds it to the observable list.
     *
     * @param newMove The move to be saved.
     */
    public void saveMove(Move newMove) {
        MoveDAO.build().save(newMove);
        this.moves.add(newMove);
    }

    /**
     * Deletes the specified move from the database and removes it from the observable list.
     *
     * @param deleteMove The move to be deleted.
     */
    public void deleteMove(Move deleteMove) {
        MoveDAO.build().delete(deleteMove);
        this.moves.remove(deleteMove);
    }

    /**
     * Called when the controller is closed.
     *
     * @param output The output object, not used in this method.
     */
    @Override
    public void onClose(Object output) {
        // No action needed on close
    }

    /**
     * Initializes the TableView and its columns with appropriate cell factories.
     * Also sets listeners for TableView selection changes.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/ModalImageUtils/fondonegro.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imageUrl.toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, false, true)
        );
        vbox.setBackground(new Background(backgroundImage));
        columnName.setCellValueFactory(move -> new SimpleStringProperty(move.getValue().getNameMove()));
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnName.setOnEditCommit(event -> {
            if (event.getNewValue().equals(event.getOldValue())) {
                return;
            }
            if (event.getNewValue().length() <= 20) {
                Move move = event.getRowValue();
                MoveDAO.build().delete(move);
                move.setNameMove(event.getNewValue());
                MoveDAO.build().save(move);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("¡Te has pasado del límite de caracteres!");
                alert.show();
            }
        });
        columnType.setCellValueFactory(move -> {
            Types type = move.getValue().getTypeMove();
            String imagePath = "/com/github/dangelcrack/media/TypesImage/" + type + ".png";
            InputStream inputStream = getClass().getResourceAsStream(imagePath);
            SimpleObjectProperty<Image> property = null;
            if (inputStream != null) {
                Image Image = new Image(inputStream);
                property = new SimpleObjectProperty<>(Image);

            }
            return property;
        });
        columnType.setCellFactory(column -> new TableCell<Move, Image>() {
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
            InputStream inputStream = getClass().getResourceAsStream(imagePath);
            SimpleObjectProperty<Image> property = null;
            if (inputStream != null) {
                Image Image = new Image(inputStream);
                property = new SimpleObjectProperty<>(Image);

            }
            return property;
        });

        columnCategory.setCellFactory(column -> new TableCell<Move, Image>() {
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
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tableView2.getItems().clear();
                Move selectedMove = newValue;
                List<Pokemon> pokemonList = PokemonDAO.build().findPokemonByMoveName(selectedMove.getNameMove());
                tableView2.getItems().addAll(pokemonList);
                imageViewTableColumn.setCellValueFactory(pokemon -> {
                    String imageExtension = pokemon.getValue().getPhotoPokemon();
                    String imagePath = "/com/github/dangelcrack/media/PokemonImages/" + imageExtension;
                    InputStream inputStream = getClass().getResourceAsStream(imagePath);
                    SimpleObjectProperty<Image> property = null;
                    if (inputStream != null) {
                        Image firstTypeImage = new Image(inputStream);
                        property = new SimpleObjectProperty<>(firstTypeImage);

                    }
                    return property;
                });
                imageViewTableColumn.setCellFactory(column -> new TableCell<Pokemon, Image>() {
                    private final ImageView imageView = new ImageView();

                    {
                        imageView.setFitWidth(50);
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
                pokemonNames.setCellValueFactory(pokemon -> new SimpleObjectProperty<>(pokemon.getValue().getPokemonName()));
                columnTypesPokemon.setCellValueFactory(pokemon -> {
                    Types firstType = pokemon.getValue().getPokemonFirstType();
                    String imagePath = "/com/github/dangelcrack/media/TypesImage/" + firstType + ".png";
                    InputStream inputStream = getClass().getResourceAsStream(imagePath);
                    SimpleObjectProperty<Image> property = null;
                    if (inputStream != null) {
                        Image firstTypeImage = new Image(inputStream);
                        property = new SimpleObjectProperty<>(firstTypeImage);

                    }
                    return property;
                });
                columnTypesPokemon.setCellFactory(column -> new TableCell<>() {
                    private final ImageView imageView = new ImageView();
                    {
                        imageView.setFitWidth(100);
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
                columnTypesPokemon2.setCellValueFactory(pokemon -> {
                    Types secondType = pokemon.getValue().getPokemonSecondType();
                    String imagePath = "/com/github/dangelcrack/media/TypesImage/" + secondType + ".png";
                    InputStream inputStream = getClass().getResourceAsStream(imagePath);
                    SimpleObjectProperty<Image> property = null;
                    if (inputStream != null) {
                        Image firstTypeImage = new Image(inputStream);
                        property = new SimpleObjectProperty<>(firstTypeImage);

                    }
                    return property;
                });
                columnTypesPokemon2.setCellFactory(column -> new TableCell<>() {
                    private final ImageView imageView = new ImageView();
                    {
                        imageView.setFitWidth(100);
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
            }
        });
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
