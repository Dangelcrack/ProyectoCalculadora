package com.github.dangelcrack.model.entity;

import com.github.dangelcrack.view.Category;
import com.github.dangelcrack.view.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Move class represents a move that a Pokémon can learn in the game.
 * It includes attributes such as the move's name, type, category, power,
 * and a list of Pokémon that can learn the move.
 */
public class Move {
    private String nameMove;
    private Types typeMove;
    private Category category;
    private int power;
    private List<Pokemon> pokemonCanLearn;

    /**
     * Constructs a Move object with the specified attributes.
     *
     * @param nameMove       the name of the move
     * @param typeMove       the type of the move
     * @param category       the category of the move
     * @param power          the power of the move
     * @param pokemonCanLearn the list of Pokémon that can learn the move
     */
    public Move(String nameMove, Types typeMove, Category category, int power, List<Pokemon> pokemonCanLearn) {
        this.nameMove = nameMove;
        this.typeMove = typeMove;
        this.category = category;
        this.power = power;
        this.pokemonCanLearn = pokemonCanLearn;
    }

    /**
     * Default constructor for Move.
     */
    public Move() {
    }

    /**
     * Gets the name of the move.
     *
     * @return the name of the move
     */
    public String getNameMove() {
        return nameMove;
    }

    /**
     * Sets the name of the move.
     *
     * @param nameMove the name to set
     */
    public void setNameMove(String nameMove) {
        this.nameMove = nameMove;
    }

    /**
     * Gets the type of the move.
     *
     * @return the type of the move
     */
    public Types getTypeMove() {
        return typeMove;
    }

    /**
     * Sets the type of the move.
     *
     * @param typeMove the type to set
     */
    public void setTypeMove(Types typeMove) {
        this.typeMove = typeMove;
    }

    /**
     * Gets the category of the move.
     *
     * @return the category of the move
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the category of the move.
     *
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Gets the power of the move.
     *
     * @return the power of the move
     */
    public int getPower() {
        return power;
    }

    /**
     * Sets the power of the move.
     *
     * @param power the power to set
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * Gets the list of Pokémon that can learn the move.
     *
     * @return the list of Pokémon that can learn the move
     */
    public List<Pokemon> getPokemonCanLearn() {
        return pokemonCanLearn;
    }

    /**
     * Sets the list of Pokémon that can learn the move.
     *
     * @param pokemonCanLearn the list of Pokémon to set
     */
    public void setPokemonCanLearn(List<Pokemon> pokemonCanLearn) {
        this.pokemonCanLearn = pokemonCanLearn;
    }

    /**
     * Adds a Pokémon to the list of Pokémon that can learn the move.
     * If the list is null, it initializes the list first.
     *
     * @param pokemon the Pokémon to add
     */
    public void addPokemon(Pokemon pokemon) {
        if (pokemonCanLearn == null) {
            pokemonCanLearn = new ArrayList<>();
        }
        if (!pokemonCanLearn.contains(pokemon)) {
            pokemonCanLearn.add(pokemon);
        }
    }

    /**
     * Removes a Pokémon from the list of Pokémon that can learn the move.
     *
     * @param pokemon the Pokémon to remove
     */
    public void removePokemon(Pokemon pokemon) {
        if (pokemonCanLearn != null) {
            pokemonCanLearn.remove(pokemon);
        }
    }

    /**
     * Retrieves a Pokémon from the list by its position.
     *
     * @param pokemon the Pokémon to get
     * @return the Pokémon if found, null otherwise
     */
    public Pokemon getMove(Pokemon pokemon) {
        Pokemon result = null;
        if (pokemonCanLearn != null) {
            int i = pokemonCanLearn.indexOf(pokemon);
            result = pokemonCanLearn.get(i);
        }
        return result;
    }

    /**
     * Checks if this Move is equal to another object.
     * Two moves are considered equal if they have the same name.
     *
     * @param o the object to compare with
     * @return true if the moves are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(nameMove, move.nameMove);
    }

    /**
     * Returns the hash code for this Move.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(nameMove);
    }

    /**
     * Returns a string representation of the Move.
     *
     * @return a string representation of the Move
     */
    @Override
    public String toString() {
        return "Name: '" + nameMove + '\'' +
                ", Type: '" + typeMove + '\'' +
                ", Category: '" + category + '\'' +
                ", Power: " + power;
    }
}