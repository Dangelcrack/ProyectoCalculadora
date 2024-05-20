package com.github.dangelcrack.model.entity;
/**
 * The Category enum defines the different categories of Pokémon moves.
 * Moves can be categorized as Special, Physical, or State.
 */
public enum Category {
    /** Special category for moves that rely on the Pokémon's Special Attack stat. */
    SPECIAL,
    /** Physical category for moves that rely on the Pokémon's Attack stat. */
    PHYSICAL,
    /** State category for moves that do not cause direct damage but affect the state of the battle. */
    STATE
}