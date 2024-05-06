package com.github.dangelcrack.model.entity;


import com.github.dangelcrack.view.PokemonType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Pokemon {
    private String pokemonName;
    private PokemonType pokemonFirstType;
    private PokemonType pokemonSecondType;
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

    public Pokemon(String pokemonName, PokemonType pokemonFirstType, PokemonType pokemonSecondType, String photoPokemon, int levelCap,
                   int health, int attack, int defense, int specialAttack, int specialDefense, int speed,
                   int iv_Health, int iv_Attack, int iv_Defense, int iv_SpecialAttack, int iv_SpecialDefense,
                   int iv_Speed, int ev_Health, int ev_Attack, int ev_Defense, int ev_SpecialAttack,
                   int ev_SpecialDefense, int ev_Speed, List<Move> moves) {
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
        this.moves=moves;
    }

    public Pokemon() {
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public PokemonType getPokemonFirstType() {
        return pokemonFirstType;
    }

    public void setPokemonFirstType(PokemonType pokemonFirstType) {
        this.pokemonFirstType = pokemonFirstType;
    }

    public PokemonType getPokemonSecondType() {
        return pokemonSecondType;
    }

    public void setPokemonSecondType(PokemonType pokemonSecondType) {
        this.pokemonSecondType = pokemonSecondType;
    }

    public String getPhotoPokemon() {
        return photoPokemon;
    }

    public void setPhotoPokemon(String photoPokemon) {
        this.photoPokemon = photoPokemon;
    }

    public int getLevelCap() {
        return levelCap;
    }

    public void setLevelCap(int levelCap) {
        this.levelCap = levelCap;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(int specialDefense) {
        this.specialDefense = specialDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getIv_Health() {
        return iv_Health;
    }

    public void setIv_Health(int iv_Health) {
        this.iv_Health = iv_Health;
    }

    public int getIv_Attack() {
        return iv_Attack;
    }

    public void setIv_Attack(int iv_Attack) {
        this.iv_Attack = iv_Attack;
    }

    public int getIv_Defense() {
        return iv_Defense;
    }

    public void setIv_Defense(int iv_Defense) {
        this.iv_Defense = iv_Defense;
    }

    public int getIv_SpecialAttack() {
        return iv_SpecialAttack;
    }

    public void setIv_SpecialAttack(int iv_SpecialAttack) {
        this.iv_SpecialAttack = iv_SpecialAttack;
    }

    public int getIv_SpecialDefense() {
        return iv_SpecialDefense;
    }

    public void setIv_SpecialDefense(int iv_SpecialDefense) {
        this.iv_SpecialDefense = iv_SpecialDefense;
    }

    public int getIv_Speed() {
        return iv_Speed;
    }

    public void setIv_Speed(int iv_Speed) {
        this.iv_Speed = iv_Speed;
    }

    public int getEv_Health() {
        return ev_Health;
    }

    public void setEv_Health(int ev_Health) {
        this.ev_Health = ev_Health;
    }

    public int getEv_Attack() {
        return ev_Attack;
    }

    public void setEv_Attack(int ev_Attack) {
        this.ev_Attack = ev_Attack;
    }

    public int getEv_Defense() {
        return ev_Defense;
    }

    public void setEv_Defense(int ev_Defense) {
        this.ev_Defense = ev_Defense;
    }

    public int getEv_SpecialAttack() {
        return ev_SpecialAttack;
    }

    public void setEv_SpecialAttack(int ev_SpecialAttack) {
        this.ev_SpecialAttack = ev_SpecialAttack;
    }

    public int getEv_SpecialDefense() {
        return ev_SpecialDefense;
    }

    public void setEv_SpecialDefense(int ev_SpecialDefense) {
        this.ev_SpecialDefense = ev_SpecialDefense;
    }

    public int getEv_Speed() {
        return ev_Speed;
    }

    public void setEv_Speed(int ev_Speed) {
        this.ev_Speed = ev_Speed;
    }
    public String getMovesNamesString(Pokemon pokemon) {
        List<Move> movesList = pokemon.getMoves();
        return movesList == null ? "" :
                movesList.stream()
                        .map(Move::getNameMove)
                        .collect(Collectors.joining(", "));
    }

    public List<Move> getMoves(){
        return moves;
    }
    public void setMoves(List<Move> moves){ this.moves = moves;}
    public void addMove(Move move){
        if(moves==null){
            moves = new ArrayList<>();
        }
        if(!moves.contains(move)){
            moves.add(move);
        }
    }
    public Move getMove(Move move){
        Move result = null;
        if(moves!=null){
            int i = moves.indexOf(move);
            result = moves.get(i);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return Objects.equals(pokemonName, pokemon.pokemonName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pokemonName);
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "\n   Name: '" + pokemonName + '\'' +
                "\n   Type: " + pokemonFirstType + (pokemonSecondType != null ? "/" + pokemonSecondType : "") +
                "\n   Photo: '" + photoPokemon + '\'' +
                "\n   Level Cap: " + levelCap +
                "\n   Stats: {" +
                "\n      Health: " + health +
                "\n      Attack: " + attack +
                "\n      Defense: " + defense +
                "\n      Special Attack: " + specialAttack +
                "\n      Special Defense: " + specialDefense +
                "\n      Speed: " + speed +
                "\n   }" +
                "\n   IVs: {" +
                "\n      Health: " + iv_Health +
                "\n      Attack: " + iv_Attack +
                "\n      Defense: " + iv_Defense +
                "\n      Special Attack: " + iv_SpecialAttack +
                "\n      Special Defense: " + iv_SpecialDefense +
                "\n      Speed: " + iv_Speed +
                "\n   }" +
                "\n   EVs: {" +
                "\n      Health: " + ev_Health +
                "\n      Attack: " + ev_Attack +
                "\n      Defense: " + ev_Defense +
                "\n      Special Attack: " + ev_SpecialAttack +
                "\n      Special Defense: " + ev_SpecialDefense +
                "\n      Speed: " + ev_Speed +
                "\n   }";
    }
}
