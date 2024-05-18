package com.github.dangelcrack.test;

import com.github.dangelcrack.model.dao.PokemonDAO;
import com.github.dangelcrack.model.entity.Pokemon;
import java.util.List;

public class testnature {
    public static void main(String[] args) {
        List<Pokemon> pokemon = PokemonDAO.build().findAll();
        for(Pokemon pokemon1 : pokemon){
            System.out.println("Nature: " + pokemon1.getNature());
            System.out.println("Attack Multiplier: " + pokemon1.getNature().getAttackMultiplier());
        }
    }
}
