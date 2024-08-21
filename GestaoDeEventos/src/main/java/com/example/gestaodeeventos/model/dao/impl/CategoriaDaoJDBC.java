package com.example.gestaodeeventos.model.dao.impl;

import com.example.gestaodeeventos.db.DB;
import com.example.gestaodeeventos.db.DbException;
import com.example.gestaodeeventos.model.dao.CategoriaDao;
import com.example.gestaodeeventos.model.entities.Categoria;
import com.example.gestaodeeventos.model.entities.Instituicao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDaoJDBC implements CategoriaDao {

    private Connection conn;

    public CategoriaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Categoria obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO categoria " +
                            "(nome, descricao) " +
                            "VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setString(2, obj.getDescricao());


            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Erro inesperado! Nenhuma linha foi inserida.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Categoria obj) {

    }

    @Override
    public void deleteByName(String nome) {

    }

    @Override
    public Categoria findByName(String nome) {
        String sql = "SELECT * FROM categoria WHERE nome = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, nome);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateCategoria(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Categoria> findAll() {
        String sql = "SELECT * FROM categoria";
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Categoria> list = new ArrayList<>();

            while (rs.next()) {
                list.add(instantiateCategoria(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Categoria instantiateCategoria(ResultSet rs) throws SQLException {
        return new Categoria(rs.getString("nome"), rs.getString("descricao"));
    }
}
