package com.github.dangelcrack.view;

public enum Types {
    NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE, FIGHTING, POISON, GROUND, FLYING, PSYCHIC, BUG, ROCK, GHOST, DARK, DRAGON, STEEL, FAIRY;

    public boolean esSuperEfectivoContra(Types tipo) {
        switch (this) {
            case FIRE:
                return tipo == GRASS || tipo == ICE || tipo == BUG || tipo == STEEL;
            case WATER:
                return tipo == FIRE || tipo == GROUND || tipo == ROCK;
            case ELECTRIC:
                return tipo == WATER || tipo == FLYING;
            case GRASS:
                return tipo == WATER || tipo == GROUND || tipo == ROCK;
            case ICE:
                return tipo == GRASS || tipo == GROUND || tipo == FLYING || tipo == DRAGON;
            case FIGHTING:
                return tipo == NORMAL || tipo == ICE || tipo == ROCK || tipo == DARK || tipo == STEEL;
            case POISON:
                return tipo == GRASS || tipo == FAIRY;
            case GROUND:
                return tipo == FIRE || tipo == ELECTRIC || tipo == POISON || tipo == ROCK || tipo == STEEL;
            case FLYING:
                return tipo == GRASS || tipo == FIGHTING || tipo == BUG;
            case PSYCHIC:
                return tipo == FIGHTING || tipo == POISON;
            case BUG:
                return tipo == GRASS || tipo == PSYCHIC || tipo == DARK;
            case ROCK:
                return tipo == FIRE || tipo == ICE || tipo == FLYING || tipo == BUG;
            case GHOST:
                return tipo == PSYCHIC || tipo == GHOST;
            case DRAGON:
                return tipo == DRAGON;
            case DARK:
                return tipo == PSYCHIC || tipo == GHOST;
            case STEEL:
                return tipo == ICE || tipo == ROCK || tipo == FAIRY;
            case FAIRY:
                return tipo == FIGHTING || tipo == DRAGON || tipo == DARK;
            default:
                return false;
        }
    }

    public boolean esNoEfectivoContra(Types tipo) {
        switch (this) {
            case FIRE:
                return tipo == FIRE || tipo == WATER || tipo == ROCK || tipo == DRAGON;
            case WATER:
                return tipo == WATER || tipo == GRASS || tipo == DRAGON;
            case ELECTRIC:
                return tipo == ELECTRIC || tipo == GRASS || tipo == DRAGON || tipo == GROUND;
            case GRASS:
                return tipo == FIRE || tipo == GRASS || tipo == POISON || tipo == FLYING || tipo == BUG || tipo == DRAGON || tipo == STEEL;
            case ICE:
                return tipo == FIRE || tipo == WATER || tipo == ICE || tipo == STEEL;
            case FIGHTING:
                return tipo == POISON || tipo == FLYING || tipo == PSYCHIC || tipo == BUG || tipo == FAIRY;
            case POISON:
                return tipo == POISON || tipo == GROUND || tipo == ROCK || tipo == GHOST;
            case GROUND:
                return tipo == GRASS || tipo == BUG;
            case FLYING:
                return tipo == ELECTRIC || tipo == ROCK || tipo == STEEL;
            case PSYCHIC:
                return tipo == PSYCHIC || tipo == STEEL;
            case BUG:
                return tipo == FIRE || tipo == FIGHTING || tipo == POISON || tipo == FLYING || tipo == GHOST || tipo == STEEL || tipo == FAIRY;
            case ROCK:
                return tipo == FIGHTING || tipo == GROUND || tipo == STEEL;
            case GHOST:
                return tipo == DARK;
            case DRAGON:
                return tipo == STEEL;
            case DARK:
                return tipo == FIGHTING || tipo == DARK || tipo == FAIRY;
            case STEEL:
                return tipo == FIRE || tipo == WATER || tipo == ELECTRIC || tipo == STEEL;
            case FAIRY:
                return tipo == FIRE || tipo == POISON || tipo == STEEL;
            default:
                return false;
        }
    }
    public boolean esInmuneContra(Types tipo) {
        switch (this) {
            case NORMAL:
                return tipo == GHOST;
            case GROUND:
                return tipo == FLYING;
            case GHOST:
                return tipo == NORMAL || tipo == FIGHTING;
            case DARK:
                return tipo == PSYCHIC;
            case FAIRY:
                return tipo == DRAGON;
            default:
                return false;
        }
    }
}
