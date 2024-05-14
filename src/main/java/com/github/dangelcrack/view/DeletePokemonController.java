package com.github.dangelcrack.view;

import com.github.dangelcrack.model.dao.PokemonDAO;
import com.github.dangelcrack.model.entity.Pokemon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class DeletePokemonController extends Controller implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private ImageView imageView;
    @FXML
    private ComboBox<Pokemon> pokemonComboBox;
    private PokemonController controller;

    @Override
    public void onOpen(Object input) {
        this.controller = (PokemonController) input;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/ModalImageUtils/img.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imageUrl.toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        vbox.setBackground(new Background(backgroundImage));
        List<Pokemon> pokemons;
        pokemons = PokemonDAO.build().findAll();
        ObservableList<Pokemon> observableNames = FXCollections.observableArrayList(pokemons);
        pokemonComboBox.setItems(observableNames);
        pokemonComboBox.setOnAction(event -> {
            Pokemon selectedPokemon = pokemonComboBox.getValue();
            initializeWithPokemon(selectedPokemon);
        });
    }

    private void initializeWithPokemon(Pokemon pokemon) {
        InputStream inputStream = getClass().getResourceAsStream("/com/github/dangelcrack/media/PokemonImages/" + pokemon.getPhotoPokemon());
        Image image = new Image(inputStream);
        imageView.setImage(image);
    }

    @FXML
    private void closeWindow(Event event) {
        String pokemonName = pokemonComboBox.getValue().getPokemonName();
        if (pokemonName != null) {
            Pokemon pokemon = PokemonDAO.build().findByName(pokemonName);
            if (pokemon != null) {
                this.controller.deletePokemon(pokemon);
            }
        }
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @Override
    public void onClose(Object output) {

    }
}
