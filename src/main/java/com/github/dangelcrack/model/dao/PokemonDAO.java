package com.github.dangelcrack.model.dao;

import com.github.dangelcrack.model.connection.ConnectionMariaDB;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Obj;
import com.github.dangelcrack.model.entity.Pokemon;
import com.github.dangelcrack.view.Nature;
import com.github.dangelcrack.view.Types;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAO implements DAO<Pokemon, String> {
    private static final String INSERT = "INSERT INTO Pokemon (PokemonName, FirstType, SecondType, Photo, LEVELCAP, HP, Attack, Defense, SpAttack, SpDefense, Speed, Iv_HP, Iv_Attack, Iv_Defense, Iv_SpAttack, Iv_SpDefense, Iv_Speed, Ev_HP, Ev_Attack, Ev_Defense, Ev_SpAttack, Ev_SpDefense, Ev_Speed, Nature) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE = "UPDATE Pokemon\n" +
            "SET FirstType = ?, SecondType = ?, Photo = ?, LEVELCAP = ?, HP = ?, Attack = ?, Defense = ?, SpAttack = ?, SpDefense = ?, Speed = ?, Iv_HP = ?, Iv_Attack = ?, Iv_Defense = ?, Iv_SpAttack = ?, Iv_SpDefense = ?, Iv_Speed = ?, Ev_HP = ?, Ev_Attack = ?, Ev_Defense = ?, Ev_SpAttack = ?, Ev_SpDefense = ?, Ev_Speed = ?, Nature = ? " +
            "WHERE PokemonName = ?";

    private static final String FINDALL = "SELECT p.PokemonName, p.FirstType, p.SecondType, p.Photo, p.LEVELCAP, p.HP, p.Attack, p.Defense, p.SpAttack, p.SpDefense, p.Speed, p.Iv_HP, p.Iv_Attack, p.Iv_Defense, p.Iv_SpAttack, p.Iv_SpDefense, p.Iv_Speed, p.Ev_HP, p.Ev_Attack, p.Ev_Defense, p.Ev_SpAttack, p.Ev_SpDefense, p.Ev_Speed, p.Nature FROM Pokemon p";

    private static final String FINDBYNAME = "SELECT PokemonName FROM Pokemon WHERE PokemonName=?";
    private static final String DELETE_POKEMON = "DELETE FROM Pokemon WHERE PokemonName = ?";
    private static final String INSERTMOVESTOPOKEMON = "INSERT INTO PokemonMoves (PokemonName, MoveName) VALUES (?, ?)";
    private static final String DELETEOLDMOVES = "DELETE FROM PokemonMoves WHERE PokemonName = ?";
    private static final String REMOVE_POKEMON_OBJECT = "UPDATE Pokemon SET Object_Name = NULL WHERE PokemonName = ?";
    private static final  String FIND_BY_MOVE_NAME = "SELECT p.* FROM Pokemon p INNER JOIN PokemonMoves pm ON p.PokemonName = pm.PokemonName WHERE pm.MoveName = ?";
    private static final String FIND_BY_OBJ_NAME = "SELECT p.* FROM Pokemon p INNER JOIN Objects o ON p.Object_Name = o.Name WHERE o.Name = ?";
    private Connection conn;
    public PokemonDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Pokemon save(Pokemon p) {
        Pokemon result = p;
        if (p == null || p.getPokemonName() == null) return result;
        try {
            Pokemon existingPokemon = findByName(p.getPokemonName());
            if (existingPokemon == null) {
                // INSERT
                try (PreparedStatement pst = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                    PreparedStatement pstMoves = conn.prepareStatement(INSERTMOVESTOPOKEMON);
                    pst.setString(1, p.getPokemonName());
                    pst.setString(2, p.getPokemonFirstType().toString());
                    Types secondType = p.getPokemonSecondType();
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
                    pst.setString(24,p.getNature().toString());
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
                try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                    PreparedStatement pstMoves = conn.prepareStatement(INSERTMOVESTOPOKEMON);
                    pst.setString(1, p.getPokemonFirstType().toString());
                    Types secondType = p.getPokemonSecondType();
                    pst.setString(2, (secondType != null) ? secondType.toString() : null);
                    pst.setString(3, p.getPhotoPokemon());
                    pst.setInt(4, p.getLevelCap());
                    pst.setInt(5, p.getHealth());
                    pst.setInt(6, p.getAttack());
                    pst.setInt(7, p.getDefense());
                    pst.setInt(8, p.getSpecialAttack());
                    pst.setInt(9, p.getSpecialDefense());
                    pst.setInt(10, p.getSpeed());
                    pst.setInt(11, p.getIv_Health());
                    pst.setInt(12, p.getIv_Attack());
                    pst.setInt(13, p.getIv_Defense());
                    pst.setInt(14, p.getIv_SpecialAttack());
                    pst.setInt(15, p.getIv_SpecialDefense());
                    pst.setInt(16, p.getIv_Speed());
                    pst.setInt(17, p.getEv_Health());
                    pst.setInt(18, p.getEv_Attack());
                    pst.setInt(19, p.getEv_Defense());
                    pst.setInt(20, p.getEv_SpecialAttack());
                    pst.setInt(21, p.getEv_SpecialDefense());
                    pst.setInt(22, p.getEv_Speed());
                    pst.setString(23,p.getNature().toString());
                    pst.setString(24, p.getPokemonName());
                    pst.executeUpdate();
                    try (PreparedStatement pstDeleteMoves = conn.prepareStatement(DELETEOLDMOVES)) {
                        pstDeleteMoves.setString(1, p.getPokemonName());
                        pstDeleteMoves.executeUpdate();
                    }
                    if (p.getMoves() != null) {
                        for (Move m : p.getMoves()) {
                            pstMoves.setString(1, p.getPokemonName());
                            pstMoves.setString(2, m.getNameMove());
                            pstMoves.addBatch();
                        }
                        pstMoves.executeBatch();
                    }
                    try (PreparedStatement pstRemovePokemonObject = conn.prepareStatement(REMOVE_POKEMON_OBJECT)) {
                        pstRemovePokemonObject.setString(1, p.getPokemonName());
                        pstRemovePokemonObject.executeUpdate();
                    }
                    if(p.getObj()!=null){
                        pst.setString(25, p.getObj().getNameObject());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Pokemon delete(Pokemon p) {
        if (p == null || p.getPokemonName() == null) return p;

        try (PreparedStatement pstMoves = conn.prepareStatement(DELETEOLDMOVES);
             PreparedStatement pstRemovePokemonObject = conn.prepareStatement(REMOVE_POKEMON_OBJECT);
             PreparedStatement pstPokemon = conn.prepareStatement(DELETE_POKEMON)) {
            pstRemovePokemonObject.setString(1, p.getPokemonName());
            pstRemovePokemonObject.executeUpdate();
            pstMoves.setString(1, p.getPokemonName());
            pstMoves.executeUpdate();
            pstPokemon.setString(1, p.getPokemonName());
            pstPokemon.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return p;
    }

    @Override
    public Pokemon findByName(String namePokemon) {
        Pokemon result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYNAME)) {
            pst.setString(1, namePokemon);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Pokemon pokemon = new Pokemon();
                    pokemon.setPokemonName(res.getString("PokemonName"));
                    pokemon.setMoves(MoveDAO.build().findByPokemon(pokemon));
                    pokemon.setObj(ObjDAO.build().findByPokemon(pokemon));
                    result = pokemon;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Pokemon> findPokemonByMoveName(String nameMove) {
        List<Pokemon> pokemonList = new ArrayList<>();

        try (PreparedStatement pst = conn.prepareStatement(FIND_BY_MOVE_NAME)) {
            pst.setString(1, nameMove);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Pokemon pokemon = new Pokemon();
                    pokemon.setPokemonName(rs.getString("PokemonName"));
                    String firstTypeString = rs.getString("FirstType");
                    String secondTypeString = rs.getString("SecondType");
                    pokemon.setPhotoPokemon(rs.getString("Photo"));
                    Types firstType = Types.valueOf(firstTypeString);
                    pokemon.setPokemonFirstType(firstType);
                    Types secondType = (secondTypeString != null) ? Types.valueOf(secondTypeString) : null;
                    pokemon.setPokemonSecondType(secondType);
                    pokemonList.add(pokemon);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pokemonList;
    }
    public List<Pokemon> findPokemonByObj(Obj o) {
        List<Pokemon> pokemonList = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(FIND_BY_OBJ_NAME)) {
            pst.setString(1, o.getNameObject());
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Pokemon pokemon = new Pokemon();
                    pokemon.setPokemonName(rs.getString("PokemonName"));
                    String firstTypeString = rs.getString("FirstType");
                    String secondTypeString = rs.getString("SecondType");
                    pokemon.setPhotoPokemon(rs.getString("Photo"));
                    Types firstType = Types.valueOf(firstTypeString);
                    pokemon.setPokemonFirstType(firstType);
                    Types secondType = (secondTypeString != null) ? Types.valueOf(secondTypeString) : null;
                    pokemon.setPokemonSecondType(secondType);
                    pokemonList.add(pokemon);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pokemonList;
    }
    @Override
    public List<Pokemon> findAll() {
        List<Pokemon> result = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Pokemon pokemon = new Pokemon();
                    pokemon.setPokemonName(res.getString("PokemonName"));
                    String firstTypeString = res.getString("FirstType");
                    String secondTypeString = res.getString("SecondType");
                    Types firstType = Types.valueOf(firstTypeString);
                    pokemon.setPokemonFirstType(firstType);
                    Types secondType = (secondTypeString != null) ? Types.valueOf(secondTypeString) : null;
                    pokemon.setPokemonSecondType(secondType);
                    String natureString = res.getString("Nature");
                    Nature nature = Nature.valueOf(natureString);
                    pokemon.setNature(nature);
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
                    pokemon.setObj(ObjDAO.build().findByPokemon(pokemon));
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

    public static PokemonDAO build() {
        return new PokemonDAO();
    }
}

