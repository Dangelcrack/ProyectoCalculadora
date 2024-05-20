package com.github.dangelcrack.controller;
import com.github.dangelcrack.model.dao.MoveDAO;
import com.github.dangelcrack.model.dao.ObjDAO;
import com.github.dangelcrack.model.dao.PokemonDAO;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Obj;
import com.github.dangelcrack.model.entity.Pokemon;
import com.github.dangelcrack.model.entity.Category;
import com.github.dangelcrack.model.entity.Types;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
/**
 * Controller class for handling combat-related operations.
 */
public class CombatsController extends Controller implements Initializable {
    @FXML
    private VBox vbox;
    @FXML
    private ComboBox<Pokemon> pokemon1ComboBox;
    @FXML
    private ComboBox<Pokemon> pokemon2ComboBox;
    @FXML
    private ImageView imageViewPokemon1;
    @FXML
    private ImageView imageViewPokemon2;
    @FXML
    private ComboBox<Move> moveChoiceBoxPokemon1;
    @FXML
    private ComboBox<Move> moveChoiceBoxPokemon2;
    @FXML
    private ComboBox<Obj> objChoiceBoxPokemon1;
    @FXML
    private ComboBox<Obj> objChoiceBoxPokemon2;
    @FXML
    public Label levelValue1;
    @FXML
    private Label labelRemainingHealthPokemon1;
    @FXML
    private Label labelRemainingHealthPokemon2;
    @FXML
    public Label hpValue1;
    @FXML
    public Label attackValue1;
    @FXML
    public Label defenseValue1;
    @FXML
    public Label spAttackValue1;
    @FXML
    public Label spDefenseValue1;
    @FXML
    public Label speedValue1;
    @FXML
    public Label levelValue2;
    @FXML
    public Label hpValue2;
    @FXML
    public Label attackValue2;
    @FXML
    public Label defenseValue2;
    @FXML
    public Label spAttackValue2;
    @FXML
    public Label spDefenseValue2;
    @FXML
    public Label speedValue2;
    @FXML
    private ProgressBar healthBarPokemon1;
    @FXML
    private ProgressBar healthBarPokemon2;
    @FXML
    private Button btnCalculateHealth;
    private DoubleProperty totalHpPokemon1 = new SimpleDoubleProperty();
    private DoubleProperty totalHpPokemon2 = new SimpleDoubleProperty();


    @Override
    public void onOpen(Object input) throws IOException {
        // Implement any necessary operations when the view is opened
    }
    @Override
    public void onClose(Object output) {
        // Implement any necessary operations when the view is closed
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load the background image for the battlefield
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/ModalImageUtils/Campodebatalla.png");
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imageUrl.toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, false, true)
        );
        // Set the background image to the vbox
        vbox.setBackground(new Background(backgroundImage));
        // Retrieve all Pokemon from the database and add them to the combo boxes
        List<Pokemon> pokemons = PokemonDAO.build().findAll();
        ObservableList<Pokemon> observableNames = FXCollections.observableArrayList(pokemons);
        pokemon1ComboBox.setItems(observableNames);
        // Set an action on Pokemon selection from the combo box
        pokemon1ComboBox.setOnAction(event -> {
            Pokemon selectedPokemon1 = pokemon1ComboBox.getValue();
            initializeWithPokemon1(selectedPokemon1);
        });
        pokemon2ComboBox.setItems(observableNames);
        // Set an action on Pokemon selection from the combo box
        pokemon2ComboBox.setOnAction(event -> {
            Pokemon selectedPokemon2 = pokemon2ComboBox.getValue();
            initializeWithPokemon2(selectedPokemon2);
        });
        // Calculate remaining health and update health bars upon button click
        btnCalculateHealth.setOnAction(event -> {
            double remainingHealth1 = calculateRemainingHealthPokemon1();
            double remainingHealth2 = calculateRemainingHealthPokemon2();
            double progressBar1 = updateHealthBarPokemon1(remainingHealth1);
            double progressBar2 = updateHealthBarPokemon2(remainingHealth2);
            healthBarPokemon1.setProgress(progressBar1);
            healthBarPokemon2.setProgress(progressBar2);
            // Update the labels with the remaining health
            labelRemainingHealthPokemon1.setText((int) (remainingHealth1) + "/" + totalHpPokemon1.getValue());
            labelRemainingHealthPokemon2.setText((int) (remainingHealth2) + "/" + totalHpPokemon2.getValue());
        });
    }

    /**
     * Initializes the view with the first selected Pokemon's data.
     *
     * @param pokemon The first selected Pokemon.
     */
    private void initializeWithPokemon1(Pokemon pokemon) {
        initializeWithPokemon(pokemon,levelValue1, imageViewPokemon1, hpValue1, attackValue1, defenseValue1, spAttackValue1, spDefenseValue1, speedValue1, moveChoiceBoxPokemon1, objChoiceBoxPokemon1, totalHpPokemon1);
    }
    /**
     * Initializes the view with the second selected Pokemon's data.
     *
     * @param pokemon The second selected Pokemon.
     */
    private void initializeWithPokemon2(Pokemon pokemon) {
        initializeWithPokemon(pokemon, levelValue2, imageViewPokemon2, hpValue2, attackValue2, defenseValue2, spAttackValue2, spDefenseValue2, speedValue2, moveChoiceBoxPokemon2, objChoiceBoxPokemon2, totalHpPokemon2);
    }
    /**
     * Initializes the view with the given Pokemon's data.
     *
     * @param pokemon The selected Pokemon.
     * @param levelValue The label to display the Pokemon's level.
     * @param imageView The ImageView to display the Pokemon's image.
     * @param hpValue The label to display the Pokemon's HP value.
     * @param attackValue The label to display the Pokemon's Attack value.
     * @param defenseValue The label to display the Pokemon's Defense value.
     * @param spAttackValue The label to display the Pokemon's Special Attack value.
     * @param spDefenseValue The label to display the Pokemon's Special Defense value.
     * @param speedValue The label to display the Pokemon's Speed value.
     * @param moveChoiceBox The ComboBox to display the Pokemon's moves.
     * @param objChoiceBox The ComboBox to display the Pokemon's objects.
     * @param hpTotalProperty The property to store the Pokemon's total HP.
     */
    private void initializeWithPokemon(Pokemon pokemon,Label levelValue, ImageView imageView, Label hpValue, Label attackValue, Label defenseValue, Label spAttackValue, Label spDefenseValue, Label speedValue, ComboBox<Move> moveChoiceBox, ComboBox<Obj> objChoiceBox, DoubleProperty hpTotalProperty) {
        InputStream inputStream = getClass().getResourceAsStream("/com/github/dangelcrack/media/PokemonImages/" + pokemon.getPhotoPokemon());
        Image image = new Image(inputStream);
        imageView.setImage(image);
        double levelCap = pokemon.getLevelCap();
        levelValue.setText(String.valueOf((int)levelCap));
        double hpTotalValue = 10 + (int) ((levelCap / 100.0) * ((pokemon.getHealth() * 2) + pokemon.getIv_Health() + pokemon.getEv_Health())) + levelCap;
        hpValue.setText(String.valueOf((int) hpTotalValue));
        hpTotalProperty.set(hpTotalValue);
        double attackTotalValue = 5 + ((levelCap / 100.0) * ((pokemon.getAttack() * 2) + pokemon.getIv_Attack() + (double) pokemon.getEv_Attack() / 4)) * (pokemon.getNature().getAttackMultiplier());
        attackValue.setText(String.valueOf((int) attackTotalValue));
        double defenseTotalValue = 5 + ((levelCap / 100.0) * ((pokemon.getDefense() * 2) + pokemon.getIv_Defense() + pokemon.getEv_Defense() / 4)) * (pokemon.getNature().getDefenseMultiplier());
        defenseValue.setText(String.valueOf((int) defenseTotalValue));
        double spAttackTotalValue = 5 + ((levelCap / 100.0) * ((pokemon.getSpecialAttack() * 2) + pokemon.getIv_SpecialAttack() + pokemon.getEv_SpecialAttack() / 4)) * (pokemon.getNature().getSpAttackMultiplier());
        spAttackValue.setText(String.valueOf((int) spAttackTotalValue));
        double spDefenseTotalValue = 5 + ((levelCap / 100.0) * ((pokemon.getSpecialDefense() * 2) + pokemon.getIv_SpecialDefense() + pokemon.getEv_SpecialDefense() / 4)) * (pokemon.getNature().getSpDefenseMultiplier());
        spDefenseValue.setText(String.valueOf((int) spDefenseTotalValue));
        double speedTotalValue = 5 + ((levelCap / 100.0) * ((pokemon.getSpeed() * 2) + pokemon.getIv_Speed() + pokemon.getEv_Speed() / 4)) * (pokemon.getNature().getSpeedMultiplier());
        speedValue.setText(String.valueOf((int) speedTotalValue));
        if (pokemon.getMoves() != null && !pokemon.getMoves().isEmpty()) {
            moveChoiceBox.getItems().clear();
            ObservableList<Move> movimientos = FXCollections.observableArrayList(MoveDAO.build().findByPokemon(pokemon));
            moveChoiceBox.getItems().addAll(movimientos);
            if (pokemon.getMoves().size() > 0) {
                moveChoiceBox.setValue(pokemon.getMoves().get(0));
            }
        }
        if (pokemon.getObj() != null) {
            objChoiceBox.getItems().clear();
            ObservableList<Obj> objs = FXCollections.observableArrayList(ObjDAO.build().findAll());
            objChoiceBox.getItems().addAll(objs);
            objChoiceBox.setValue(pokemon.getObj());
        }
    }
    /**
     * Updates the health bar of the first Pokemon.
     *
     * @param remaining The remaining health.
     * @return The updated progress value.
     */
    private double updateHealthBarPokemon1(double remaining) {
        double maximumHealth = totalHpPokemon1.getValue();
        double healthPercentage = remaining / maximumHealth;
        healthBarPokemon1.setProgress(healthPercentage);
        if (healthPercentage > 0.5) {
            healthBarPokemon1.setStyle("-fx-accent: green;");
        } else if (healthPercentage > 0.2) {
            healthBarPokemon1.setStyle("-fx-accent: yellow;");
        } else if (healthPercentage > 0){
            healthBarPokemon1.setStyle("-fx-accent: red;");
        } else {
            healthBarPokemon1.setStyle("-fx-accent: transparent;");
        }
        return healthPercentage;
    }
    /**
     * Updates the health bar of the second Pokemon.
     *
     * @param remaining The remaining health.
     * @return The updated progress value.
     */
    private double updateHealthBarPokemon2(double remaining) {
        double maximumHealth = totalHpPokemon2.getValue();
        double healthPercentage = remaining / maximumHealth;
        healthBarPokemon2.setProgress(healthPercentage);
        if (healthPercentage > 0.5) {
            healthBarPokemon2.setStyle("-fx-accent: green;");
        } else if (healthPercentage > 0.2) {
            healthBarPokemon2.setStyle("-fx-accent: yellow;");
        } else if (healthPercentage > 0){
            healthBarPokemon2.setStyle("-fx-accent: red;");
        } else {
            healthBarPokemon2.setStyle("-fx-accent: transparent;");
        }
        return healthPercentage;
    }

    /**
     * Calculates the remaining health of the second Pokemon.
     *
     * @return The remaining health value.
     */
    private double calculateRemainingHealthPokemon2() {
        Move move = moveChoiceBoxPokemon1.getValue();
        if (move == null) {
            return totalHpPokemon2.getValue();
        }
        Obj object = objChoiceBoxPokemon1.getValue();
        double objectModifier;
        if(object == null){
            objectModifier = 1.0;
        } else {
            objectModifier = calculateObjectModifier(move, object);
        }
        double baseDamage = getBaseDamage(pokemon1ComboBox.getValue(), move, pokemon2ComboBox.getValue(), object);
        double typeModifier = calculateTypeModifier(move.getTypeMove(), pokemon2ComboBox.getValue().getPokemonFirstType(), pokemon2ComboBox.getValue().getPokemonSecondType());
        double stab; // Same Type Attack Bonus
        if (pokemon1ComboBox.getValue().getPokemonFirstType().equals(move.getTypeMove())) {
            stab = 1.5;
        } else {
            Types secondType = pokemon1ComboBox.getValue().getPokemonSecondType();
            stab = (secondType != null && secondType.equals(move.getTypeMove())) ? 1.5 : 1.0;
        }
        double result = baseDamage * typeModifier * stab * objectModifier;
        double remainingHealth = totalHpPokemon2.getValue() - result;
        return remainingHealth;
    }
    /**
     * Calculates the remaining health of the first Pokemon.
     *
     * @return The remaining health value.
     */
    private double calculateRemainingHealthPokemon1() {
        Move move = moveChoiceBoxPokemon2.getValue();
        if (move == null) {
            return totalHpPokemon1.getValue();
        }
        Obj object = objChoiceBoxPokemon2.getValue();
        double objectModifier;
        if(object == null){
            objectModifier = 1.0;
        } else {
            objectModifier = calculateObjectModifier(move, object);
        }
        double baseDamage = getBaseDamage(pokemon2ComboBox.getValue(), move, pokemon1ComboBox.getValue(), object);
        double typeModifier = calculateTypeModifier(move.getTypeMove(), pokemon1ComboBox.getValue().getPokemonFirstType(), pokemon1ComboBox.getValue().getPokemonSecondType());
        double stab; // Same Type Attack Bonus
        if (pokemon1ComboBox.getValue().getPokemonFirstType().equals(move.getTypeMove())) {
            stab = 1.5;
        } else {
            Types secondType = pokemon1ComboBox.getValue().getPokemonSecondType();
            stab = (secondType != null && secondType.equals(move.getTypeMove())) ? 1.5 : 1.0;
        }
        double result = baseDamage * typeModifier * stab * objectModifier;
        return totalHpPokemon1.getValue() - result;
    }

    /**
     * Calculates the modifier based on the object and the move.
     *
     * @param move The move being used.
     * @param object The object affecting the move.
     * @return The calculated modifier.
     */
    private double calculateObjectModifier(Move move, Obj object) {
        double modifier = 1.0;
        if (object.getBoostCategory() == Category.PHYSICAL && move.getCategory() == Category.PHYSICAL) {
            modifier += (double) object.getAttackBoost() / 10;
        }
        if (object.getBoostCategory() == Category.SPECIAL && move.getCategory() == Category.SPECIAL) {
            modifier += (double) object.getSpAttackBoost() / 10;
        }
        if (object.getBoostType() == move.getTypeMove()) {
            modifier += 0.2;
        }
        return modifier; // Return the calculated modifier
    }
    /**
     * Calculates the base damage of a move.
     *
     * @param attacker The attacking Pokemon.
     * @param move The move being used.
     * @param defender The defending Pokemon.
     * @param object The object affecting the move.
     * @return The calculated base damage.
     */
    private double getBaseDamage(Pokemon attacker, Move move, Pokemon defender, Obj object) {
        double attackPower = move.getPower();
        double totalAttack;
        double totalDefense;
        double defenseBoostObject;
        double specialDefenseBoostObject;
        if(object.getDefenseBoost() == 0){
            defenseBoostObject = 1;
        } else {
            defenseBoostObject = (double) object.getDefenseBoost() / 10;
        }
        if(object.getSpDefenseBoost() == 0){
            specialDefenseBoostObject = 1;
        } else {
            specialDefenseBoostObject = (double) object.getDefenseBoost() / 10;
        }
        double modifiedDefense;
        if (move.getCategory() == Category.SPECIAL) {
            totalAttack = attacker.getSpecialAttack();
            totalDefense = defender.getSpecialDefense();
            modifiedDefense = totalDefense * specialDefenseBoostObject;
        } else if (move.getCategory() == Category.PHYSICAL) {
            totalAttack = attacker.getAttack();
            totalDefense = defender.getDefense();
            modifiedDefense = totalDefense * defenseBoostObject;
        } else {
            attackPower = 0;
            totalAttack = attacker.getSpecialAttack();
            totalDefense = defender.getSpecialDefense();
            modifiedDefense = totalDefense * object.getDefenseBoost();
        }

        return (((((double) (2 * attacker.getLevelCap()) / 5 + 2) * attackPower * (totalAttack / modifiedDefense)) / 50) + 2);
    }
    /**
     * Calculates the type modifier for a move against a Pokémon with primary and secondary types.
     * @param typeMove The type of the move being used.
     * @param primaryTypePokemon2 The primary type of the Pokémon the move is used against.
     * @param secondaryTypePokemon2 The secondary type of the Pokémon the move is used against (can be null).
     * @return The type effectiveness modifier.
     */
    private double calculateTypeModifier(Types typeMove, Types primaryTypePokemon2, Types secondaryTypePokemon2) {
        double modifier = 1.0;
        if (typeMove.isImmuneAgainst(primaryTypePokemon2)) {
            return 0.0;
        }
        if (typeMove.isSuperEffectiveAgainst(primaryTypePokemon2)) {
            modifier *= 2.0;
        } else if (typeMove.isNotEffectiveAgainst(primaryTypePokemon2)) {
            modifier *= 0.5;
        }
        if (secondaryTypePokemon2 != null) {
            if (typeMove.isSuperEffectiveAgainst(secondaryTypePokemon2)) {
                modifier *= 2.0;
            } else if (typeMove.isNotEffectiveAgainst(secondaryTypePokemon2)) {
                modifier *= 0.5;
            }
        }
        return modifier;
    }
}
