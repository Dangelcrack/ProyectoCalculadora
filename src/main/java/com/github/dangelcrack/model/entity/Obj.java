package com.github.dangelcrack.model.entity;

import com.github.dangelcrack.view.Category;
import com.github.dangelcrack.view.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Obj {
    private String nameObject;
    private String description;
    private String photoObj;
    private Types boostType;
    private Category boostCategory;
    private int attackBoost;
    private int defenseBoost;
    private int spAttackBoost;
    private int spDefenseBoost;
    private int speedBoost;
    private List<Pokemon> pokemonWithObj;

    public Obj(String nameObject, String description, String photoObj, Types boostType, Category boostCategory, int attackBoost, int defenseBoost, int spAttackBoost, int spDefenseBoost, int speedBoost,List<Pokemon> pokemonWithObj) {
        this.nameObject = nameObject;
        this.description = description;
        this.photoObj = photoObj;
        this.boostType = boostType;
        this.boostCategory = boostCategory;
        this.attackBoost = attackBoost;
        this.defenseBoost = defenseBoost;
        this.spAttackBoost = spAttackBoost;
        this.spDefenseBoost = spDefenseBoost;
        this.speedBoost = speedBoost;
        this.pokemonWithObj = pokemonWithObj;
    }

    public Obj() {
    }

    public String getNameObject() {
        return nameObject;
    }

    public void setNameObject(String nameObject) {
        this.nameObject = nameObject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoObj() {
        return photoObj;
    }

    public void setPhotoObj(String photoObj) {
        this.photoObj = photoObj;
    }

    public Types getBoostType() {
        return boostType;
    }

    public void setBoostType(Types boostType) {
        this.boostType = boostType;
    }

    public int getAttackBoost() {
        return attackBoost;
    }

    public void setAttackBoost(int attackBoost) {
        this.attackBoost = attackBoost;
    }

    public int getDefenseBoost() {
        return defenseBoost;
    }

    public void setDefenseBoost(int defenseBoost) {
        this.defenseBoost = defenseBoost;
    }

    public int getSpAttackBoost() {
        return spAttackBoost;
    }

    public void setSpAttackBoost(int spAttackBoost) {
        this.spAttackBoost = spAttackBoost;
    }

    public int getSpDefenseBoost() {
        return spDefenseBoost;
    }

    public void setSpDefenseBoost(int spDefenseBoost) {
        this.spDefenseBoost = spDefenseBoost;
    }

    public int getSpeedBoost() {
        return speedBoost;
    }

    public void setSpeedBoost(int speedBoost) {
        this.speedBoost = speedBoost;
    }

    public Category getBoostCategory() {
        return boostCategory;
    }

    public void setBoostCategory(Category boostCategory) {
        this.boostCategory = boostCategory;
    }

    public List<Pokemon> getPokemonWithObj() {
        if (pokemonWithObj == null) {
            return new ArrayList<>();
        }
        return pokemonWithObj;
    }

    public void setPokemonWithObj(List<Pokemon> pokemonWithObj) {
        this.pokemonWithObj = pokemonWithObj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obj obj = (Obj) o;
        return Objects.equals(nameObject, obj.nameObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameObject);
    }

    @Override
    public String toString() {
        return nameObject;
    }
}
