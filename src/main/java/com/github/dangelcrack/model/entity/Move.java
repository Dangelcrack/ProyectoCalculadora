package com.github.dangelcrack.model.entity;
import com.github.dangelcrack.view.Category;
import com.github.dangelcrack.view.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Move {
    private String nameMove;
    private Types typeMove;
    private Category category;
    private int power;
    private List<Pokemon> pokemonCanLearn;

    public Move(String nameMove, Types typeMove, Category category, int power, ArrayList<Pokemon> pokemonCanLearn) {
        this.nameMove = nameMove;
        this.typeMove = typeMove;
        this.category = category;
        this.power = power;
        this.pokemonCanLearn = pokemonCanLearn;
    }

    public Move() {
    }

    public String getNameMove() {
        return nameMove;
    }

    public void setNameMove(String nameMove) {
        this.nameMove = nameMove;
    }

    public Types getTypeMove() {
        return typeMove;
    }

    public void setTypeMove(Types typeMove) {
        this.typeMove = typeMove;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public List<Pokemon> getPokemonCanLearn() {
        return pokemonCanLearn;
    }

    public void setPokemonCanLearn(List<Pokemon> pokemonCanLearn) {
        this.pokemonCanLearn = pokemonCanLearn;
    }
    public void addPokemon(Pokemon pokemon){
        if (pokemonCanLearn==null){
            pokemonCanLearn = new ArrayList<>();
        }
        if (!pokemonCanLearn.contains(pokemon)){
            pokemonCanLearn.add(pokemon);
        }
    }
    public void removePokemon(Pokemon pokemon){
        if(pokemonCanLearn!=null){
            pokemonCanLearn.remove(pokemon);
        }
    }
    public Pokemon getMove(Pokemon pokemon){
        Pokemon result = null;
        if(pokemonCanLearn!=null){
            int i = pokemonCanLearn.indexOf(pokemon);
            result = pokemonCanLearn.get(i);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(nameMove, move.nameMove);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameMove);
    }

    @Override
    public String toString() {
        return "Name: '" + nameMove + '\'' +
                ", Type: '" + typeMove + '\'' +
                ", Category: '" + category + '\'' +
                ", Power: " + power;
    }
}
