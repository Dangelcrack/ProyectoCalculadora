package com.github.dangelcrack.view;

import com.github.dangelcrack.model.dao.PokemonDAO;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Pokemon;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
/**
 * The AddMoveController class is responsible for handling the UI logic for adding moves in the application.
 * It implements the Initializable interface to initialize the controller after its root element has been completely processed.
 */
public class AddMoveController extends Controller implements Initializable {
    @FXML
    private HBox hBox;
    @FXML
    private TableColumn<Pokemon, Image> imageViewTableColumn;
    @FXML
    private TextField name;
    @FXML
    private ComboBox<Types> type;
    @FXML
    private ComboBox<Category> category;
    @FXML
    private ComboBox<Pokemon> pokemonCanLearn;
    @FXML
    private Button addPokemon;
    @FXML
    private Button deletePokemon;
    @FXML
    private Slider power;
    @FXML
    public Label powerValue;
    @FXML
    private TableView<Pokemon> tableView;
    @FXML
    private TableColumn<Pokemon, String> columnName;
    @FXML
    private TableColumn<Pokemon, Image> columnFirstType;
    @FXML
    private TableColumn<Pokemon, Image> columnSecondType;
    private ObservableList<Pokemon> pokemonList = FXCollections.observableArrayList();
    private MovesController controller;
    /**
     * This method is called when the controller is opened. It initializes the list of Pokemon and sets the controller reference.
     */
    @Override
    public void onOpen(Object input) {
        List<Pokemon> pokemons = new ArrayList<>();
        pokemonList = FXCollections.observableArrayList(pokemons);
        tableView.setItems(pokemonList);
        this.controller = (MovesController) input;

    }

    /**
     * This method is called when the controller is closed. Currently, it has no implementation.
     */
    @Override
    public void onClose(Object output) {

    }
    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/ModalImageUtils/imgAddPokemon.png");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imageUrl.toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, false, true)
        );
        hBox.setBackground(new Background(backgroundImage));
        imageViewTableColumn.setCellValueFactory(pokemon -> {
            String imageExtension = pokemon.getValue().getPhotoPokemon();
            String imagePath = "/com/github/dangelcrack/media/PokemonImages/" + imageExtension;
            InputStream inputStream = getClass().getResourceAsStream(imagePath);
            SimpleObjectProperty<Image> property = null;
            if (inputStream != null) {
                Image Image = new Image(inputStream);
                property = new SimpleObjectProperty<>(Image);

            }
            return property;
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
        type.setItems(FXCollections.observableArrayList(Types.values()));
        category.setItems(FXCollections.observableArrayList(Category.values()));
        power.valueProperty().addListener((observable, oldValue, newValue) -> powerValue.setText(String.valueOf(newValue.intValue())));
        pokemonCanLearn.setItems(FXCollections.observableArrayList(PokemonDAO.build().findAll()));
        deletePokemon.setOnAction(event -> {
            Pokemon selectedPokemon = pokemonCanLearn.getValue();
            if (selectedPokemon != null && pokemonList.contains(selectedPokemon)) {
                pokemonList.remove(selectedPokemon);
                if (tableView != null) {
                    tableView.setItems(null);
                    tableView.setItems(pokemonList);
                    tableView.refresh();
                }
            }
        });
        addPokemon.setOnAction(event -> {
            Pokemon selectedPokemon = pokemonCanLearn.getValue();
            if (selectedPokemon != null && (!pokemonList.contains(selectedPokemon))) {
                pokemonList.add(selectedPokemon);
                columnName.setCellValueFactory(pokemonname -> new SimpleStringProperty(pokemonname.getValue().getPokemonName()));
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
            }
                tableView.refresh();
        });
    }
    /**
     * Handles the closing of the window. It saves the move details and hides the window.
     */
    @FXML
    private void closeWindow(Event event) {
        String moveName = name.getText();
        int powerValue = (int) power.getValue();
        Types moveType = type.getValue();
        Category moveCategory = category.getValue();
        List<Pokemon> selectedPokemonList = new ArrayList<>(pokemonList);
        Move move = new Move(moveName, moveType, moveCategory, powerValue, selectedPokemonList);

        if(Objects.equals(move.getNameMove(), name.getText())){
            this.controller.deleteMove(move);
        }
        this.controller.saveMove(move);
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
