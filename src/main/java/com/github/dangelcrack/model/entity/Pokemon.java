package com.github.dangelcrack.model.entity;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The Pokemon class represents a Pokémon entity with various attributes such as name,
 * types, stats, IVs, EVs, moves, and other properties. It includes methods to manipulate
 * and retrieve these attributes.
 */
public class Pokemon {
    private String pokemonName;
    private Types pokemonFirstType;
    private Types pokemonSecondType;
    private String photoPokemon;
    private int levelCap;
    private int health;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    private int iv_Health;
    private int iv_Attack;
    private int iv_Defense;
    private int iv_SpecialAttack;
    private int iv_SpecialDefense;
    private int iv_Speed;
    private int ev_Health;
    private int ev_Attack;
    private int ev_Defense;
    private int ev_SpecialAttack;
    private int ev_SpecialDefense;
    private int ev_Speed;
    private List<Move> moves;
    private Obj obj;
    private Nature nature;

    /**
     * Constructs a Pokemon object with the specified attributes.
     *
     * @param pokemonName       the name of the Pokémon
     * @param pokemonFirstType  the first type of the Pokémon
     * @param pokemonSecondType the second type of the Pokémon
     * @param photoPokemon      the photo of the Pokémon
     * @param levelCap          the level cap of the Pokémon
     * @param health            the health stat of the Pokémon
     * @param attack            the attack stat of the Pokémon
     * @param defense           the defense stat of the Pokémon
     * @param specialAttack     the special attack stat of the Pokémon
     * @param specialDefense    the special defense stat of the Pokémon
     * @param speed             the speed stat of the Pokémon
     * @param iv_Health         the IV for health
     * @param iv_Attack         the IV for attack
     * @param iv_Defense        the IV for defense
     * @param iv_SpecialAttack  the IV for special attack
     * @param iv_SpecialDefense the IV for special defense
     * @param iv_Speed          the IV for speed
     * @param ev_Health         the EV for health
     * @param ev_Attack         the EV for attack
     * @param ev_Defense        the EV for defense
     * @param ev_SpecialAttack  the EV for special attack
     * @param ev_SpecialDefense the EV for special defense
     * @param ev_Speed          the EV for speed
     * @param moves             the list of moves the Pokémon can use
     * @param obj               the object held by the Pokémon
     * @param nature            the nature of the Pokémon
     */
    public Pokemon(String pokemonName, Types pokemonFirstType, Types pokemonSecondType, String photoPokemon, int levelCap,
                   int health, int attack, int defense, int specialAttack, int specialDefense, int speed,
                   int iv_Health, int iv_Attack, int iv_Defense, int iv_SpecialAttack, int iv_SpecialDefense,
                   int iv_Speed, int ev_Health, int ev_Attack, int ev_Defense, int ev_SpecialAttack,
                   int ev_SpecialDefense, int ev_Speed, List<Move> moves, Obj obj, Nature nature) {
        this.pokemonName = pokemonName;
        this.pokemonFirstType = pokemonFirstType;
        this.pokemonSecondType = pokemonSecondType;
        this.photoPokemon = photoPokemon;
        this.levelCap = levelCap;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
        this.iv_Health = iv_Health;
        this.iv_Attack = iv_Attack;
        this.iv_Defense = iv_Defense;
        this.iv_SpecialAttack = iv_SpecialAttack;
        this.iv_SpecialDefense = iv_SpecialDefense;
        this.iv_Speed = iv_Speed;
        this.ev_Health = ev_Health;
        this.ev_Attack = ev_Attack;
        this.ev_Defense = ev_Defense;
        this.ev_SpecialAttack = ev_SpecialAttack;
        this.ev_SpecialDefense = ev_SpecialDefense;
        this.ev_Speed = ev_Speed;
        this.moves = moves;
        this.obj = obj;
        this.nature = nature;
    }

    /**
     * Default constructor for Pokemon.
     */
    public Pokemon() {
    }

    /**
     * Constructs a Pokemon object with the specified nature.
     *
     * @param nature the nature of the Pokémon
     */
    public Pokemon(Nature nature) {
        this.nature = nature;
    }

    /**
     * Gets the name of the Pokémon.
     *
     * @return the name of the Pokémon
     */
    public String getPokemonName() {
        return pokemonName;
    }

    /**
     * Sets the name of the Pokémon.
     *
     * @param pokemonName the name to set
     */
    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    /**
     * Gets the first type of the Pokémon.
     *
     * @return the first type of the Pokémon
     */
    public Types getPokemonFirstType() {
        return pokemonFirstType;
    }

    /**
     * Sets the first type of the Pokémon.
     *
     * @param pokemonFirstType the first type to set
     */
    public void setPokemonFirstType(Types pokemonFirstType) {
        this.pokemonFirstType = pokemonFirstType;
    }

    /**
     * Gets the second type of the Pokémon.
     *
     * @return the second type of the Pokémon
     */
    public Types getPokemonSecondType() {
        return pokemonSecondType;
    }

    /**
     * Sets the second type of the Pokémon.
     *
     * @param pokemonSecondType the second type to set
     */
    public void setPokemonSecondType(Types pokemonSecondType) {
        this.pokemonSecondType = pokemonSecondType;
    }

    /**
     * Gets the photo of the Pokémon.
     *
     * @return the photo of the Pokémon
     */
    public String getPhotoPokemon() {
        return photoPokemon;
    }

    /**
     * Sets the photo of the Pokémon.
     *
     * @param photoPokemon the photo to set
     */
    public void setPhotoPokemon(String photoPokemon) {
        this.photoPokemon = photoPokemon;
    }

    /**
     * Gets the image of the Pokémon from the file system.
     *
     * @return the image of the Pokémon
     */
    public Image getImage() {
        File file = new File("src/main/resources/com/github/dangelcrack/media/" + getPhotoPokemon());
        return new Image(file.toURI().toString());
    }

    /**
     * Gets the level cap of the Pokémon.
     *
     * @return the level cap of the Pokémon
     */
    public int getLevelCap() {
        return levelCap;
    }

    /**
     * Sets the level cap of the Pokémon.
     *
     * @param levelCap the level cap to set
     */
    public void setLevelCap(int levelCap) {
        this.levelCap = levelCap;
    }

    /**
     * Gets the health stat of the Pokémon.
     *
     * @return the health stat
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health stat of the Pokémon.
     *
     * @param health the health stat to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Gets the attack stat of the Pokémon.
     *
     * @return the attack stat
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Sets the attack stat of the Pokémon.
     *
     * @param attack the attack stat to set
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

    /**
     * Gets the defense stat of the Pokémon.
     *
     * @return the defense stat
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Sets the defense stat of the Pokémon.
     *
     * @param defense the defense stat to set
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }

    /**
     * Gets the special attack stat of the Pokémon.
     *
     * @return the special attack stat
     */
    public int getSpecialAttack() {
        return specialAttack;
    }

    /**
     * Sets the special attack stat of the Pokémon.
     *
     * @param specialAttack the special attack stat to set
     */
    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
    }

    /**
     * Gets the special defense stat of the Pokémon.
     *
     * @return the special defense stat
     */
    public int getSpecialDefense() {
        return specialDefense;
    }

    /**
     * Sets the special defense stat of the Pokémon.
     *
     * @param specialDefense the special defense stat to set
     */
    public void setSpecialDefense(int specialDefense) {
        this.specialDefense = specialDefense;
    }

    /**
     * Gets the speed stat of the Pokémon.
     *
     * @return the speed stat
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets the speed stat of the Pokémon.
     *
     * @param speed the speed stat to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Gets the IV for health.
     *
     * @return the IV for health
     */
    public int getIv_Health() {
        return iv_Health;
    }

    /**
     * Sets the IV for health.
     *
     * @param iv_Health the IV to set
     */
    public void setIv_Health(int iv_Health) {
        this.iv_Health = iv_Health;
    }

    /**
     * Gets the IV for attack.
     *
     * @return the IV for attack
     */
    public int getIv_Attack() {
        return iv_Attack;
    }

    /**
     * Sets the IV for attack.
     *
     * @param iv_Attack the IV to set
     */
    public void setIv_Attack(int iv_Attack) {
        this.iv_Attack = iv_Attack;
    }

    /**
     * Gets the IV for defense.
     *
     * @return the IV for defense
     */
    public int getIv_Defense() {
        return iv_Defense;
    }

    /**
     * Sets the IV for defense.
     *
     * @param iv_Defense the IV to set
     */
    public void setIv_Defense(int iv_Defense) {
        this.iv_Defense = iv_Defense;
    }

    /**
     * Gets the IV for special attack.
     *
     * @return the IV for special attack
     */
    public int getIv_SpecialAttack() {
        return iv_SpecialAttack;
    }

    /**
     * Sets the IV for special attack.
     *
     * @param iv_SpecialAttack the IV to set
     */
    public void setIv_SpecialAttack(int iv_SpecialAttack) {
        this.iv_SpecialAttack = iv_SpecialAttack;
    }

    /**
     * Gets the IV for special defense.
     *
     * @return the IV for special defense
     */
    public int getIv_SpecialDefense() {
        return iv_SpecialDefense;
    }

    /**
     * Sets the IV for special defense.
     *
     * @param iv_SpecialDefense the IV to set
     */
    public void setIv_SpecialDefense(int iv_SpecialDefense) {
        this.iv_SpecialDefense = iv_SpecialDefense;
    }

    /**
     * Gets the IV for speed.
     *
     * @return the IV for speed
     */
    public int getIv_Speed() {
        return iv_Speed;
    }

    /**
     * Sets the IV for speed.
     *
     * @param iv_Speed the IV to set
     */
    public void setIv_Speed(int iv_Speed) {
        this.iv_Speed = iv_Speed;
    }

    /**
     * Gets the EV for health.
     *
     * @return the EV for health
     */
    public int getEv_Health() {
        return ev_Health;
    }

    /**
     * Sets the EV for health.
     *
     * @param ev_Health the EV to set
     */
    public void setEv_Health(int ev_Health) {
        this.ev_Health = ev_Health;
    }

    /**
     * Gets the EV for attack.
     *
     * @return the EV for attack
     */
    public int getEv_Attack() {
        return ev_Attack;
    }

    /**
     * Sets the EV for attack.
     *
     * @param ev_Attack the EV to set
     */
    public void setEv_Attack(int ev_Attack) {
        this.ev_Attack = ev_Attack;
    }

    /**
     * Gets the EV for defense.
     *
     * @return the EV for defense
     */
    public int getEv_Defense() {
        return ev_Defense;
    }

    /**
     * Sets the EV for defense.
     *
     * @param ev_Defense the EV to set
     */
    public void setEv_Defense(int ev_Defense) {
        this.ev_Defense = ev_Defense;
    }

    /**
     * Gets the EV for special attack.
     *
     * @return the EV for special attack
     */
    public int getEv_SpecialAttack() {
        return ev_SpecialAttack;
    }

    /**
     * Sets the EV for special attack.
     *
     * @param ev_SpecialAttack the EV to set
     */
    public void setEv_SpecialAttack(int ev_SpecialAttack) {
        this.ev_SpecialAttack = ev_SpecialAttack;
    }

    /**
     * Gets the EV for special defense.
     *
     * @return the EV for special defense
     */
    public int getEv_SpecialDefense() {
        return ev_SpecialDefense;
    }

    /**
     * Sets the EV for special defense.
     *
     * @param ev_SpecialDefense the EV to set
     */
    public void setEv_SpecialDefense(int ev_SpecialDefense) {
        this.ev_SpecialDefense = ev_SpecialDefense;
    }

    /**
     * Gets the EV for speed.
     *
     * @return the EV for speed
     */
    public int getEv_Speed() {
        return ev_Speed;
    }

    /**
     * Sets the EV for speed.
     *
     * @param ev_Speed the EV to set
     */
    public void setEv_Speed(int ev_Speed) {
        this.ev_Speed = ev_Speed;
    }

    /**
     * Gets a comma-separated string of the names of the Pokémon's moves.
     *
     * @param pokemon the Pokémon
     * @return a string of move names
     */
    public String getMovesNamesString(Pokemon pokemon) {
        List<Move> movesList = pokemon.getMoves();
        return movesList == null ? "" :
                movesList.stream()
                        .map(Move::getNameMove)
                        .collect(Collectors.joining(", "));
    }

    /**
     * Gets the name of the object held by the Pokémon.
     *
     * @param pokemon the Pokémon
     * @return the name of the object
     */
    public String getObjString(Pokemon pokemon) {
        return String.valueOf(pokemon.getObj());
    }

    /**
     * Gets the list of moves of the Pokémon.
     *
     * @return the list of moves
     */
    public List<Move> getMoves() {
        return moves;
    }

    /**
     * Sets the list of moves of the Pokémon.
     *
     * @param moves the list of moves to set
     */
    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    /**
     * Adds a move to the Pokémon's move list if it's not already present.
     *
     * @param move the move to add
     */
    public void addMove(Move move) {
        if (moves == null) {
            moves = new ArrayList<>();
        }
        if (!moves.contains(move)) {
            moves.add(move);
        }
    }

    /**
     * Gets a specific move from the Pokémon's move list.
     *
     * @param move the move to get
     * @return the move, or null if not found
     */
    public Move getMove(Move move) {
        Move result = null;
        if (moves != null) {
            int i = moves.indexOf(move);
            if (i >= 0) {
                result = moves.get(i);
            }
        }
        return result;
    }

    /**
     * Gets the object held by the Pokémon.
     *
     * @return the object
     */
    public Obj getObj() {
        return obj;
    }

    /**
     * Sets the object held by the Pokémon.
     *
     * @param obj the object to set
     */
    public void setObj(Obj obj) {
        this.obj = obj;
    }

    /**
     * Gets the nature of the Pokémon.
     *
     * @return the nature
     */
    public Nature getNature() {
        return nature;
    }

    /**
     * Sets the nature of the Pokémon.
     *
     * @param nature the nature to set
     */
    public void setNature(Nature nature) {
        this.nature = nature;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return Objects.equals(pokemonName, pokemon.pokemonName);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(pokemonName);
    }

    /**
     * Returns a string representation of the Pokémon.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return pokemonName;
    }
}
