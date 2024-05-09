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

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class DeletePokemonController extends Controller implements Initializable {
    @FXML
    private ComboBox<String> pokemonComboBox;
    private MainController controller;

    @Override
    public void onOpen(Object input) {
        this.controller = (MainController) input;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Pokemon> pokemons = PokemonDAO.build().findAll();
        ArrayList<String> pokemonNames = new ArrayList<>();
        for (Pokemon pokemon : pokemons) {
            pokemonNames.add(pokemon.getPokemonName());
        }
        ObservableList<String> observableNames = FXCollections.observableArrayList(pokemonNames);
        pokemonComboBox.setItems(observableNames);
    }

    @FXML
    private void closeWindow(Event event) throws SQLException {
        String pokemonName = pokemonComboBox.getSelectionModel().getSelectedItem();
        if (pokemonName != null) {
            Pokemon pokemon = PokemonDAO.build().findByName(pokemonName);
            if (pokemon != null) {
                this.controller.deletePokemon(pokemon);
            }
        }
        //Antes de cerrar notificamos a nuestro parent que hay un pokemon nuevo
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @Override
    public void onClose(Object output) {

    }
}
