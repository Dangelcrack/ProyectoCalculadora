package com.github.dangelcrack.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Obj class represents an object in the game that can be held by Pokémon.
 * It includes attributes such as the object's name, description, photo, boosts
 * to various stats, and a list of Pokémon that hold the object.
 */
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

    /**
     * Constructs an Obj object with the specified attributes.
     *
     * @param nameObject     the name of the object
     * @param description    the description of the object
     * @param photoObj       the photo of the object
     * @param boostType      the type boost provided by the object
     * @param boostCategory  the category boost provided by the object
     * @param attackBoost    the attack boost provided by the object
     * @param defenseBoost   the defense boost provided by the object
     * @param spAttackBoost  the special attack boost provided by the object
     * @param spDefenseBoost the special defense boost provided by the object
     * @param speedBoost     the speed boost provided by the object
     * @param pokemonWithObj the list of Pokémon that hold the object
     */
    public Obj(String nameObject, String description, String photoObj, Types boostType, Category boostCategory, int attackBoost, int defenseBoost, int spAttackBoost, int spDefenseBoost, int speedBoost, List<Pokemon> pokemonWithObj) {
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

    /**
     * Default constructor for Obj.
     */
    public Obj() {
    }

    /**
     * Gets the name of the object.
     *
     * @return the name of the object
     */
    public String getNameObject() {
        return nameObject;
    }

    /**
     * Sets the name of the object.
     *
     * @param nameObject the name to set
     */
    public void setNameObject(String nameObject) {
        this.nameObject = nameObject;
    }

    /**
     * Gets the description of the object.
     *
     * @return the description of the object
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the object.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the photo of the object.
     *
     * @return the photo of the object
     */
    public String getPhotoObj() {
        return photoObj;
    }

    /**
     * Sets the photo of the object.
     *
     * @param photoObj the photo to set
     */
    public void setPhotoObj(String photoObj) {
        this.photoObj = photoObj;
    }

    /**
     * Gets the type boost provided by the object.
     *
     * @return the type boost
     */
    public Types getBoostType() {
        return boostType;
    }

    /**
     * Sets the type boost provided by the object.
     *
     * @param boostType the type boost to set
     */
    public void setBoostType(Types boostType) {
        this.boostType = boostType;
    }

    /**
     * Gets the attack boost provided by the object.
     *
     * @return the attack boost
     */
    public int getAttackBoost() {
        return attackBoost;
    }

    /**
     * Sets the attack boost provided by the object.
     *
     * @param attackBoost the attack boost to set
     */
    public void setAttackBoost(int attackBoost) {
        this.attackBoost = attackBoost;
    }

    /**
     * Gets the defense boost provided by the object.
     *
     * @return the defense boost
     */
    public int getDefenseBoost() {
        return defenseBoost;
    }

    /**
     * Sets the defense boost provided by the object.
     *
     * @param defenseBoost the defense boost to set
     */
    public void setDefenseBoost(int defenseBoost) {
        this.defenseBoost = defenseBoost;
    }

    /**
     * Gets the special attack boost provided by the object.
     *
     * @return the special attack boost
     */
    public int getSpAttackBoost() {
        return spAttackBoost;
    }

    /**
     * Sets the special attack boost provided by the object.
     *
     * @param spAttackBoost the special attack boost to set
     */
    public void setSpAttackBoost(int spAttackBoost) {
        this.spAttackBoost = spAttackBoost;
    }

    /**
     * Gets the special defense boost provided by the object.
     *
     * @return the special defense boost
     */
    public int getSpDefenseBoost() {
        return spDefenseBoost;
    }

    /**
     * Sets the special defense boost provided by the object.
     *
     * @param spDefenseBoost the special defense boost to set
     */
    public void setSpDefenseBoost(int spDefenseBoost) {
        this.spDefenseBoost = spDefenseBoost;
    }

    /**
     * Gets the speed boost provided by the object.
     *
     * @return the speed boost
     */
    public int getSpeedBoost() {
        return speedBoost;
    }

    /**
     * Sets the speed boost provided by the object.
     *
     * @param speedBoost the speed boost to set
     */
    public void setSpeedBoost(int speedBoost) {
        this.speedBoost = speedBoost;
    }

    /**
     * Gets the category boost provided by the object.
     *
     * @return the category boost
     */
    public Category getBoostCategory() {
        return boostCategory;
    }

    /**
     * Sets the category boost provided by the object.
     *
     * @param boostCategory the category boost to set
     */
    public void setBoostCategory(Category boostCategory) {
        this.boostCategory = boostCategory;
    }

    /**
     * Gets the list of Pokémon that hold the object.
     * If the list is null, it initializes it to an empty list.
     *
     * @return the list of Pokémon that hold the object
     */
    public List<Pokemon> getPokemonWithObj() {
        if (pokemonWithObj == null) {
            return new ArrayList<>();
        }
        return pokemonWithObj;
    }

    /**
     * Sets the list of Pokémon that hold the object.
     *
     * @param pokemonWithObj the list of Pokémon to set
     */
    public void setPokemonWithObj(List<Pokemon> pokemonWithObj) {
        this.pokemonWithObj = pokemonWithObj;
    }

    /**
     * Checks if this Obj is equal to another object.
     * Two objects are considered equal if they have the same name.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obj obj = (Obj) o;
        return Objects.equals(nameObject, obj.nameObject);
    }

    /**
     * Returns the hash code for this Obj.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(nameObject);
    }

    /**
     * Returns a string representation of the Obj.
     *
     * @return a string representation of the Obj
     */
    @Override
    public String toString() {
        return nameObject;
    }
}
