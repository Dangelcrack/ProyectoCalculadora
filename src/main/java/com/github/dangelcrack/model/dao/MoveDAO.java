package com.github.dangelcrack.model.dao;
import com.github.dangelcrack.model.connection.ConnectionMariaDB;
import com.github.dangelcrack.model.entity.Pokemon;
import com.github.dangelcrack.model.entity.Move;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class MoveDAO implements DAO<Move,String> {
    private static final String FINDALL ="SELECT m.name, m.type, m.category, m.power FROM moves AS m";
    private static final String FINDBYNAME ="SELECT m.name, m.type, m.category, m.power FROM moves WHERE m.name=?";
    private static final String INSERT ="INSERT INTO moves (name,type,category,power) VALUES (?,?,?,?)";
    private static final String UPDATE ="UPDATE moves SET type = ?, category = ?, power = ? WHERE name = ?";
    private static final String DELETE ="DELETE FROM moves WHERE name=?";


    private Connection conn;
    public MoveDAO(){
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Move save(Move entity) {
        Move result = entity;
        if (entity != null) {
            String nameMove = entity.getNameMove();
            if (nameMove != null && !nameMove.isEmpty()) {
                Move isInDataBase = findByName(nameMove);
                if (isInDataBase == null) {
                    // INSERT
                    try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setString(1, entity.getNameMove());
                        pst.setString(2, entity.getTypeMove());
                        pst.setString(3, entity.getCategory());
                        pst.setInt(4, entity.getPower());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    // UPDATE
                    try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                        pst.setString(1, entity.getTypeMove());
                        pst.setString(2, entity.getCategory());
                        pst.setInt(3, entity.getPower());
                        pst.setString(4, entity.getNameMove());
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
    public Move delete(Move entity) {
        if (entity != null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setString(1, entity.getNameMove());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                entity = null;
            }
        }
        return entity;
    }


    @Override
    public Move findByName(String key) {
        Move result = null;
        try (PreparedStatement pst = conn.prepareStatement(FINDBYNAME)) {
            pst.setString(1, key);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Move move = new Move();
                    move.setNameMove(res.getString("nameMove"));
                    move.setTypeMove(res.getString("typeMove"));
                    move.setCategory(res.getString("category"));
                    move.setPower(res.getInt("power"));
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
        try (PreparedStatement pst = conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Move move = new Move();
                    move.setNameMove(res.getString("nameMove"));
                    move.setTypeMove(res.getString("typeMove"));
                    move.setCategory(res.getString("category"));
                    move.setPower(res.getInt("power"));
                    result.add(move);
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

    public static MoveDAO build(){
        return new MoveDAO();
    }
}
