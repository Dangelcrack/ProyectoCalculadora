package com.github.dangelcrack.view;

public enum Nature {
    HARDY, LONELY, BRAVE, ADAMANT, NAUGHTY, BOLD, DOCILE, RELAXED, IMPISH, LAX, TIMID, HASTY, SERIOUS, JOLLY, NAIVE,
    MODEST, MILD, QUIET, BASHFUL, RASH, CALM, GENTLE, SASSY, CAREFUL, QUIRKY;
    public double getAttackMultiplier() {
        switch (this) {
            case LONELY: // Aumenta Ataque, disminuye Defensa
            case BRAVE:  // Aumenta Ataque, disminuye Velocidad
            case ADAMANT: // Aumenta Ataque, disminuye Ataque Especial
            case NAUGHTY: // Aumenta Ataque, disminuye Defensa Especial
                return 1.1;
            case BOLD: // Aumenta Defensa, disminuye Ataque
            case TIMID: // Aumenta Velocidad, disminuye Ataque
            case MODEST: // Aumenta Ataque Especial, disminuye Ataque
            case CALM: // Aumenta Defensa Especial, disminuye Ataque
                return 0.9;
            default: // Naturalezas neutrales
                return 1.0;
        }
    }
    public double getDefenseMultiplier() {
        switch (this) {
            case BOLD: // Aumenta Defensa, disminuye Ataque
            case IMPISH: // Aumenta Defensa, disminuye Ataque Especial
            case LAX: // Aumenta Defensa, disminuye Defensa Especial
            case RELAXED: // Aumenta Defensa, disminuye Velocidad
                return 1.1;
            case LONELY: // Aumenta Ataque, disminuye Defensa
            case HASTY: // Aumenta Velocidad, disminuye Defensa
            case MILD: // Aumenta Ataque Especial, disminuye Defensa
            case GENTLE: // Aumenta Defensa Especial, disminuye Defensa
                return 0.9;
            default: // Naturalezas neutrales
                return 1.0;
        }
    }
    public double getSpAttackMultiplier() {
        switch (this) {
            case MODEST: // Aumenta Ataque Especial, disminuye Ataque
            case MILD: // Aumenta Ataque Especial, disminuye Defensa
            case QUIET: // Aumenta Ataque Especial, disminuye Velocidad
            case RASH: // Aumenta Ataque Especial, disminuye Defensa Especial
                return 1.1;
            case ADAMANT: // Aumenta Ataque, disminuye Ataque Especial
            case IMPISH: // Aumenta Defensa, disminuye Ataque Especial
            case JOLLY: // Aumenta Velocidad, disminuye Ataque Especial
            case CAREFUL: // Aumenta Defensa Especial, disminuye Ataque Especial
                return 0.9;
            default: // Naturalezas neutrales
                return 1.0;
        }
    }

    public double getSpDefenseMultiplier() {
        switch (this) {
            case CALM: // Aumenta Defensa Especial, disminuye Ataque
            case GENTLE: // Aumenta Defensa Especial, disminuye Defensa
            case SASSY: // Aumenta Defensa Especial, disminuye Velocidad
            case CAREFUL: // Aumenta Defensa Especial, disminuye Ataque Especial
                return 1.1;
            case NAUGHTY: // Aumenta Ataque, disminuye Defensa Especial
            case LAX: // Aumenta Defensa, disminuye Defensa Especial
            case NAIVE: // Aumenta Velocidad, disminuye Defensa Especial
            case RASH: // Aumenta Ataque Especial, disminuye Defensa Especial
                return 0.9;
            default: // Naturalezas neutrales
                return 1.0;
        }
    }
    public double getSpeedMultiplier() {
        switch (this) {
            case TIMID: // Aumenta Velocidad, disminuye Ataque
            case HASTY: // Aumenta Velocidad, disminuye Defensa
            case JOLLY: // Aumenta Velocidad, disminuye Ataque Especial
            case NAIVE: // Aumenta Velocidad, disminuye Defensa Especial
                return 1.1;
            case BRAVE: // Aumenta Ataque, disminuye Velocidad
            case RELAXED: // Aumenta Defensa, disminuye Velocidad
            case QUIET: // Aumenta Ataque Especial, disminuye Velocidad
            case SASSY: // Aumenta Defensa Especial, disminuye Velocidad
                return 0.9;
            default: // Naturalezas neutrales
                return 1.0;
        }
    }
}