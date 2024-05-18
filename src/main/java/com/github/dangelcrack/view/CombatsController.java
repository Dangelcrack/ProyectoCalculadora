package com.github.dangelcrack.view;
import com.github.dangelcrack.model.dao.MoveDAO;
import com.github.dangelcrack.model.dao.ObjDAO;
import com.github.dangelcrack.model.dao.PokemonDAO;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Obj;
import com.github.dangelcrack.model.entity.Pokemon;
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
    private Label labelVidaRestantePokemon1;
    @FXML
    private Label labelVidaRestantePokemon2;
    @FXML
    public Label hpValue1;
    @FXML
    public Label attackValue1;
    @FXML
    public Label defenseValue1;
    @FXML
    public Label spattackValue1;
    @FXML
    public Label spdefenseValue1;
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
    public Label spattackValue2;
    @FXML
    public Label spdefenseValue2;
    @FXML
    public Label speedValue2;
    @FXML
    private ProgressBar barraVidaPokemon1;
    @FXML
    private ProgressBar barraVidaPokemon2;
    @FXML
    private Button btnCalcularVida;
    private DoubleProperty hpTotalPokemon1 = new SimpleDoubleProperty();
    private DoubleProperty hpTotalPokemon2 = new SimpleDoubleProperty();
    @Override
    public void onOpen(Object input) throws IOException {
    }
    @Override
    public void onClose(Object output) {
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        URL imageUrl = getClass().getResource("/com/github/dangelcrack/media/ModalImageUtils/Campodebatalla.png");
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
        pokemon1ComboBox.setItems(observableNames);
        pokemon1ComboBox.setOnAction(event -> {
            Pokemon selectedPokemon1 = pokemon1ComboBox.getValue();
            initializeWithPokemon1(selectedPokemon1);
        });
        pokemon2ComboBox.setItems(observableNames);
        pokemon2ComboBox.setOnAction(event -> {
            Pokemon selectedPokemon2 = pokemon2ComboBox.getValue();
            initializeWithPokemon2(selectedPokemon2);
        });
        btnCalcularVida.setOnAction(event -> {
            double vidaRestante1 = calcularVidaRestantePokemon1();
            double vidaRestante2 = calcularVidaRestantePokemon2();
            double progresoBarra1 = actualizarBarraVidaPokemon1(vidaRestante1);
            double progresoBarra2 = actualizarBarraVidaPokemon2(vidaRestante2);
            barraVidaPokemon1.setProgress(progresoBarra1);
            barraVidaPokemon2.setProgress(progresoBarra2);
            labelVidaRestantePokemon1.setText((int) (vidaRestante1) + "/" + hpTotalPokemon1.getValue());
            labelVidaRestantePokemon2.setText((int) (vidaRestante2) + "/" + hpTotalPokemon2.getValue());
        });

    }
    private void initializeWithPokemon1(Pokemon pokemon) {
        initializeWithPokemon(pokemon,levelValue1, imageViewPokemon1, hpValue1, attackValue1, defenseValue1, spattackValue1, spdefenseValue1, speedValue1, moveChoiceBoxPokemon1, objChoiceBoxPokemon1, hpTotalPokemon1);
    }
    private void initializeWithPokemon2(Pokemon pokemon) {
        initializeWithPokemon(pokemon, levelValue2, imageViewPokemon2, hpValue2, attackValue2, defenseValue2, spattackValue2, spdefenseValue2, speedValue2, moveChoiceBoxPokemon2, objChoiceBoxPokemon2, hpTotalPokemon2);
    }
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
    private double actualizarBarraVidaPokemon1(double restante) {
        double vidaMaxima = hpTotalPokemon1.getValue();
        double porcentajeVida = restante / vidaMaxima;
        barraVidaPokemon1.setProgress(porcentajeVida);
        if (porcentajeVida > 0.5) {
            barraVidaPokemon1.setStyle("-fx-accent: green;");
        } else if (porcentajeVida > 0.2) {
            barraVidaPokemon1.setStyle("-fx-accent: yellow;");
        } else if (porcentajeVida>0){
            barraVidaPokemon1.setStyle("-fx-accent: red;");
        }else{
            barraVidaPokemon1.setStyle("-fx-accent: transparent;");
        }
        return porcentajeVida;
    }

    private double actualizarBarraVidaPokemon2(double restante) {
        double vidaMaxima = hpTotalPokemon2.getValue();
        double porcentajeVida = restante / vidaMaxima;
        barraVidaPokemon2.setProgress(porcentajeVida);
        if (porcentajeVida > 0.5) {
            barraVidaPokemon2.setStyle("-fx-accent: green;");
        } else if (porcentajeVida > 0.2) {
            barraVidaPokemon2.setStyle("-fx-accent: yellow;");
        } else if (porcentajeVida>0){
            barraVidaPokemon2.setStyle("-fx-accent: red;");
        }else{
            barraVidaPokemon2.setStyle("-fx-accent: transparent;");
        }
        return porcentajeVida;
    }
    private double calcularVidaRestantePokemon2() {
        Move move = moveChoiceBoxPokemon1.getValue();
        if (move == null) {
            return hpTotalPokemon2.getValue();
        }
        Obj obj = objChoiceBoxPokemon1.getValue();
        double modificadorObjeto;
        if(obj == null){
            modificadorObjeto=1.0;
        }else{
            modificadorObjeto = calcularModificadorObjeto(move, obj);
        }
        double dañoBase = getDañoBase(pokemon1ComboBox.getValue(), move, pokemon2ComboBox.getValue(), obj);
        double modificadorTipo = calcularModificadorTipo(move.getTypeMove(), pokemon2ComboBox.getValue().getPokemonFirstType(), pokemon2ComboBox.getValue().getPokemonSecondType());
        double stab;
        if (pokemon1ComboBox.getValue().getPokemonFirstType().equals(move.getTypeMove())) {
            stab = 1.5;
        } else {
            Types secondType = pokemon1ComboBox.getValue().getPokemonSecondType();
            stab = (secondType != null && secondType.equals(move.getTypeMove())) ? 1.5 : 1.0;
        }
        double resultado = dañoBase * modificadorTipo * stab*modificadorObjeto;
        double vidaRestante = hpTotalPokemon2.getValue() - resultado;
        return vidaRestante;
    }
    private double calcularVidaRestantePokemon1() {
        Move move = moveChoiceBoxPokemon2.getValue();
        if (move == null) {
            return hpTotalPokemon1.getValue();
        }
        Obj obj = objChoiceBoxPokemon2.getValue();
        double modificadorObjeto;
        if(obj == null){
            modificadorObjeto=1.0;
        }else{
            modificadorObjeto = calcularModificadorObjeto(move, obj);
        }
        double dañoBase = getDañoBase(pokemon2ComboBox.getValue(), move, pokemon1ComboBox.getValue(), obj);
        double modificadorTipo = calcularModificadorTipo(move.getTypeMove(), pokemon1ComboBox.getValue().getPokemonFirstType(), pokemon1ComboBox.getValue().getPokemonSecondType());
        double stab;
        if (pokemon1ComboBox.getValue().getPokemonFirstType().equals(move.getTypeMove())) {
            stab = 1.5;
        } else {
            Types secondType = pokemon1ComboBox.getValue().getPokemonSecondType();
            stab = (secondType != null && secondType.equals(move.getTypeMove())) ? 1.5 : 1.0;
        }
        double resultado = dañoBase * modificadorTipo * stab * modificadorObjeto;
        return hpTotalPokemon1.getValue() - resultado;
    }
    private double calcularModificadorObjeto(Move move, Obj objeto) {
        double modificador = 1.0;
        if (objeto.getBoostCategory() == Category.PHYSICAL && move.getCategory()==Category.PHYSICAL) {
            modificador += (double) objeto.getAttackBoost()/10;
        }
        if (objeto.getBoostCategory() == Category.SPECIAL && move.getCategory()==Category.SPECIAL) {
            modificador += (double) objeto.getSpAttackBoost() /10;
        }
        if (objeto.getBoostType()==move.getTypeMove()) {
            modificador += 0.2;
        }
        return modificador; // Devolver el modificador calculado
    }
    private double getDañoBase(Pokemon atacante, Move move, Pokemon defensor, Obj objeto) {
        double poderDeAtaque = move.getPower();
        double ataqueTotal;
        double defensaTotal;
        double potenciadorDefensaObjeto;
        double potenciadorDefensaEspecialObjeto;
        if(objeto.getDefenseBoost()==0){
            potenciadorDefensaObjeto=1;
        }else{
            potenciadorDefensaObjeto= (double) objeto.getDefenseBoost()/10;
        }
        if(objeto.getSpDefenseBoost()==0){
            potenciadorDefensaEspecialObjeto=1;
        }else{
            potenciadorDefensaEspecialObjeto= (double) objeto.getDefenseBoost()/10;
        }
        double defensaModificada;
        if (move.getCategory() == Category.SPECIAL) {
            ataqueTotal = atacante.getSpecialAttack();
            defensaTotal = defensor.getSpecialDefense();
            defensaModificada = defensaTotal * potenciadorDefensaEspecialObjeto;
        } else if (move.getCategory() == Category.PHYSICAL) {
            ataqueTotal = atacante.getAttack();
            defensaTotal = defensor.getDefense();
            defensaModificada = defensaTotal * potenciadorDefensaObjeto;
        } else {
            poderDeAtaque=0;
            ataqueTotal = atacante.getSpecialAttack();
            defensaTotal = defensor.getSpecialDefense();
            defensaModificada = defensaTotal * objeto.getDefenseBoost();
        }


        return (((((double) (2 * atacante.getLevelCap()) / 5 + 2) * poderDeAtaque * (ataqueTotal / defensaModificada)) / 50) + 2);
    }

    private double calcularModificadorTipo(Types tipoMovimiento, Types tipoPrimarioPokemon2, Types tipoSecundarioPokemon2) {
        double modificador = 1.0;
        if (tipoMovimiento.esInmuneContra(tipoPrimarioPokemon2)) {
            return 0.0;
        }
        if (tipoMovimiento.esSuperEfectivoContra(tipoPrimarioPokemon2)) {
            modificador *= 2.0;
        } else if (tipoMovimiento.esNoEfectivoContra(tipoPrimarioPokemon2)) {
            modificador *= 0.5;
        }
        if (tipoSecundarioPokemon2 != null) {
            if (tipoMovimiento.esSuperEfectivoContra(tipoSecundarioPokemon2)) {
                modificador *= 2.0;
            } else if (tipoMovimiento.esNoEfectivoContra(tipoSecundarioPokemon2)) {
                modificador *= 0.5;
            }
        }
        return modificador;
    }
}
