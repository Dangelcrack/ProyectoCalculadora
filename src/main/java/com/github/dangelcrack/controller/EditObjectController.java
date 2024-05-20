package com.github.dangelcrack.controller;
import com.github.dangelcrack.model.dao.ObjDAO;
import com.github.dangelcrack.model.dao.PokemonDAO;
import com.github.dangelcrack.model.entity.Obj;
import com.github.dangelcrack.model.entity.Pokemon;
import com.github.dangelcrack.model.entity.Category;
import com.github.dangelcrack.model.entity.Types;
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

public class EditObjectController extends Controller implements Initializable{
    @FXML
    private VBox vbox;
    @FXML
    private ComboBox<Obj> objetoComboBox;
    private ObjectsController controller;
    @FXML
    private TableColumn<Pokemon, Image> imageViewTableColumn;
    @FXML
    private TextField photo;
    @FXML
    private ImageView imageView;
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
    private ComboBox<Pokemon> pokemonCanHold;
    @FXML
    private Button deletePokemon;
    @FXML
    private TableColumn<Pokemon, String> columnNamePokemon;
    @FXML
    private TableColumn<Pokemon, Image> columnFirstType;
    @FXML
    private TableColumn<Pokemon, Image> columnSecondType;
    private ObservableList<Pokemon> pokemonList = FXCollections.observableArrayList();
    @Override
    public void onOpen(Object input) throws IOException {
        List<Pokemon> pokemons = new ArrayList<>();
        pokemonList = FXCollections.observableArrayList(pokemons);
        tableView.setItems(pokemonList);
        this.controller = (ObjectsController) input;
        tableView.refresh();
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/ModalImageUtils/imgEditPokemon.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imageUrl.toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, false, true)
        );
        vbox.setBackground(new Background(backgroundImage));
        List<Obj> objs = ObjDAO.build().findAll();
        ObservableList<Obj> observableNames = FXCollections.observableArrayList(objs);
        objetoComboBox.setItems(observableNames);
        objetoComboBox.setCellFactory(lv -> new ListCell<Obj>() {
            @Override
            protected void updateItem(Obj item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getNameObject());
            }
        });
        objetoComboBox.setButtonCell(new ListCell<Obj>() {
            @Override
            protected void updateItem(Obj item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getNameObject());
            }
        });
        objetoComboBox.setOnAction(event -> {
            Obj selectedObj = objetoComboBox.getValue();
            List<Pokemon> pokemonList = PokemonDAO.build().findPokemonByObj(selectedObj);
            tableView.getItems().clear();
            tableView.getItems().addAll(pokemonList);
            initializeWithObj(selectedObj);
        });
    }
    private void initializeWithObj(Obj obj) {
        photo.setText(obj.getPhotoObj());
        InputStream inputStream = getClass().getResourceAsStream("/com/github/dangelcrack/media/ObjImages/" + obj.getPhotoObj());
        Image image = new Image(inputStream);
        imageView.setImage(image);
        description.setText(obj.getDescription());
        attackBoost.setValue(obj.getAttackBoost());
        attackValueBoost.setText(String.valueOf((int)attackBoost.getValue()));
        defenseBoost.setValue(obj.getDefenseBoost());
        defenseValueBoost.setText(String.valueOf((int)defenseBoost.getValue()));
        spAttackBoost.setValue(obj.getSpAttackBoost());
        spAttackValueBoost.setText(String.valueOf((int)spAttackBoost.getValue()));
        spDefenseBoost.setValue(obj.getSpDefenseBoost());
        spDefenseValueBoost.setText(String.valueOf((int)spDefenseBoost.getValue()));
        speedBoost.setValue(obj.getSpeedBoost());
        speedValueBoost.setText(String.valueOf((int)speedBoost.getValue()));
        attackBoost.valueProperty().addListener((observable, oldValue, newValue) -> attackValueBoost.setText(String.valueOf(newValue.intValue())));
        defenseBoost.valueProperty().addListener((observable, oldValue, newValue) -> defenseValueBoost.setText(String.valueOf(newValue.intValue())));
        spAttackBoost.valueProperty().addListener((observable, oldValue, newValue) -> spAttackValueBoost.setText(String.valueOf(newValue.intValue())));
        spDefenseBoost.valueProperty().addListener((observable, oldValue, newValue) -> spDefenseValueBoost.setText(String.valueOf(newValue.intValue())));
        speedBoost.valueProperty().addListener((observable, oldValue, newValue) -> speedValueBoost.setText(String.valueOf(newValue.intValue())));
        typeBoost.setValue(obj.getBoostType());
        categoryBoost.setValue(obj.getBoostCategory());
        typeBoost.setItems(FXCollections.observableArrayList(Types.values()));
        categoryBoost.setItems(FXCollections.observableArrayList(Category.values()));
        imageViewTableColumn.setCellValueFactory(pokemon -> {
            String imageExtension = pokemon.getValue().getPhotoPokemon();
            String imagePath = "/com/github/dangelcrack/media/PokemonImages/" + imageExtension;
            InputStream inputStreamPokemon = getClass().getResourceAsStream(imagePath);
            SimpleObjectProperty<Image> property = null;
            if (inputStreamPokemon != null) {
                Image Image = new Image(inputStreamPokemon);
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
        columnNamePokemon.setCellValueFactory(pokemon -> new SimpleObjectProperty<>(pokemon.getValue().getPokemonName()));
        columnFirstType.setCellValueFactory(pokemon -> {
            Types firstType = pokemon.getValue().getPokemonFirstType();
            String imagePath = "/com/github/dangelcrack/media/TypesImage/" + firstType + ".png";
            InputStream inputStreamFirstType = getClass().getResourceAsStream(imagePath);
            SimpleObjectProperty<Image> property = null;
            if (inputStreamFirstType != null) {
                Image firstTypeImage = new Image(inputStreamFirstType);
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
            InputStream inputStreamSecondType = getClass().getResourceAsStream(imagePath);
            SimpleObjectProperty<Image> property = null;
            if (inputStreamSecondType != null) {
                Image firstTypeImage = new Image(inputStreamSecondType);
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
        pokemonCanHold.setItems(FXCollections.observableArrayList(PokemonDAO.build().findAll()));
        deletePokemon.setOnAction(event -> {
            Pokemon selectedPokemon = pokemonCanHold.getValue();
            if (selectedPokemon != null && pokemonList.contains(selectedPokemon)) {
                pokemonList.remove(selectedPokemon);
                ObservableList<Pokemon> observablePokemonList = FXCollections.observableArrayList(pokemonList);
                tableView.setItems(null);
                tableView.setItems(observablePokemonList);
            }
            tableView.refresh();
        });
        addPokemon.setOnAction(event -> {
            Pokemon selectedPokemon = pokemonCanHold.getValue();
            if (selectedPokemon != null && !pokemonList.contains(selectedPokemon)) {
                pokemonList.add(selectedPokemon);
                columnNamePokemon.setCellValueFactory(pokemon -> new SimpleStringProperty(pokemon.getValue().getPokemonName()));
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
                    Types secondType = pokemon.getValue().getPokemonSecondType();
                    if (secondType == null) {
                        return new SimpleObjectProperty<>();
                    } else {
                        Image secondTypeImage = new Image(getClass().getResourceAsStream("/com/github/dangelcrack/media/TypesImage/" + secondType + ".png"));
                        return new SimpleObjectProperty<>(secondTypeImage);
                    }
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
        Obj obj = new Obj(objetoComboBox.getValue().getNameObject(), description.getText(), photo.getText(), typeValueBoost, categoryValueBoost,
                attackValueBoost, defenseValueBoost, spattackValueBoost, spdefenseValueBoost, speedValueBoost,
                selectedPokemonList);
        if (!photo.getText().isEmpty()) {
            obj.setPhotoObj(photo.getText());
        }
        this.controller.deleteOldObj(objetoComboBox.getValue());
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
            String mediaPath = "src/main/resources/com/github/dangelcrack/media/PokemonImages/";
            File destinationFolder = new File(mediaPath);
            File destinationFile = new File(destinationFolder.getAbsolutePath() + "/" + photoPath);//y aquí luego lo que hacia era un get.name a photopath
            try {
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image image = new Image(destinationFile.toURI().toString());
            imageView.setImage(image);
        }
    }

}
