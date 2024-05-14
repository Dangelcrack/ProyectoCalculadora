package com.github.dangelcrack.view;

import com.github.dangelcrack.App;
import com.github.dangelcrack.model.dao.PokemonDAO;
import com.github.dangelcrack.model.entity.Pokemon;
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
import java.sql.SQLException;
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
    public ObservableList<Pokemon> pokemons;

    @Override
    public void onOpen(Object input) {
        List<Pokemon> pokemons = PokemonDAO.build().findAll();
        this.pokemons = FXCollections.observableArrayList(pokemons);
        tableView.setItems(this.pokemons);
    }

    @Override
    public void onClose(Object output) {

    }
    public void deleteOldPokemon(Pokemon oldPokemon){
        this.pokemons.remove(oldPokemon);
    }
    public void savePokemon(Pokemon newPokemon) {
        PokemonDAO.build().save(newPokemon);
        this.pokemons.add(newPokemon);

    }
    public void deletePokemon(Pokemon deletePokemon) {
        PokemonDAO.build().delete(deletePokemon);
        this.pokemons.remove(deletePokemon);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.refresh();
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

            if (event.getNewValue().length() <= 60) {
                Pokemon pokemon = event.getRowValue();
                pokemon.setPokemonName(event.getNewValue());
                PokemonDAO.build().save(pokemon);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("¡Te has pasao!!!!");
                alert.show();
            }
        });
    }

    @FXML
    private void agregaPokemon() throws IOException {
        App.currentController.openModal(Scenes.ADDPOKEMON, "Agregando un Pokemon...", this, null);
        //deberíamos saber si hemos agregado un pokemon nuevo o no para acatualizar la lista
    }
    @FXML
    private void borrarPokemon() throws IOException{
        App.currentController.openModal(Scenes.DELETEPOKEMON,"Borrando un Pokemon...",this,null);
    }
    @FXML
    private void editarPokemon() throws IOException{
        App.currentController.openModal(Scenes.EDITPOKEMON,"Editando un Pokemon...", this,null);
    }
}
