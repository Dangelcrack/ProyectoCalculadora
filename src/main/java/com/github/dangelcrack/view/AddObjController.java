package com.github.dangelcrack.view;

import com.github.dangelcrack.model.dao.PokemonDAO;
import com.github.dangelcrack.model.entity.Obj;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddObjController extends Controller implements Initializable {
    @FXML
    private TableColumn<Pokemon, Image> imageViewTableColumn;
    @FXML
    private TextField name;
    @FXML
    private TextField photo;
    @FXML
    private ImageView imageView;
    @FXML
    private VBox vbox;
    @FXML
    private ComboBox<Types> typeBoost;
    @FXML
    private ComboBox<Category> categoryBoost;
    @FXML
    private TextArea description;
    @FXML
    private Slider attackBoost;
    @FXML
    public Label attackValueBoost;

    @FXML
    private Slider defenseBoost;
    @FXML
    public Label defenseValueBoost;

    @FXML
    private Slider spAttackBoost;
    @FXML
    public Label spAttackValueBoost;

    @FXML
    private Slider spDefenseBoost;
    @FXML
    public Label spDefenseValueBoost;

    @FXML
    private Slider speedBoost;
    @FXML
    public Label speedValueBoost;
    @FXML
    private Button addPokemon;
    @FXML
    private TableView<Pokemon> tableView;

    @FXML
    private TableColumn<Pokemon, Image> columnFirstType;
    @FXML
    private TableColumn<Pokemon, Image> columnSecondType;
    @FXML
    private ComboBox<Pokemon> pokemonCanHold;
    @FXML
    private Button deletePokemon;
    @FXML
    private TableColumn<Pokemon, String> columnNamePokemon;
    private ObservableList<Pokemon> pokemonList = FXCollections.observableArrayList();
    private ObjectsController controller;

    @Override
    public void onOpen(Object input) throws IOException {
        List<Pokemon> pokemons = new ArrayList<>();
        pokemonList = FXCollections.observableArrayList(pokemons);
        tableView.setItems(pokemonList);
        this.controller = (ObjectsController) input;
    }

    @Override
    public void onClose(Object output) {

    }

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
        vbox.setBackground(new Background(backgroundImage));
        typeBoost.setItems(FXCollections.observableArrayList(Types.values()));
        categoryBoost.setItems(FXCollections.observableArrayList(Category.values()));
        description.getParagraphs();
        attackBoost.valueProperty().addListener((observable, oldValue, newValue) -> attackValueBoost.setText(String.valueOf(newValue.intValue())));
        defenseBoost.valueProperty().addListener((observable, oldValue, newValue) -> defenseValueBoost.setText(String.valueOf(newValue.intValue())));
        spAttackBoost.valueProperty().addListener((observable, oldValue, newValue) -> spAttackValueBoost.setText(String.valueOf(newValue.intValue())));
        spDefenseBoost.valueProperty().addListener((observable, oldValue, newValue) -> spDefenseValueBoost.setText(String.valueOf(newValue.intValue())));
        speedBoost.valueProperty().addListener((observable, oldValue, newValue) -> speedValueBoost.setText(String.valueOf(newValue.intValue())));
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
        pokemonCanHold.setItems(FXCollections.observableArrayList(PokemonDAO.build().findAll()));
        deletePokemon.setOnAction(event -> {
            Pokemon selectedPokemon = pokemonCanHold.getValue();
            if (selectedPokemon != null) {
                pokemonList.remove(selectedPokemon);
            }
        });
        addPokemon.setOnAction(event -> {
            Pokemon selectedPokemon = pokemonCanHold.getValue();
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
                ObservableList<Pokemon> observablePokemonList = FXCollections.observableArrayList(pokemonList);
                tableView.setItems(observablePokemonList);
            }
            tableView.refresh();
        });
    }

    @FXML
    private void closeWindow(Event event) {
        int attackValueBoost = (int) attackBoost.getValue();
        int defenseValueBoost = (int) defenseBoost.getValue();
        int spattackValueBoost = (int) spAttackBoost.getValue();
        int spdefenseValueBoost = (int) spDefenseBoost.getValue();
        int speedValueBoost = (int) speedBoost.getValue();
        Types typeValueBoost = typeBoost.getValue();
        Category categoryValueBoost = categoryBoost.getValue();
        List<Pokemon> selectedPokemonList = new ArrayList<>(pokemonList);
        Obj obj = new Obj(name.getText(), description.getText(), photo.getText(), typeValueBoost, categoryValueBoost,
                attackValueBoost, defenseValueBoost, spattackValueBoost, spdefenseValueBoost, speedValueBoost,
                selectedPokemonList);
        this.controller.saveObj(obj);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void selectPhoto() {
        Stage stage = (Stage) photo.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Photo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String photoPath = selectedFile.getName();//aqui me cogia el absolutePath
            photo.setText(photoPath);
            String mediaPath = "src/main/resources/com/github/dangelcrack/media/ObjImages/";
            File destinationFolder = new File(mediaPath);
            File destinationFile = new File(destinationFolder.getAbsolutePath() + "/" + photoPath);//y aquí luego lo que hacia era un get.name a photopath
            try {
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image image = new Image(destinationFile.toURI().toString());
            imageView.setImage(image);//esto es para que en la pantalla de añadir pokemon al seleccionar la imagen se muestre abajo del boton de agregar
        }
    }
}
