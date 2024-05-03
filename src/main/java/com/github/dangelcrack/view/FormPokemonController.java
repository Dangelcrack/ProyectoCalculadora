package com.github.dangelcrack.view;

import com.github.dangelcrack.model.dao.MoveDAO;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Pokemon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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

public class FormPokemonController extends Controller implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private TextField firstType;
    @FXML
    private TextField name;
    @FXML
    private TextField secondType;
    @FXML
    private TextField photo;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField level;
    @FXML
    private TextField hp;
    @FXML
    private TextField attack;
    @FXML
    private TextField defense;
    @FXML
    private TextField spattack;
    @FXML
    private TextField spdefense;
    @FXML
    private TextField speed;
    @FXML
    private TextField iv_hp;
    @FXML
    private TextField iv_attack;
    @FXML
    private TextField iv_defense;
    @FXML
    private TextField iv_spattack;
    @FXML
    private TextField iv_spdefense;
    @FXML
    private TextField iv_speed;
    @FXML
    private TextField ev_hp;
    @FXML
    private TextField ev_attack;
    @FXML
    private TextField ev_defense;
    @FXML
    private TextField ev_spattack;
    @FXML
    private TextField ev_spdefense;
    @FXML
    private TextField ev_speed;

    @FXML
    private ChoiceBox<Move> moveChoiceBox1;

    @FXML
    private ChoiceBox<Move> moveChoiceBox2;

    @FXML
    private ChoiceBox<Move> moveChoiceBox3;

    @FXML
    private ChoiceBox<Move> moveChoiceBox4;
    private MainController controller;

    @Override
    public void onOpen(Object input) throws IOException {
        this.controller = (MainController) input;
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/img.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imageUrl.toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        vbox.setBackground(new Background(backgroundImage));
        List<Move> moves = MoveDAO.build().findAll();
        ObservableList<Move> observableMoves = FXCollections.observableArrayList(moves);
        moveChoiceBox1.setItems(observableMoves);
        moveChoiceBox2.setItems(observableMoves);
        moveChoiceBox3.setItems(observableMoves);
        moveChoiceBox4.setItems(observableMoves);
    }


    @FXML
    private void closeWindow(Event event) {
        int levelValue = Integer.parseInt(level.getText());
        int hpValue = Integer.parseInt(hp.getText());
        int attackValue = Integer.parseInt(attack.getText());
        int defenseValue = Integer.parseInt(defense.getText());
        int spattackValue = Integer.parseInt(spattack.getText());
        int spdefenseValue = Integer.parseInt(spdefense.getText());
        int speedValue = Integer.parseInt(speed.getText());
        int ivHpValue = Integer.parseInt(iv_hp.getText());
        int ivAttackValue = Integer.parseInt(iv_attack.getText());
        int ivDefenseValue = Integer.parseInt(iv_defense.getText());
        int ivSpattackValue = Integer.parseInt(iv_spattack.getText());
        int ivSpdefenseValue = Integer.parseInt(iv_spdefense.getText());
        int ivSpeedValue = Integer.parseInt(iv_speed.getText());
        int evHpValue = Integer.parseInt(ev_hp.getText());
        int evAttackValue = Integer.parseInt(ev_attack.getText());
        int evDefenseValue = Integer.parseInt(ev_defense.getText());
        int evSpattackValue = Integer.parseInt(ev_spattack.getText());
        int evSpdefenseValue = Integer.parseInt(ev_spdefense.getText());
        int evSpeedValue = Integer.parseInt(ev_speed.getText());
        Move move1 = moveChoiceBox1.getValue();
        Move move2 = moveChoiceBox2.getValue();
        Move move3 = moveChoiceBox3.getValue();
        Move move4 = moveChoiceBox4.getValue();
        List<Move> moves = new ArrayList<>();
        if (move1 != null) moves.add(move1);
        if (move2 != null) moves.add(move2);
        if (move3 != null) moves.add(move3);
        if (move4 != null) moves.add(move4);
        Pokemon pokemon = new Pokemon(name.getText(), firstType.getText(), secondType.getText(), photo.getText(), levelValue, hpValue, attackValue, defenseValue, spattackValue, spdefenseValue, speedValue,
                ivHpValue, ivAttackValue, ivDefenseValue, ivSpattackValue, ivSpdefenseValue, ivSpeedValue,
                evHpValue, evAttackValue, evDefenseValue, evSpattackValue, evSpdefenseValue, evSpeedValue,moves);
        this.controller.savePokemon(pokemon);
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
            String photoPath = selectedFile.getAbsolutePath();
            photo.setText(photoPath);
            String mediaPath = "src/main/resources/com/github/dangelcrack/media/";

            File destinationFolder = new File(mediaPath);
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }

            File destinationFile = new File(destinationFolder.getAbsolutePath() + "/" + selectedFile.getName());
            try {
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Image image = new Image(destinationFile.toURI().toString());
                imageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
