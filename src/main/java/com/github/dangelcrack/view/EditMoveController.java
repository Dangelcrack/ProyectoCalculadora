package com.github.dangelcrack.view;

import com.github.dangelcrack.model.dao.MoveDAO;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditMoveController extends Controller implements Initializable{ @FXML
private HBox hBox;
    @FXML
    private TableView<Move> tableViewMoves;
    @FXML
    private TableColumn<Move, String> columnNameMove;
    @FXML
    private TableColumn<Move, Image> columnTypeMove;
    @FXML
    private TableColumn<Move, Image> columnCategoryMove;
    @FXML
    private TableColumn<Move, Integer> columnPowerMove;
    @FXML
    private TableColumn<Pokemon, Image> imageViewTableColumn;
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
    private TableView<Pokemon> tableViewPokemon;
    @FXML
    private TableColumn<Pokemon, String> columnNamePokemon;
    @FXML
    private TableColumn<Pokemon, Image> columnFirstType;
    @FXML
    private TableColumn<Pokemon, Image> columnSecondType;
    private ObservableList<Pokemon> pokemonList = FXCollections.observableArrayList();
    public ObservableList<Move> moves;
    private MovesController controller;

    @Override
    public void onOpen(Object input) {
        List<Pokemon> pokemons = new ArrayList<>();
        pokemonList = FXCollections.observableArrayList(pokemons);
        tableViewPokemon.setItems(pokemonList);
        this.controller = (MovesController) input;
        List<Move> moves = MoveDAO.build().findAll();
        this.moves = FXCollections.observableArrayList(moves);
        tableViewMoves.refresh();
        tableViewMoves.setItems(this.moves);
    }



    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/ModalImageUtils/pokestop.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imageUrl.toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                // Ajusta el tamaño de fondo para que se estire tanto en ancho como en alto
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
                tableViewPokemon.setItems(null);
                tableViewPokemon.setItems(pokemonList);
            }
            tableViewPokemon.refresh();
        });
        addPokemon.setOnAction(event -> {
            Pokemon selectedPokemon = pokemonCanLearn.getValue();
            if (selectedPokemon != null && (!pokemonList.contains(selectedPokemon))) {
                pokemonList.add(selectedPokemon);
                columnNamePokemon.setCellValueFactory(pokemonname -> new SimpleStringProperty(pokemonname.getValue().getPokemonName()));
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
            tableViewPokemon.refresh();
        });
        tableViewMoves.setEditable(true);

        columnNameMove.setCellValueFactory(move -> new SimpleStringProperty(move.getValue().getNameMove()));
        columnNameMove.setCellFactory(TextFieldTableCell.forTableColumn());
        columnTypeMove.setCellValueFactory(move -> {
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
        columnTypeMove.setCellFactory(column -> new TableCell<>() {
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
        columnCategoryMove.setCellValueFactory(move -> {
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

        columnCategoryMove.setCellFactory(column -> new TableCell<>() {
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

        columnPowerMove.setCellValueFactory(new PropertyValueFactory<>("power"));
        tableViewMoves.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                initializeWithMove(newValue);
                tableViewPokemon.getItems().clear();
                Move selectedMove = newValue;
                List<Pokemon> pokemonList = PokemonDAO.build().findPokemonByMoveName(selectedMove.getNameMove());
                tableViewPokemon.getItems().addAll(pokemonList);
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
                columnNamePokemon.setCellValueFactory(pokemon -> new SimpleObjectProperty<>(pokemon.getValue().getPokemonName()));
                columnFirstType.setCellValueFactory(pokemon -> {
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
        });
    }
    private void initializeWithMove(Move move) {
        power.setValue(move.getPower());
        type.setValue(move.getTypeMove());
        type.setItems(FXCollections.observableArrayList(Types.values()));
        category.setValue(move.getCategory());
        category.setItems(FXCollections.observableArrayList(Category.values()));
    }
    @FXML
    private void closeWindow(Event event) {
        int powerValue = (int) power.getValue();
        Types moveType = type.getValue();
        Category moveCategory = category.getValue();
        List<Pokemon> selectedPokemonList = new ArrayList<>(pokemonList);
        Move moveBeingEdited = tableViewMoves.getSelectionModel().getSelectedItem();

        if (moveBeingEdited != null) {
            moveBeingEdited.setPower(powerValue);
            moveBeingEdited.setTypeMove(moveType);
            moveBeingEdited.setCategory(moveCategory);
            moveBeingEdited.setPokemonCanLearn(selectedPokemonList);
            this.controller.deleteOldMove(tableViewMoves.getSelectionModel().getSelectedItem());
            this.controller.saveMove(moveBeingEdited);

            ((Node) event.getSource()).getScene().getWindow().hide();
        }
    }
}
