package com.github.dangelcrack.test;

import com.github.dangelcrack.model.dao.PokemonDAO;
import com.github.dangelcrack.model.entity.Pokemon;
import java.util.List;

/**
 * This class demonstrates fetching a list of Pokemon from the database
 * and printing each Pokemon's nature and corresponding attack multiplier to the console.
 */
public class testnature {
    public static void main(String[] args) {
        // Fetch all Pokemon from the database using the PokemonDAO
        List<Pokemon> pokemon = PokemonDAO.build().findAll();

        // Iterate through the list of Pokemon and print their nature and attack multiplier
        for (Pokemon pokemon1 : pokemon) {
            System.out.println("Nature: " + pokemon1.getNature());
            System.out.println("Attack Multiplier: " + pokemon1.getNature().getAttackMultiplier());
        }
    }
}
