package com.github.dangelcrack.model.dao;

import com.github.dangelcrack.model.connection.ConnectionMariaDB;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Pokemon;
import com.github.dangelcrack.view.Category;
import com.github.dangelcrack.view.Types;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoveDAO implements DAO<Move, String> {
    private static final String FINDALL = "SELECT m.Name, m.Type, m.Category, m.Power FROM moves AS m";
    private static final String FINDBYNAME = "SELECT Name FROM Moves WHERE Name=?";
    private static final String INSERT = "INSERT INTO moves (Name,Type,Category,Power) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE moves SET Type = ?, Category = ?, Power = ? WHERE Name = ?";
    private static final String DELETE_MOVES = "DELETE FROM PokemonMoves WHERE MoveName = ?";
    private static final String DELETE_POKEMON = "DELETE FROM Moves WHERE Name = ?";
    private static final String FINDBYPOKEMON = "SELECT m.Name, m.Type, m.Category, m.Power\n" +
            "FROM Moves AS m\n" +
            "INNER JOIN PokemonMoves AS pm ON m.Name = pm.MoveName\n" +
            "WHERE pm.PokemonName = ?;";
    private static final String INSERTMOVESTOPOKEMON = "INSERT INTO PokemonMoves (PokemonName, MoveName) VALUES (?, ?)";
    private static final String MOVES_CAN_LEARN_POKEMON = "SELECT COUNT(*) FROM PokemonMoves WHERE PokemonName = ? AND MoveName = ?";
    private static final String DELETE_OLD_MOVE = "DELETE FROM PokemonMoves WHERE MoveName = ?;";


    private Connection conn;

    public MoveDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Move save(Move m) {
        if (m == null || m.getNameMove() == null || m.getNameMove().isEmpty()) {
            // Manejar caso de entrada inválida
            return null;
        }

        try {
            String nameMove = m.getNameMove();
            Move isInDataBase = findByName(nameMove);

            if (isInDataBase == null) {
                // INSERT
                try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
                    pst.setString(1, nameMove);
                    pst.setString(2, m.getTypeMove().toString());
                    pst.setString(3, m.getCategory().toString());
                    pst.setInt(4, m.getPower());
                    pst.executeUpdate();
                }

                try (PreparedStatement pstMoves = conn.prepareStatement(INSERTMOVESTOPOKEMON)) {
                    for (Pokemon pokemon : m.getPokemonCanLearn()) {
                        pstMoves.setString(1, pokemon.getPokemonName());
                        pstMoves.setString(2, nameMove);
                        pstMoves.addBatch();
                    }
                    pstMoves.executeBatch();
                }
            } else {
                //UPDATE
                try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                    pst.setString(1, m.getTypeMove().toString());
                    pst.setString(2, m.getCategory().toString());
                    pst.setInt(3, m.getPower());
                    pst.setString(4, nameMove);
                    pst.executeUpdate();
                }

                // Eliminación de todos los movimientos antiguos asociados con el nombre del movimiento
                try (PreparedStatement pstDelete = conn.prepareStatement(DELETE_OLD_MOVE)) {
                    pstDelete.setString(1, nameMove);
                    pstDelete.executeUpdate();
                }

                // Inserción de los nuevos movimientos para los Pokémon que pueden aprender el movimiento
                try (PreparedStatement pstMoves = conn.prepareStatement(INSERTMOVESTOPOKEMON)) {
                    for (Pokemon pokemon : m.getPokemonCanLearn()) {
                        pstMoves.setString(1, pokemon.getPokemonName());
                        pstMoves.setString(2, nameMove);
                        pstMoves.addBatch();
                    }
                    pstMoves.executeBatch();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return m;
    }
    // Método para verificar si el movimiento ya existe para el Pokémon
    private boolean checkIfMoveExists(Connection conn, String pokemonName, String moveName) {
        // El resultado de la consulta
        boolean exists = false;
        try {
            // Preparar la declaración
            PreparedStatement pst = conn.prepareStatement(MOVES_CAN_LEARN_POKEMON);
            // Establecer los parámetros de la consulta
            pst.setString(1, pokemonName);
            pst.setString(2, moveName);
            // Ejecutar la consulta
            ResultSet rs = pst.executeQuery();
            // Si hay un resultado, entonces el movimiento ya existe
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            // Manejar la excepción SQL
            e.printStackTrace();
        }
        // Retornar el resultado de la consulta
        return exists;
    }
    @Override
    public Move delete(Move m) {
        if (m == null || m.getNameMove() == null) return m;

        try (PreparedStatement pstMoves = ConnectionMariaDB.getConnection().prepareStatement(DELETE_MOVES);
             PreparedStatement pstPokemon = ConnectionMariaDB.getConnection().prepareStatement(DELETE_POKEMON)) {
            pstMoves.setString(1, m.getNameMove());
            pstMoves.executeUpdate();
            pstPokemon.setString(1, m.getNameMove());
            pstPokemon.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return m;
    }



    @Override
    public Move findByName(String key) {
        Move result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYNAME)) {
            pst.setString(1, key);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Move move = new Move();
                    move.setNameMove(res.getString("Name"));
                    result = move;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Move> findAll() {
        List<Move> result = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet res = null;

        try {
            pst = conn.prepareStatement(FINDALL);
            res = pst.executeQuery();

            while (res.next()) {
                Move move = new Move();
                move.setNameMove(res.getString("Name"));

                String TypeString = res.getString("Type");
                Types Type = Types.valueOf(TypeString);
                move.setTypeMove(Type);

                String CategoryString = res.getString("Category");
                Category category = Category.valueOf(CategoryString);
                move.setCategory(category);

                move.setPower(res.getInt("Power"));
                result.add(move);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public List<Move> findByPokemon(Pokemon p) {
        List<Move> result = new ArrayList<>();
        if (p == null || p.getPokemonName() == null) return result;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYPOKEMON)) {
            pst.setString(1, p.getPokemonName());
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Move m = new Move();
                    m.setNameMove(res.getString("Name"));
                    m.setTypeMove(Types.valueOf(res.getString("Type")));
                    m.setCategory(Category.valueOf(res.getString("Category")));
                    m.setPower(res.getInt("Power"));
                    result.add(m);

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
    public void close() {

    }

    public static MoveDAO build() {
        return new MoveDAO();
    }
}
