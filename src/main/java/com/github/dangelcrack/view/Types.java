package com.github.dangelcrack.view;
/**
 * Enum representing different types of creatures in the game.
 * Each type has unique interactions with other types, such as super effectiveness, not effectiveness, and immunity.
 */
public enum Types {
    NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE, FIGHTING, POISON, GROUND, FLYING, PSYCHIC, BUG, ROCK, GHOST, DARK, DRAGON, STEEL, FAIRY;
    /**
     * Checks if the current type is super effective against another type.
     * @param tipo The type to compare against.
     * @return true if super effective, false otherwise.
     */
    public boolean isSuperEffectiveAgainst(Types tipo) {
        boolean result = false;
        switch (this) {
            case FIRE:
                result = tipo == GRASS || tipo == ICE || tipo == BUG || tipo == STEEL;
                break;
            case WATER:
                result = tipo == FIRE || tipo == GROUND || tipo == ROCK;
                break;
            case ELECTRIC:
                result = tipo == WATER || tipo == FLYING;
                break;
            case GRASS:
                result = tipo == WATER || tipo == GROUND || tipo == ROCK;
                break;
            case ICE:
                result = tipo == GRASS || tipo == GROUND || tipo == FLYING || tipo == DRAGON;
                break;
            case FIGHTING:
                result = tipo == NORMAL || tipo == ICE || tipo == ROCK || tipo == DARK || tipo == STEEL;
                break;
            case POISON:
                result = tipo == GRASS || tipo == FAIRY;
                break;
            case GROUND:
                result = tipo == FIRE || tipo == ELECTRIC || tipo == POISON || tipo == ROCK || tipo == STEEL;
                break;
            case FLYING:
                result = tipo == GRASS || tipo == FIGHTING || tipo == BUG;
                break;
            case PSYCHIC:
                result = tipo == FIGHTING || tipo == POISON;
                break;
            case BUG:
                result = tipo == GRASS || tipo == PSYCHIC || tipo == DARK;
                break;
            case ROCK:
                result = tipo == FIRE || tipo == ICE || tipo == FLYING || tipo == BUG;
                break;
            case GHOST:
                result = tipo == PSYCHIC || tipo == GHOST;
                break;
            case DRAGON:
                result = tipo == DRAGON;
                break;
            case DARK:
                result = tipo == PSYCHIC || tipo == GHOST;
                break;
            case STEEL:
                result = tipo == ICE || tipo == ROCK || tipo == FAIRY;
                break;
            case FAIRY:
                result = tipo == FIGHTING || tipo == DRAGON || tipo == DARK;
                break;
            default:
                result = false;
                break;
        }

        return result;
    }

    /**
     * Checks if the current type is not very effective against another type.
     * @param tipo The type to compare against.
     * @return true if not effective, false otherwise.
     */
    public boolean isNotEffectiveAgainst(Types tipo) {
        boolean result = false;

        switch (this) {
            case FIRE:
                result = tipo == FIRE || tipo == WATER || tipo == ROCK || tipo == DRAGON;
                break;
            case WATER:
                result = tipo == WATER || tipo == GRASS || tipo == DRAGON;
                break;
            case ELECTRIC:
                result = tipo == ELECTRIC || tipo == GRASS || tipo == DRAGON || tipo == GROUND;
                break;
            case GRASS:
                result = tipo == FIRE || tipo == GRASS || tipo == POISON || tipo == FLYING || tipo == BUG || tipo == DRAGON || tipo == STEEL;
                break;
            case ICE:
                result = tipo == FIRE || tipo == WATER || tipo == ICE || tipo == STEEL;
                break;
            case FIGHTING:
                result = tipo == POISON || tipo == FLYING || tipo == PSYCHIC || tipo == BUG || tipo == FAIRY;
                break;
            case POISON:
                result = tipo == POISON || tipo == GROUND || tipo == ROCK || tipo == GHOST;
                break;
            case GROUND:
                result = tipo == GRASS || tipo == BUG;
                break;
            case FLYING:
                result = tipo == ELECTRIC || tipo == ROCK || tipo == STEEL;
                break;
            case PSYCHIC:
                result = tipo == PSYCHIC || tipo == STEEL;
                break;
            case BUG:
                result = tipo == FIRE || tipo == FIGHTING || tipo == POISON || tipo == FLYING || tipo == GHOST || tipo == STEEL || tipo == FAIRY;
                break;
            case ROCK:
                result = tipo == FIGHTING || tipo == GROUND || tipo == STEEL;
                break;
            case GHOST:
                result = tipo == DARK;
                break;
            case DRAGON:
                result = tipo == STEEL;
                break;
            case DARK:
                result = tipo == FIGHTING || tipo == DARK || tipo == FAIRY;
                break;
            case STEEL:
                result = tipo == FIRE || tipo == WATER || tipo == ELECTRIC || tipo == STEEL;
                break;
            case FAIRY:
                result = tipo == FIRE || tipo == POISON || tipo == STEEL;
                break;
            default:
                result = false;
                break;
        }

        return result;
    }
    /**
     * Checks if the current type is immune against another type.
     * @param tipo The type to compare against.
     * @return true if immune, false otherwise.
     */
    public boolean isImmuneAgainst(Types tipo) {
        boolean result = false;

        switch (this) {
            case NORMAL:
                result = tipo == GHOST;
                break;
            case GROUND:
                result = tipo == FLYING;
                break;
            case GHOST:
                result = tipo == NORMAL || tipo == FIGHTING;
                break;
            case DARK:
                result = tipo == PSYCHIC;
                break;
            case FAIRY:
                result = tipo == DRAGON;
                break;
            default:
                result = false;
                break;
        }

        return result;
    }

}
