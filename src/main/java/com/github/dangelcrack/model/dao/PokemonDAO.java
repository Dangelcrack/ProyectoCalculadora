package com.github.dangelcrack.model.dao;

import com.github.dangelcrack.model.connection.ConnectionMariaDB;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Pokemon;
import com.github.dangelcrack.view.PokemonType;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAO implements DAO<Pokemon,String> {
    private static final String INSERT = "INSERT INTO Pokemon (PokemonName, FirstType, SecondType, Photo, LEVELCAP, HP, Attack, Defense, SpAttack, SpDefense, Speed, Iv_HP, Iv_Attack, Iv_Defense, Iv_SpAttack, Iv_SpDefense, Iv_Speed, Ev_HP, Ev_Attack, Ev_Defense, Ev_SpAttack, Ev_SpDefense, Ev_Speed) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE="UPDATE Pokemon\n" +
            "SET PokemonName = ?, FirstType = ?, SecondType = ?, Photo = ?, LEVELCAP = ?, HP = ?, Attack = ?, Defense = ?, SpAttack = ?, SpDefense = ?, Speed = ?, Iv_HP = ?, Iv_Attack = ?, Iv_Defense = ?, Iv_SpAttack = ?, Iv_SpDefense = ?, Iv_Speed = ?, Ev_HP = ?, Ev_Attack = ?, Ev_Defense = ?, Ev_SpAttack = ?, Ev_SpDefense = ?, Ev_Speed = ?" +
            "WHERE PokemonName = ?";
    private static final String FINDALL = "SELECT p.PokemonName, p.FirstType, p.SecondType, p.Photo, p.LEVELCAP, p.HP, p.Attack, p.Defense, p.SpAttack, p.SpDefense, p.Speed, p.Iv_HP, p.Iv_Attack, p.Iv_Defense, p.Iv_SpAttack, p.Iv_SpDefense, p.Iv_Speed, p.Ev_HP, p.Ev_Attack, p.Ev_Defense, p.Ev_SpAttack, p.Ev_SpDefense, p.Ev_Speed FROM Pokemon p";

    private static final String FINDBYNAME="SELECT p.PokemonName, p.FirstType, p.SecondType FROM Pokemon AS p WHERE p.PokemonName=?";
    private static final String DELETE="DELETE FROM Pokemon AS p WHERE p.PokemonName=?";
    @Override
    public Pokemon save(Pokemon p) {
        Pokemon result = p;
        if (p == null || p.getPokemonName() == null) return result;

        try {
            Pokemon existingPokemon = findByName(p.getPokemonName());
            if (existingPokemon == null) {
                // INSERT
                try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                    PreparedStatement pstMoves = ConnectionMariaDB.getConnection().prepareStatement("INSERT INTO PokemonMoves (PokemonName, MoveName) VALUES (?, ?)");
                    pst.setString(1, p.getPokemonName());
                    pst.setString(2, p.getPokemonFirstType().toString());
                    PokemonType secondType = p.getPokemonSecondType();
                    pst.setString(3, (secondType != null) ? secondType.toString() : null);
                    pst.setString(4, p.getPhotoPokemon());
                    pst.setInt(5, p.getLevelCap());
                    pst.setInt(6, p.getHealth());
                    pst.setInt(7, p.getAttack());
                    pst.setInt(8, p.getDefense());
                    pst.setInt(9, p.getSpecialAttack());
                    pst.setInt(10, p.getSpecialDefense());
                    pst.setInt(11, p.getSpeed());
                    pst.setInt(12, p.getIv_Health());
                    pst.setInt(13, p.getIv_Attack());
                    pst.setInt(14, p.getIv_Defense());
                    pst.setInt(15, p.getIv_SpecialAttack());
                    pst.setInt(16, p.getIv_SpecialDefense());
                    pst.setInt(17, p.getIv_Speed());
                    pst.setInt(18, p.getEv_Health());
                    pst.setInt(19, p.getEv_Attack());
                    pst.setInt(20, p.getEv_Defense());
                    pst.setInt(21, p.getEv_SpecialAttack());
                    pst.setInt(22, p.getEv_SpecialDefense());
                    pst.setInt(23, p.getEv_Speed());
                    pst.executeUpdate();
                    if (p.getMoves() != null) {
                        for (Move m : p.getMoves()) {
                            pstMoves.setString(1, p.getPokemonName());
                            pstMoves.setString(2, m.getNameMove());
                            pstMoves.addBatch();
                        }
                        pstMoves.executeBatch();
                    }
                }
            } else {
                // UPDATE
                try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(UPDATE)) {
                    pst.setString(1, p.getPokemonName());
                    pst.setString(2, p.getPokemonFirstType().toString());
                    PokemonType secondType = p.getPokemonSecondType();
                    pst.setString(3, (secondType != null) ? secondType.toString() : null);
                    pst.setString(4, p.getPhotoPokemon());
                    pst.setInt(5, p.getLevelCap());
                    pst.setInt(6, p.getHealth());
                    pst.setInt(7, p.getAttack());
                    pst.setInt(8, p.getDefense());
                    pst.setInt(9, p.getSpecialAttack());
                    pst.setInt(10, p.getSpecialDefense());
                    pst.setInt(11, p.getSpeed());
                    pst.setInt(12, p.getIv_Health());
                    pst.setInt(13, p.getIv_Attack());
                    pst.setInt(14, p.getIv_Defense());
                    pst.setInt(15, p.getIv_SpecialAttack());
                    pst.setInt(16, p.getIv_SpecialDefense());
                    pst.setInt(17, p.getIv_Speed());
                    pst.setInt(18, p.getEv_Health());
                    pst.setInt(19, p.getEv_Attack());
                    pst.setInt(20, p.getEv_Defense());
                    pst.setInt(21, p.getEv_SpecialAttack());
                    pst.setInt(22, p.getEv_SpecialDefense());
                    pst.setInt(23, p.getEv_Speed());
                    pst.executeUpdate();
                    if(p.getMoves()!=null){
                        List<Move> movesBefore = MoveDAO.build().findByPokemon(p);
                        List<Move> movesAfter = p.getMoves();
                        List<Move> movesToBeRemoved = new ArrayList<>(movesBefore);
                        movesToBeRemoved.removeAll(movesAfter);
                        for(Move m:movesToBeRemoved){
                            MoveDAO.build().delete(m);
                        }
                        for(Move m:movesAfter){
                            MoveDAO.build().save(m);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Pokemon delete(Pokemon p) throws SQLException {
        if(p==null || p.getPokemonName()==null) return p;
        try(PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(DELETE)) {
            pst.setString(1,p.getPokemonName());
            pst.executeUpdate();
        }
        return p;
    }

    @Override
    public Pokemon findByName(String key) {
        Pokemon result = null;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYNAME)) {
            pst.setString(1, key);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Pokemon pokemon = new Pokemon();
                    pokemon.setPokemonName(res.getString("PokemonName"));
                    String firstTypeString = res.getString("FirstType");
                    String secondTypeString = res.getString("SecondType");
                    PokemonType firstType = PokemonType.valueOf(firstTypeString);
                    pokemon.setPokemonFirstType(firstType);
                    PokemonType secondType = (secondTypeString != null) ? PokemonType.valueOf(secondTypeString) : null;
                    pokemon.setPokemonSecondType(secondType);
                    pokemon.setPhotoPokemon(res.getString("Photo"));
                    pokemon.setLevelCap(res.getInt("LEVELCAP"));
                    pokemon.setHealth(res.getInt("HP"));
                    pokemon.setAttack(res.getInt("Attack"));
                    pokemon.setDefense(res.getInt("Defense"));
                    pokemon.setSpecialAttack(res.getInt("SpAttack"));
                    pokemon.setSpecialDefense(res.getInt("SpDefense"));
                    pokemon.setSpeed(res.getInt("Speed"));
                    pokemon.setIv_Health(res.getInt("IV_HP"));
                    pokemon.setIv_Attack(res.getInt("IV_Attack"));
                    pokemon.setIv_Defense(res.getInt("IV_Defense"));
                    pokemon.setIv_SpecialAttack(res.getInt("IV_SpAttack"));
                    pokemon.setIv_SpecialDefense(res.getInt("IV_SpDefense"));
                    pokemon.setIv_Speed(res.getInt("IV_Speed"));
                    pokemon.setEv_Health(res.getInt("EV_HP"));
                    pokemon.setEv_Attack(res.getInt("EV_Attack"));
                    pokemon.setEv_Defense(res.getInt("EV_Defense"));
                    pokemon.setEv_SpecialAttack(res.getInt("EV_SpAttack"));
                    pokemon.setEv_SpecialDefense(res.getInt("EV_SpDefense"));
                    pokemon.setEv_Speed(res.getInt("EV_Speed"));
                    pokemon.setMoves(MoveDAO.build().findByPokemon(pokemon));
                    result = pokemon;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Pokemon> findAll() {
        List<Pokemon> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Pokemon pokemon = new Pokemon();
                    pokemon.setPokemonName(res.getString("PokemonName"));
                    String firstTypeString = res.getString("FirstType");
                    String secondTypeString = res.getString("SecondType");
                    PokemonType firstType = PokemonType.valueOf(firstTypeString);
                    pokemon.setPokemonFirstType(firstType);
                    PokemonType secondType = (secondTypeString != null) ? PokemonType.valueOf(secondTypeString) : null;
                    pokemon.setPokemonSecondType(secondType);
                    pokemon.setPhotoPokemon(res.getString("Photo"));
                    pokemon.setLevelCap(res.getInt("LEVELCAP"));
                    pokemon.setHealth(res.getInt("HP"));
                    pokemon.setAttack(res.getInt("Attack"));
                    pokemon.setDefense(res.getInt("Defense"));
                    pokemon.setSpecialAttack(res.getInt("SpAttack"));
                    pokemon.setSpecialDefense(res.getInt("SpDefense"));
                    pokemon.setSpeed(res.getInt("Speed"));
                    pokemon.setIv_Health(res.getInt("IV_HP"));
                    pokemon.setIv_Attack(res.getInt("IV_Attack"));
                    pokemon.setIv_Defense(res.getInt("IV_Defense"));
                    pokemon.setIv_SpecialAttack(res.getInt("IV_SpAttack"));
                    pokemon.setIv_SpecialDefense(res.getInt("IV_SpDefense"));
                    pokemon.setIv_Speed(res.getInt("IV_Speed"));
                    pokemon.setEv_Health(res.getInt("EV_HP"));
                    pokemon.setEv_Attack(res.getInt("EV_Attack"));
                    pokemon.setEv_Defense(res.getInt("EV_Defense"));
                    pokemon.setEv_SpecialAttack(res.getInt("EV_SpAttack"));
                    pokemon.setEv_SpecialDefense(res.getInt("EV_SpDefense"));
                    pokemon.setEv_Speed(res.getInt("EV_Speed"));
                    pokemon.setMoves(MoveDAO.build().findByPokemon(pokemon));
                    result.add(pokemon);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void close() throws IOException {

    }

    public static PokemonDAO build(){
        return new PokemonDAO();
    }
}

