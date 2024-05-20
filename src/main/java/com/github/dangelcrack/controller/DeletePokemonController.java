package com.github.dangelcrack.controller;
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
    /**
     * This method is called when the view is opened with the given input.
     * @param input The PokemonController instance passed as input.
     */
    @Override
    public void onOpen(Object input) {
        this.controller = (PokemonController) input;
    }

    /**
     * Initializes the controller class, sets up the background image, and populates the Pokemon combo box.
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
        List<Pokemon> pokemons = PokemonDAO.build().findAll();
        ObservableList<Pokemon> observableNames = FXCollections.observableArrayList(pokemons);
        pokemonComboBox.setItems(observableNames);
        pokemonComboBox.setOnAction(event -> {
            Pokemon selectedPokemon = pokemonComboBox.getValue();
            initializeWithPokemon(selectedPokemon);
        });
    }

    /**
     * Initializes the view with the selected Pokemon's image.
     * @param pokemon The Pokemon instance containing the Pokemon's data.
     */
    private void initializeWithPokemon(Pokemon pokemon) {
        InputStream inputStream = getClass().getResourceAsStream("/com/github/dangelcrack/media/PokemonImages/" + pokemon.getPhotoPokemon());
        Image image = new Image(inputStream);
        imageView.setImage(image);
    }

    /**
     * Handles the close window event, deletes the selected Pokemon, and hides the window.
     * @param event The event that triggered the method call.
     */
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

    /**
     * This method is called when the view is closed.
     * @param output The output data to be passed, not used in this implementation.
     */
    @Override
    public void onClose(Object output) {
    }
}
