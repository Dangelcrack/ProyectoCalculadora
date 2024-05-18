package com.github.dangelcrack.view;

import com.github.dangelcrack.model.dao.MoveDAO;
import com.github.dangelcrack.model.dao.PokemonDAO;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Obj;
import com.github.dangelcrack.model.entity.Pokemon;
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
import java.util.*;

public class EditPokemonController extends Controller implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private ComboBox<Pokemon> pokemonComboBox;
    @FXML
    private ComboBox<Types> firstType;
    @FXML
    private ComboBox<Types> secondType;
    @FXML
    private ComboBox<Nature> nature;
    @FXML
    private TextField photo;
    @FXML
    private ImageView imageView;
    @FXML
    private Slider level;
    @FXML
    public Label levelValue;

    @FXML
    private Slider hp;
    @FXML
    public Label hpValue;

    @FXML
    private Slider attack;
    @FXML
    public Label attackValue;

    @FXML
    private Slider defense;
    @FXML
    public Label defenseValue;

    @FXML
    private Slider spattack;
    @FXML
    public Label spattackValue;

    @FXML
    private Slider spdefense;
    @FXML
    public Label spdefenseValue;

    @FXML
    private Slider speed;
    @FXML
    public Label speedValue;

    @FXML
    private Slider iv_hp;
    @FXML
    public Label iv_hpValue;

    @FXML
    private Slider iv_attack;
    @FXML
    public Label iv_attackValue;

    @FXML
    private Slider iv_defense;
    @FXML
    public Label iv_defenseValue;

    @FXML
    private Slider iv_spattack;
    @FXML
    public Label iv_spattackValue;

    @FXML
    private Slider iv_spdefense;
    @FXML
    public Label iv_spdefenseValue;

    @FXML
    private Slider iv_speed;
    @FXML
    public Label iv_speedValue;

    @FXML
    private Slider ev_hp;
    @FXML
    public Label ev_hpValue;

    @FXML
    private Slider ev_attack;
    @FXML
    public Label ev_attackValue;

    @FXML
    private Slider ev_defense;
    @FXML
    public Label ev_defenseValue;

    @FXML
    private Slider ev_spattack;
    @FXML
    public Label ev_spattackValue;

    @FXML
    private Slider ev_spdefense;
    @FXML
    public Label ev_spdefenseValue;

    @FXML
    private Slider ev_speed;
    @FXML
    public Label ev_speedValue;

    @FXML
    private ComboBox<Move> moveChoiceBox1;

    @FXML
    private ComboBox<Move> moveChoiceBox2;

    @FXML
    private ComboBox<Move> moveChoiceBox3;

    @FXML
    private ComboBox<Move> moveChoiceBox4;
    private PokemonController controller;

    @Override
    public void onOpen(Object input) {
        this.controller = (PokemonController) input;
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
        List<Pokemon> pokemons = PokemonDAO.build().findAll();
        ObservableList<Pokemon> observableNames = FXCollections.observableArrayList(pokemons);
        pokemonComboBox.setItems(observableNames);
        pokemonComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Pokemon item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getPokemonName());
            }
        });
        pokemonComboBox.setButtonCell(new ListCell<Pokemon>() {
            @Override
            protected void updateItem(Pokemon item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getPokemonName());
            }
        });
        pokemonComboBox.setOnAction(event -> {
            Pokemon selectedPokemon = pokemonComboBox.getValue();
            initializeWithPokemon(selectedPokemon);
        });
    }

    private void initializeWithPokemon(Pokemon pokemon) {
        photo.setText(pokemon.getPhotoPokemon());
        InputStream inputStream = getClass().getResourceAsStream("/com/github/dangelcrack/media/PokemonImages/" + pokemon.getPhotoPokemon());
        Image image = new Image(inputStream);
        imageView.setImage(image);
        level.setValue(pokemon.getLevelCap());
        levelValue.setText(String.valueOf((int)level.getValue()));
        hp.setValue(pokemon.getHealth());
        hpValue.setText(String.valueOf((int)hp.getValue()));
        attack.setValue(pokemon.getAttack());
        attackValue.setText(String.valueOf((int)attack.getValue()));
        defense.setValue(pokemon.getDefense());
        defenseValue.setText(String.valueOf((int)defense.getValue()));
        spattack.setValue(pokemon.getSpecialAttack());
        spattackValue.setText(String.valueOf((int)spattack.getValue()));
        spdefense.setValue(pokemon.getSpecialDefense());
        spdefenseValue.setText(String.valueOf((int)spdefense.getValue()));
        speed.setValue(pokemon.getSpeed());
        speedValue.setText(String.valueOf((int)speed.getValue()));
        iv_hp.setValue(pokemon.getIv_Health());
        iv_hpValue.setText(String.valueOf((int)iv_hp.getValue()));
        iv_attack.setValue(pokemon.getIv_Attack());
        iv_attackValue.setText(String.valueOf((int)iv_attack.getValue()));
        iv_defense.setValue(pokemon.getIv_Defense());
        iv_defenseValue.setText(String.valueOf((int)iv_defense.getValue()));
        iv_spattack.setValue(pokemon.getIv_SpecialAttack());
        iv_spattackValue.setText(String.valueOf((int)iv_spattack.getValue()));
        iv_spdefense.setValue(pokemon.getIv_SpecialDefense());
        iv_spdefenseValue.setText(String.valueOf((int)iv_spdefense.getValue()));
        iv_speed.setValue(pokemon.getIv_Speed());
        iv_speedValue.setText(String.valueOf((int)iv_speed.getValue()));
        ev_hp.setValue(pokemon.getEv_Health());
        ev_hpValue.setText(String.valueOf((int)ev_hp.getValue()));
        ev_attack.setValue(pokemon.getEv_Attack());
        ev_attackValue.setText(String.valueOf((int)ev_attack.getValue()));
        ev_defense.setValue(pokemon.getEv_Defense());
        ev_defenseValue.setText(String.valueOf((int)ev_defense.getValue()));
        ev_spattack.setValue(pokemon.getEv_SpecialAttack());
        ev_spattackValue.setText(String.valueOf((int)ev_spattack.getValue()));
        ev_spdefense.setValue(pokemon.getEv_SpecialDefense());
        ev_spdefenseValue.setText(String.valueOf((int)ev_spdefense.getValue()));
        ev_speed.setValue(pokemon.getEv_Speed());
        ev_speedValue.setText(String.valueOf((int)ev_speed.getValue()));
        level.valueProperty().addListener((observable, oldValue, newValue) -> levelValue.setText(String.valueOf(newValue.intValue())));
        hp.valueProperty().addListener((observable, oldValue, newValue) -> hpValue.setText(String.valueOf(newValue.intValue())));
        attack.valueProperty().addListener((observable, oldValue, newValue) -> attackValue.setText(String.valueOf(newValue.intValue())));
        defense.valueProperty().addListener((observable, oldValue, newValue) -> defenseValue.setText(String.valueOf(newValue.intValue())));
        spattack.valueProperty().addListener((observable, oldValue, newValue) -> spattackValue.setText(String.valueOf(newValue.intValue())));
        spdefense.valueProperty().addListener((observable, oldValue, newValue) -> spdefenseValue.setText(String.valueOf(newValue.intValue())));
        speed.valueProperty().addListener((observable, oldValue, newValue) -> speedValue.setText(String.valueOf(newValue.intValue())));
        iv_hp.valueProperty().addListener((observable, oldValue, newValue) -> iv_hpValue.setText(String.valueOf(newValue.intValue())));
        iv_attack.valueProperty().addListener((observable, oldValue, newValue) -> iv_attackValue.setText(String.valueOf(newValue.intValue())));
        iv_defense.valueProperty().addListener((observable, oldValue, newValue) -> iv_defenseValue.setText(String.valueOf(newValue.intValue())));
        iv_spattack.valueProperty().addListener((observable, oldValue, newValue) -> iv_spattackValue.setText(String.valueOf(newValue.intValue())));
        iv_spdefense.valueProperty().addListener((observable, oldValue, newValue) -> iv_spdefenseValue.setText(String.valueOf(newValue.intValue())));
        iv_speed.valueProperty().addListener((observable, oldValue, newValue) -> iv_speedValue.setText(String.valueOf(newValue.intValue())));
        ev_hp.valueProperty().addListener((observable, oldValue, newValue) -> ev_hpValue.setText(String.valueOf(newValue.intValue())));
        ev_attack.valueProperty().addListener((observable, oldValue, newValue) -> ev_attackValue.setText(String.valueOf(newValue.intValue())));
        ev_defense.valueProperty().addListener((observable, oldValue, newValue) -> ev_defenseValue.setText(String.valueOf(newValue.intValue())));
        ev_spattack.valueProperty().addListener((observable, oldValue, newValue) -> ev_spattackValue.setText(String.valueOf(newValue.intValue())));
        ev_spdefense.valueProperty().addListener((observable, oldValue, newValue) -> ev_spdefenseValue.setText(String.valueOf(newValue.intValue())));
        ev_speed.valueProperty().addListener((observable, oldValue, newValue) -> ev_speedValue.setText(String.valueOf(newValue.intValue())));
        firstType.setValue(pokemon.getPokemonFirstType());
        secondType.setValue(pokemon.getPokemonSecondType());
        firstType.setItems(FXCollections.observableArrayList(Types.values()));
        secondType.setItems(FXCollections.observableArrayList(Types.values()));
        nature.setValue(pokemon.getNature());
        nature.setItems(FXCollections.observableArrayList(Nature.values()));
        if (pokemon.getMoves() != null && !pokemon.getMoves().isEmpty()) {
            // Limpiar los items anteriores
            moveChoiceBox1.getItems().clear();
            moveChoiceBox2.getItems().clear();
            moveChoiceBox3.getItems().clear();
            moveChoiceBox4.getItems().clear();
            ObservableList<Move> movimientos = FXCollections.observableArrayList(MoveDAO.build().findAll());
            moveChoiceBox1.getItems().addAll(movimientos);
            moveChoiceBox2.getItems().addAll(movimientos);
            moveChoiceBox3.getItems().addAll(movimientos);
            moveChoiceBox4.getItems().addAll(movimientos);

            // Seleccionar los movimientos del Pokemon en los ChoiceBox
            if (pokemon.getMoves().size() > 0) {
                moveChoiceBox1.setValue(pokemon.getMoves().get(0));
            }
            if (pokemon.getMoves().size() > 1) {
                moveChoiceBox2.setValue(pokemon.getMoves().get(1));
            }
            if (pokemon.getMoves().size() > 2) {
                moveChoiceBox3.setValue(pokemon.getMoves().get(2));
            }
            if (pokemon.getMoves().size() > 3) {
                moveChoiceBox4.setValue(pokemon.getMoves().get(3));
            }
        }
    }

    @FXML
    private void closeWindow(Event event) {
        String nameValue = pokemonComboBox.getValue().getPokemonName();
        String photoValue= photo.getText();
        int levelValue = (int) level.getValue();
        int hpValue = (int) hp.getValue();
        int attackValue = (int) attack.getValue();
        int defenseValue = (int) defense.getValue();
        int spattackValue = (int) spattack.getValue();
        int spdefenseValue = (int) spdefense.getValue();
        int speedValue = (int) speed.getValue();
        int ivHpValue = (int) iv_hp.getValue();
        int ivAttackValue = (int) iv_attack.getValue();
        int ivDefenseValue = (int) iv_defense.getValue();
        int ivSpattackValue = (int) iv_spattack.getValue();
        int ivSpdefenseValue = (int) iv_spdefense.getValue();
        int ivSpeedValue = (int) iv_speed.getValue();
        int evHpValue = (int) ev_hp.getValue();
        int evAttackValue = (int) ev_attack.getValue();
        int evDefenseValue = (int) ev_defense.getValue();
        int evSpattackValue = (int) ev_spattack.getValue();
        int evSpdefenseValue = (int) ev_spdefense.getValue();
        int evSpeedValue = (int) ev_speed.getValue();
        Obj obj = null;
        Types firstTypeValue = firstType.getValue();
        Types secondTypeValue = secondType.getValue();
        Nature natureValue = nature.getValue();
        Move move1 = moveChoiceBox1.getValue();
        Move move2 = moveChoiceBox2.getValue();
        Move move3 = moveChoiceBox3.getValue();
        Move move4 = moveChoiceBox4.getValue();
        List<Move> moves = new ArrayList<>();
        if (move1 != null) moves.add(move1);
        if (move2 != null) moves.add(move2);
        if (move3 != null) moves.add(move3);
        if (move4 != null) moves.add(move4);
        Pokemon pokemonBeingEdited = new Pokemon(
                nameValue, firstTypeValue, secondTypeValue, photoValue, levelValue, hpValue, attackValue, defenseValue,
                spattackValue, spdefenseValue, speedValue, ivHpValue, ivAttackValue, ivDefenseValue, ivSpattackValue,
                ivSpdefenseValue, ivSpeedValue, evHpValue, evAttackValue, evDefenseValue, evSpattackValue, evSpdefenseValue,
                evSpeedValue, moves, obj, natureValue);
        if (!photo.getText().isEmpty()) {
            pokemonBeingEdited.setPhotoPokemon(photo.getText());
        }
        this.controller.deleteOldPokemon(pokemonComboBox.getValue());
        this.controller.savePokemon(pokemonBeingEdited);
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
            imageView.setImage(image);//esto es para que en la pantalla de añadir pokemon al seleccionar la imagen se muestre abajo del boton de agregar
        }
    }

}

