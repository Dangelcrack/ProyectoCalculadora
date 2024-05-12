package com.github.dangelcrack.model.dao;
import com.github.dangelcrack.model.connection.ConnectionMariaDB;
import com.github.dangelcrack.model.entity.Pokemon;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.view.Category;
import com.github.dangelcrack.view.Types;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class MoveDAO implements DAO<Move,String> {
    private static final String FINDALL ="SELECT m.Name, m.Type, m.Category, m.Power FROM moves AS m";
    private static final String FINDBYNAME ="SELECT m.Name FROM moves WHERE m.Name=?";
    private static final String INSERT ="INSERT INTO moves (Name,Type,Category,Power) VALUES (?,?,?,?)";
    private static final String UPDATE ="UPDATE moves SET Type = ?, Category = ?, Power = ? WHERE Name = ?";
    private static final String DELETE ="DELETE FROM moves WHERE Name=?";
    private static final String FINDBYPOKEMON = "SELECT m.Name, m.Type, m.Category, m.Power\n" +
            "FROM Moves AS m\n" +
            "INNER JOIN PokemonMoves AS pm ON m.Name = pm.MoveName\n" +
            "WHERE pm.PokemonName = ?;";


    private Connection conn;
    public MoveDAO(){
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Move save(Move m) {
        Move result = m;
        if (m != null) {
            String nameMove = m.getNameMove();
            if (nameMove != null && !nameMove.isEmpty()) {
                Move isInDataBase = findByName(nameMove);
                if (isInDataBase == null) {
                    // INSERT
                    try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setString(1, m.getNameMove());
                        pst.setString(2, m.getTypeMove().toString());
                        pst.setString(3, m.getCategory().toString());
                        pst.setInt(4, m.getPower());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    // UPDATE
                    try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                        pst.setString(2, m.getTypeMove().toString());
                        pst.setString(3, m.getCategory().toString());
                        pst.setInt(4, m.getPower());
                        pst.setString(1, m.getNameMove());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Move delete(Move m) {
        if (m != null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setString(1, m.getNameMove());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                m = null;
            }
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

    public List<Move> findByPokemon(Pokemon p){
        List<Move> result = new ArrayList<>();
        if(p==null || p.getPokemonName()==null)return result;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYPOKEMON)) {
            pst.setString(1,p.getPokemonName());
            try(ResultSet res = pst.executeQuery()){
                while(res.next()){
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
        return  result;
    }
    @Override
    public void close() {

    }

    public static MoveDAO build(){
        return new MoveDAO();
    }
}
