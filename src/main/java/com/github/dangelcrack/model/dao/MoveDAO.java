package com.github.dangelcrack.model.dao;

import com.github.dangelcrack.model.connection.ConnectionMariaDB;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Pokemon;
import com.github.dangelcrack.model.entity.Category;
import com.github.dangelcrack.model.entity.Types;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing Move entities in the database.
 */
public class MoveDAO implements DAO<Move, String> {

    private static final String FINDALL = "SELECT m.Name, m.Type, m.Category, m.Power FROM moves AS m";
    private static final String FINDBYNAME = "SELECT m.Name FROM Moves AS m WHERE m.Name=?";
    private static final String INSERT = "INSERT INTO moves (Name,Type,Category,Power) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE moves SET Type = ?, Category = ?, Power = ? WHERE Name = ?";
    private static final String DELETE_MOVE = "DELETE FROM Moves WHERE Name = ?";
    private static final String FINDBYPOKEMON = "SELECT m.Name, m.Type, m.Category, m.Power\n" +
            "FROM Moves AS m\n" +
            "INNER JOIN PokemonMoves AS pm ON m.Name = pm.MoveName\n" +
            "WHERE pm.PokemonName = ?;";
    private static final String INSERTMOVESTOPOKEMON = "INSERT INTO PokemonMoves (PokemonName, MoveName) VALUES (?, ?)";
    private static final String DELETE_OLD_MOVE = "DELETE FROM PokemonMoves WHERE MoveName = ?;";
    private Connection conn;

    /**
     * Constructor initializes the database connection.
     */
    public MoveDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    /**
     * Saves a Move entity to the database. If the move already exists, it updates the existing entry.
     *
     * @param m the Move entity to save.
     * @return the saved Move entity.
     */
    @Override
    public Move save(Move m) {
        if (m == null || m.getNameMove() == null || m.getNameMove().isEmpty()) {
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
                // UPDATE
                try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                    pst.setString(1, m.getTypeMove().toString());
                    pst.setString(2, m.getCategory().toString());
                    pst.setInt(3, m.getPower());
                    pst.setString(4, nameMove);
                    pst.executeUpdate();
                }

                // Delete old moves associated with the move name
                try (PreparedStatement pstDelete = conn.prepareStatement(DELETE_OLD_MOVE)) {
                    pstDelete.setString(1, nameMove);
                    pstDelete.executeUpdate();
                }

                // Insert new moves for the Pokémon that can learn the move
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

    /**
     * Deletes a Move entity from the database.
     *
     * @param m the Move entity to delete.
     * @return the deleted Move entity.
     */
    @Override
    public Move delete(Move m) {
        if (m == null || m.getNameMove() == null) return m;

        try (PreparedStatement pstMoves = conn.prepareStatement(DELETE_OLD_MOVE);
             PreparedStatement pstPokemon = conn.prepareStatement(DELETE_MOVE)) {
            pstMoves.setString(1, m.getNameMove());
            pstMoves.executeUpdate();
            pstPokemon.setString(1, m.getNameMove());
            pstPokemon.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return m;
    }

    /**
     * Finds a Move entity by its name.
     *
     * @param nameMove the name of the Move to find.
     * @return the found Move entity, or null if not found.
     */
    @Override
    public Move findByName(String nameMove) {
        Move result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYNAME)) {
            pst.setString(1, nameMove);
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

    /**
     * Finds all Move entities in the database.
     *
     * @return a list of all Move entities.
     */
    @Override
    public List<Move> findAll() {
        List<Move> result = new ArrayList<>();
        try (PreparedStatement pst = conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Finds all Move entities that can be learned by a specific Pokémon.
     *
     * @param p the Pokémon entity.
     * @return a list of Move entities that the Pokémon can learn.
     */
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

    /**
     * Closes any resources held by the DAO.
     */
    @Override
    public void close() {
        // Method to close resources if needed
    }

    /**
     * Builds a new instance of the MoveDAO.
     *
     * @return a new MoveDAO instance.
     */
    public static MoveDAO build() {
        return new MoveDAO();
    }
}
