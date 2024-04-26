package com.github.dangelcrack.model.dao;
import com.github.dangelcrack.model.connection.ConnectionMariaDB;
import com.github.dangelcrack.model.entity.Move;
import com.github.dangelcrack.model.entity.Obj;
import com.github.dangelcrack.model.entity.Pokemon;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ObjDAO implements DAO<Obj,String>{
    private static final String FINDALL ="SELECT o.Name, o.Description FROM Objects AS o";
    private static final String FINDBYNAME ="SELECT o.Name, o.Description FROM Objects AS o WHERE o.Name=?";
    private static final String INSERT ="INSERT INTO Objects (Name,Description) VALUES (?,?)";
    private static final String UPDATE ="UPDATE Objects SET Name = ?, Description = ? WHERE Name = ?";
    private static final String DELETE ="DELETE FROM Objects WHERE Name=?";
    private Connection conn;

    public ObjDAO() {
        conn = ConnectionMariaDB.getConnection();
    }

    @Override
    public Obj save(Obj entity) {
        Obj result = entity;
        if (entity != null) {
            String nameObj = entity.getNameObject();
            if (nameObj != null && !nameObj.isEmpty()) {
                Obj isInDataBase = findByName(nameObj);
                if (isInDataBase == null) {
                    // INSERT
                    try (PreparedStatement pst = conn.prepareStatement(INSERT)) {
                        pst.setString(1, entity.getNameObject());
                        pst.setString(2, entity.getDescription());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    // UPDATE
                    try (PreparedStatement pst = conn.prepareStatement(UPDATE)) {
                        pst.setString(1, entity.getNameObject());
                        pst.setString(2, entity.getDescription());
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
    public Obj delete(Obj entity) throws SQLException {
        if (entity != null) {
            try (PreparedStatement pst = conn.prepareStatement(DELETE)) {
                pst.setString(1, entity.getNameObject());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                entity = null;
            }
        }
        return entity;
    }

    @Override
    public Obj findByName(String key) {
        Obj result = null;
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDBYNAME)) {
            pst.setString(1, key);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    Obj obj = new Obj();
                    obj.setNameObject(res.getString("Name"));
                    obj.setDescription(res.getString("Description"));
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
        List<Obj> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionMariaDB.getConnection().prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Obj obj = new Obj();
                    obj.setNameObject(res.getString("Name"));
                    obj.setDescription(res.getString("Description"));
                    result.add(obj);
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
}
