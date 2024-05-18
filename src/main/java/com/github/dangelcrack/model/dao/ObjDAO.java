package com.github.dangelcrack.model.dao;

import com.github.dangelcrack.model.connection.ConnectionMariaDB;
import com.github.dangelcrack.model.entity.Obj;
import com.github.dangelcrack.model.entity.Pokemon;
import com.github.dangelcrack.view.Category;
import com.github.dangelcrack.view.Types;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ObjDAO implements DAO<Obj, String> {
    private static final String FINDALL = "SELECT o.Name, o.Description, o.Photo, o.BoostType, o.BoostCategory, o.BoostAttack, o.BoostDefense, o.BoostSpAttack, o.BoostSpDefense, o.BoostSpeed FROM Objects AS o";
    private static final String FINDBYNAME = "SELECT o.Name FROM Objects AS o WHERE o.Name=?";
    private static final String INSERT = "INSERT INTO Objects (Name, Description, Photo, BoostType, BoostCategory, BoostAttack, BoostDefense, BoostSpAttack, BoostSpDefense, BoostSpeed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE Objects SET Description = ?, Photo = ?, BoostType = ?, BoostCategory = ?, BoostAttack = ?, BoostDefense = ?, BoostSpAttack = ?, BoostSpDefense = ?, BoostSpeed = ? WHERE Name = ?";
    private static final String DELETE_OBJ = "DELETE FROM Objects WHERE Name=?";
    private static final String UPDATE_OBJ_TO_POKEMON = "UPDATE Pokemon SET Object_Name = ? WHERE PokemonName = ?";
    private static final String FINDBYPOKEMON = "SELECT o.Name, o.Description, o.Photo, o.BoostType, o.BoostCategory, o.BoostAttack, o.BoostDefense, o.BoostSpAttack, o.BoostSpDefense, o.BoostSpeed\n" +
            "FROM Objects AS o\n" +
            "INNER JOIN Pokemon AS p ON o.Name = p.Object_Name\n" +
            "WHERE p.PokemonName = ?";
    private Connection conn;

    public ObjDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Obj save(Obj o) {
        if (o == null || o.getNameObject() == null || o.getNameObject().isEmpty()) {
            return null;
        }
        try {
            String nameObj = o.getNameObject();
            Obj isInDataBase = findByName(nameObj);
            if (isInDataBase == null) {
                // INSERT
                try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
                    pst.setString(1, nameObj);
                    pst.setString(2, o.getDescription());
                    pst.setString(3, o.getPhotoObj());
                    Types BoostType = o.getBoostType();
                    pst.setString(4, (BoostType != null) ? BoostType.toString() : null);
                    Category BoostCategory = o.getBoostCategory();
                    pst.setString(5, (BoostCategory != null) ? BoostCategory.toString() : null);
                    pst.setInt(6, o.getAttackBoost());
                    pst.setInt(7, o.getDefenseBoost());
                    pst.setInt(8, o.getSpAttackBoost());
                    pst.setInt(9, o.getSpDefenseBoost());
                    pst.setInt(10, o.getSpeedBoost());
                    pst.executeUpdate();
                }

                List<Pokemon> pokemonCanHold = o.getPokemonWithObj();
                for (Pokemon pokemon : pokemonCanHold) {
                    try (PreparedStatement pstObj = conn.prepareStatement(UPDATE_OBJ_TO_POKEMON)) {
                        pstObj.setString(1, nameObj);
                        pstObj.setString(2, pokemon.getPokemonName());
                        pstObj.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //UPDATE
                try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                    pst.setString(10, nameObj);
                    pst.setString(1, o.getDescription());
                    pst.setString(2, o.getPhotoObj());
                    Types BoostType = o.getBoostType();
                    pst.setString(3, (BoostType != null) ? BoostType.toString() : null);
                    Category BoostCategory = o.getBoostCategory();
                    pst.setString(4, (BoostCategory != null) ? BoostCategory.toString() : null);
                    pst.setInt(5, o.getAttackBoost());
                    pst.setInt(6, o.getDefenseBoost());
                    pst.setInt(7, o.getSpAttackBoost());
                    pst.setInt(8, o.getSpDefenseBoost());
                    pst.setInt(9, o.getSpeedBoost());
                    pst.executeUpdate();
                }
                List<Pokemon> pokemonCanHold = o.getPokemonWithObj();
                for (Pokemon pokemon : pokemonCanHold) {
                    try (PreparedStatement pstObj = conn.prepareStatement(UPDATE_OBJ_TO_POKEMON)) {
                        pstObj.setString(1, nameObj);
                        pstObj.setString(2, pokemon.getPokemonName());
                        pstObj.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return o;
    }

    @Override
    public Obj delete(Obj o) {
        if (o != null) {
            try {
                // Paso 1: Desvincular el objeto de todos los Pokémon que lo tienen asignado
                for (Pokemon pokemon : o.getPokemonWithObj()) {
                    try (PreparedStatement pst = conn.prepareStatement(UPDATE_OBJ_TO_POKEMON)) {
                        pst.setString(1, null); // Establecer Object_Name a NULL
                        pst.setString(2, pokemon.getPokemonName()); // Usar el nombre del Pokémon
                        pst.executeUpdate();
                    }
                }
                // Paso 2: Eliminar el objeto de la tabla 'objects'
                try (PreparedStatement pstObj = conn.prepareStatement(DELETE_OBJ)) {
                    pstObj.setString(1, o.getNameObject()); // Usar el nombre del objeto
                    pstObj.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                o = null;
            }
        }
        return o;
    }


    @Override
    public Obj findByName(String nameObject) {
        Obj result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYNAME)) {
            pst.setString(1, nameObject);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Obj obj = new Obj();
                    obj.setNameObject(res.getString("Name"));
                    result = obj;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Obj> findAll() {
        List<Obj> objectsList = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(FINDALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Obj obj = new Obj();
                obj.setNameObject(rs.getString("Name"));
                obj.setDescription(rs.getString("Description"));
                obj.setPhotoObj(rs.getString("Photo"));
                String boostTypeString = rs.getString("BoostType");
                if (boostTypeString != null) {
                    Types boostType = Types.valueOf(boostTypeString);
                    obj.setBoostType(boostType);
                } else {
                    obj.setBoostType(null);
                }
                String boostCategoryString = rs.getString("BoostCategory");
                if (boostCategoryString != null) {
                    Category boostCategoryAffected = Category.valueOf(boostCategoryString);
                    obj.setBoostCategory(boostCategoryAffected);
                } else {
                    obj.setBoostCategory(null);
                }
                obj.setAttackBoost(rs.getInt("BoostAttack"));
                obj.setDefenseBoost(rs.getInt("BoostDefense"));
                obj.setSpAttackBoost(rs.getInt("BoostSpAttack"));
                obj.setSpDefenseBoost(rs.getInt("BoostSpDefense"));
                obj.setSpeedBoost(rs.getInt("BoostSpeed"));
                objectsList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objectsList;
    }

    public Obj findByPokemon(Pokemon p) {
        Obj result = new Obj();
        if (p == null || p.getPokemonName() == null)
            return result;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYPOKEMON)) {
            pst.setString(1, p.getPokemonName());
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) { // Asegúrate de llamar a res.next()
                    Obj o = new Obj();
                    o.setNameObject(res.getString("Name"));
                    o.setDescription(res.getString("Description"));
                    o.setPhotoObj(res.getString("Photo"));
                    String boostTypeString = res.getString("BoostType");
                    if (boostTypeString != null) {
                        Types boostType = Types.valueOf(boostTypeString);
                        o.setBoostType(boostType);
                    } else {
                        o.setBoostType(null);
                    }
                    String boostCategoryString = res.getString("BoostCategory");
                    if (boostCategoryString != null) {
                        Category boostCategoryAffected = Category.valueOf(boostCategoryString);
                        o.setBoostCategory(boostCategoryAffected);
                    } else {
                        o.setBoostCategory(null);
                    }
                    o.setAttackBoost(res.getInt("BoostAttack"));
                    o.setDefenseBoost(res.getInt("BoostDefense"));
                    o.setSpAttackBoost(res.getInt("BoostSpAttack"));
                    o.setSpDefenseBoost(res.getInt("BoostSpDefense"));
                    o.setSpeedBoost(res.getInt("BoostSpeed"));
                    result = o; // Esto sobrescribirá el objeto en cada iteración
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws IOException {

    }

    public static ObjDAO build() {
        return new ObjDAO();
    }
}
