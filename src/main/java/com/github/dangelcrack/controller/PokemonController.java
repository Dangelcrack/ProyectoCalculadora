package com.github.dangelcrack.controller;

import com.github.dangelcrack.App;
import com.github.dangelcrack.model.dao.PokemonDAO;
import com.github.dangelcrack.model.entity.Obj;
import com.github.dangelcrack.model.entity.Pokemon;
import com.github.dangelcrack.model.entity.Scenes;
import com.github.dangelcrack.model.entity.Types;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PokemonController extends Controller implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private TableView<Pokemon> tableView;
    @FXML
    private TableColumn<Pokemon, Image> imageViewTableColumn;
    @FXML
    private TableColumn<Pokemon, Image> columnFirstType;
    @FXML
    private TableColumn<Pokemon, Image> columnSecondType;
    @FXML
    private TableColumn<Pokemon, String> columnName;
    @FXML
    private TableColumn<Pokemon, String> columnMoves;
    @FXML
    private TableColumn<Pokemon , String> pokemonHolds;
    public ObservableList<Pokemon> pokemons;
    /**
     * Called when the view is opened.
     * @param input The input provided when the view is opened.
     */
    @Override
    public void onOpen(Object input) {
        List<Pokemon> pokemons = PokemonDAO.build().findAll();
        this.pokemons = FXCollections.observableArrayList(pokemons);
        tableView.setItems(this.pokemons);
    }

    /**
     * Called when the view is closed.
     * @param output The output provided when the view is closed.
     */
    @Override
    public void onClose(Object output) {
    }

    /**
     * Deletes an old Pokemon from the observable list.
     * @param oldPokemon The Pokemon to be removed from the list.
     */
    public void deleteOldPokemon(Pokemon oldPokemon){
        this.pokemons.remove(oldPokemon);
    }

    /**
     * Saves a new Pokemon both to the database and the observable list.
     * @param newPokemon The new Pokemon to be added.
     */
    public void savePokemon(Pokemon newPokemon) {
        PokemonDAO.build().save(newPokemon);
        this.pokemons.add(newPokemon);
    }

    /**
     * Deletes a Pokemon from both the database and the observable list.
     * @param deletePokemon The Pokemon to be deleted.
     */
    public void deletePokemon(Pokemon deletePokemon) {
        PokemonDAO.build().delete(deletePokemon);
        this.pokemons.remove(deletePokemon);
    }
    /**
     * Initializes the controller when the corresponding view is loaded.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root pokemon, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.refresh();
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
        imageViewTableColumn.setCellValueFactory(pokemon -> {
            String imageExtension = pokemon.getValue().getPhotoPokemon();
            String imagePath = "/com/github/dangelcrack/media/PokemonImages/" + imageExtension;
            InputStream inputStream = getClass().getResourceAsStream(imagePath);
            Image image = new Image(inputStream);
            return new SimpleObjectProperty<>(image);
        });
        imageViewTableColumn.setCellFactory(column -> new TableCell<>() {
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
        columnMoves.setCellValueFactory(pokemon -> new SimpleStringProperty(pokemon.getValue().getMovesNamesString(pokemon.getValue())));
        pokemonHolds.setCellValueFactory(pokemon -> {
            Obj obj = pokemon.getValue().getObj();
            return new SimpleStringProperty(obj != null ? obj.getNameObject() : "");
        });
        columnFirstType.setCellValueFactory(pokemon -> {
            Types firstType = pokemon.getValue().getPokemonFirstType();
            Image firstTypeImage = new Image(getClass().getResourceAsStream("/com/github/dangelcrack/media/TypesImage/" + firstType + ".png"));
            return new SimpleObjectProperty<>(firstTypeImage);
        });
        columnFirstType.setCellFactory(column -> new TableCell<>() {
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
        columnSecondType.setCellValueFactory(pokemon -> {
            SimpleObjectProperty result;
            Types secondType = pokemon.getValue().getPokemonSecondType();
            if (secondType == null) {
                result = new SimpleObjectProperty<>();
            } else {
                Image secondTypeImage = new Image(getClass().getResourceAsStream("/com/github/dangelcrack/media/TypesImage/" + secondType + ".png"));
                result = new SimpleObjectProperty<>(secondTypeImage);
            }
            return result;
        });
        columnSecondType.setCellFactory(column -> new TableCell<>() {
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
        columnName.setCellValueFactory(pokemon -> new SimpleStringProperty(pokemon.getValue().getPokemonName().toString()));
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnName.setOnEditCommit(event ->

        {
            if (event.getNewValue() == event.getOldValue()) {
                return;
            }

            if (event.getNewValue().length() <= 20) {
                Pokemon pokemon = event.getRowValue();
                PokemonDAO.build().delete(pokemon);
                pokemon.setPokemonName(event.getNewValue());
                PokemonDAO.build().save(pokemon);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("¡Te has pasao!!!!");
                alert.show();
            }
        });
    }
    /**
     * Handles the event when the user wants to add a Pokemon.
     *
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void addPokemon() throws IOException {
        App.currentController.openModal(Scenes.ADDPOKEMON, "Adding a Pokemon...", this, null);
    }
    /**
     * Handles the event when the user wants to delete a Pokemon.
     *
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void deletePokemon() throws IOException {
        App.currentController.openModal(Scenes.DELETEPOKEMON, "Deleting a Pokemon...", this, null);
    }
    /**
     * Handles the event when the user wants to edit a Pokemon.
     *
     * @throws IOException If an I/O error occurs.
     */
    @FXML
    private void editPokemon() throws IOException {
        App.currentController.openModal(Scenes.EDITPOKEMON, "Editing a Pokemon...", this, null);
    }
}
