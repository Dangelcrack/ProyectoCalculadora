package com.github.dangelcrack.view;

import com.github.dangelcrack.App;
import com.github.dangelcrack.model.dao.PokemonDAO;
import com.github.dangelcrack.model.entity.Pokemon;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {
    @FXML
    private TableView<Pokemon> tableView;

    @FXML
    private TableColumn<Pokemon,String> columnFirstType;
    @FXML
    private TableColumn<Pokemon,String> columnName;

    private ObservableList<Pokemon> pokemons;

    @Override
    public void onOpen(Object input) {
        List<Pokemon> pokemons = PokemonDAO.build().findAll();
        this.pokemons = FXCollections.observableArrayList(pokemons);
        tableView.setItems(this.pokemons);
    }

    @Override
    public void onClose(Object output) {

    }

    public void savePokemon(Pokemon newPokemon){
        PokemonDAO.build().save(newPokemon);
        this.pokemons.add(newPokemon);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setEditable(true);
        columnFirstType.setCellValueFactory(pokemon-> new SimpleStringProperty(pokemon.getValue().getFirstType()));
        columnName.setCellValueFactory(pokemon-> new SimpleStringProperty(pokemon.getValue().getPokemonName()));
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnName.setOnEditCommit(event -> {
            if(event.getNewValue()== event.getOldValue()){
                return;
            }

            if(event.getNewValue().length()<=60){
                Pokemon pokemon = event.getRowValue();
                pokemon.setPokemonName(event.getNewValue());
                PokemonDAO.build().save(pokemon);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Te has pasao!!!!!");
                alert.show();
            }
            //Actualizar los datos



        });

    }

    @FXML
    private void agregaPokemon() throws IOException {
        App.currentController.openModal(Scenes.FORMPOKEMON,"Agregando un Pokemon...",this,null);
    }
}
