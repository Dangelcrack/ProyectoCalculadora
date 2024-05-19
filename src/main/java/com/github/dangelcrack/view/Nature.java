package com.github.dangelcrack.view;

/**
 * Enum representing different natures of Pokémon.
 * Each nature has multipliers for different stats.
 */
public enum Nature {
    HARDY, LONELY, BRAVE, ADAMANT, NAUGHTY, BOLD, DOCILE, RELAXED, IMPISH, LAX, TIMID, HASTY, SERIOUS, JOLLY, NAIVE,
    MODEST, MILD, QUIET, BASHFUL, RASH, CALM, GENTLE, SASSY, CAREFUL, QUIRKY;

    /**
     * Returns the attack multiplier for the nature.
     *
     * @return The attack multiplier.
     */
    public double getAttackMultiplier() {
        switch (this) {
            case LONELY:
            case BRAVE:
            case ADAMANT:
            case NAUGHTY:
                return 1.1;
            case BOLD:
            case TIMID:
            case MODEST:
            case CALM:
                return 0.9;
            default:
                return 1.0;
        }
    }

    /**
     * Returns the defense multiplier for the nature.
     *
     * @return The defense multiplier.
     */
    public double getDefenseMultiplier() {
        switch (this) {
            case BOLD:
            case IMPISH:
            case LAX:
            case RELAXED:
                return 1.1;
            case LONELY:
            case HASTY:
            case MILD:
            case GENTLE:
                return 0.9;
            default:
                return 1.0;
        }
    }

    /**
     * Returns the special attack multiplier for the nature.
     *
     * @return The special attack multiplier.
     */
    public double getSpAttackMultiplier() {
        switch (this) {
            case MODEST:
            case MILD:
            case QUIET:
            case RASH:
                return 1.1;
            case ADAMANT:
            case IMPISH:
            case JOLLY:
            case CAREFUL:
                return 0.9;
            default:
                return 1.0;
        }
    }

    /**
     * Returns the special defense multiplier for the nature.
     *
     * @return The special defense multiplier.
     */
    public double getSpDefenseMultiplier() {
        switch (this) {
            case CALM:
            case GENTLE:
            case SASSY:
            case CAREFUL:
                return 1.1;
            case NAUGHTY:
            case LAX:
            case NAIVE:
            case RASH:
                return 0.9;
            default:
                return 1.0;
        }
    }

    /**
     * Returns the speed multiplier for the nature.
     *
     * @return The speed multiplier.
     */
    public double getSpeedMultiplier() {
        switch (this) {
            case TIMID:
            case HASTY:
            case JOLLY:
            case NAIVE:
                return 1.1;
            case BRAVE:
            case RELAXED:
            case QUIET:
            case SASSY:
                return 0.9;
            default:
                return 1.0;
        }
    }
}
