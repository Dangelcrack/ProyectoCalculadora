package com.github.dangelcrack.view;

import com.github.dangelcrack.model.dao.MoveDAO;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Pokemon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddPokemonController extends Controller implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private ComboBox<Types> firstType;
    @FXML
    private TextField name;
    @FXML
    private ComboBox<Types> secondType;
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
    public void initialize(URL location, ResourceBundle resources) {
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/ModalImageUtils/imgAddPokemon.png");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imageUrl.toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        vbox.setBackground(new Background(backgroundImage));
        firstType.setItems(FXCollections.observableArrayList(Types.values()));
        secondType.setItems(FXCollections.observableArrayList(Types.values()));
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
        List<Move> moves = MoveDAO.build().findAll();
        ObservableList<Move> observableMoves = FXCollections.observableArrayList(moves);
        moveChoiceBox1.setItems(observableMoves);
        moveChoiceBox2.setItems(observableMoves);
        moveChoiceBox3.setItems(observableMoves);
        moveChoiceBox4.setItems(observableMoves);
    }


    @FXML
    private void closeWindow(Event event) {
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

        Types firstTypeValue = firstType.getValue();
        Types secondTypeValue = secondType.getValue();
        Move move1 = moveChoiceBox1.getValue();
        Move move2 = moveChoiceBox2.getValue();
        Move move3 = moveChoiceBox3.getValue();
        Move move4 = moveChoiceBox4.getValue();
        List<Move> moves = new ArrayList<>();
        if (move1 != null) moves.add(move1);
        if (move2 != null) moves.add(move2);
        if (move3 != null) moves.add(move3);
        if (move4 != null) moves.add(move4);
        Pokemon pokemon = new Pokemon(name.getText(), firstTypeValue, secondTypeValue, photo.getText(), levelValue, hpValue, attackValue, defenseValue, spattackValue, spdefenseValue, speedValue,
                ivHpValue, ivAttackValue, ivDefenseValue, ivSpattackValue, ivSpdefenseValue, ivSpeedValue,
                evHpValue, evAttackValue, evDefenseValue, evSpattackValue, evSpdefenseValue, evSpeedValue, moves);

        pokemon.setPhotoPokemon(pokemon.getPhotoPokemon());
        this.controller.savePokemon(pokemon);
        //Antes de cerrar notificamos a nuestro parent que hay un pokemon nuevo
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
