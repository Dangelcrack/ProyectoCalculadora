package com.github.dangelcrack.view;

import com.github.dangelcrack.model.entity.Pokemon;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FormPokemonController extends Controller implements Initializable {
    @FXML
    private TextField firstType;
    @FXML
    private TextField name;
    @FXML
    private TextField secondType;
    @FXML
    private TextField photo;
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
        Pokemon pokemon = new Pokemon(name.getText(), firstType.getText(), secondType.getText(), photo.getText(), levelValue, hpValue, attackValue, defenseValue, spattackValue, spdefenseValue, speedValue,
                ivHpValue, ivAttackValue, ivDefenseValue, ivSpattackValue, ivSpdefenseValue, ivSpeedValue,
                evHpValue, evAttackValue, evDefenseValue, evSpattackValue, evSpdefenseValue, evSpeedValue);
        this.controller.savePokemon(pokemon);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
