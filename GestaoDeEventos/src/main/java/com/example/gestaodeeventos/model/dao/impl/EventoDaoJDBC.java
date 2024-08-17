package com.example.gestaodeeventos.model.dao.impl;

import com.example.gestaodeeventos.db.DB;
import com.example.gestaodeeventos.db.DbException;
import com.example.gestaodeeventos.model.dao.EventoDao;
import com.example.gestaodeeventos.model.entities.Evento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDaoJDBC implements EventoDao {
    private Connection conn;

    public EventoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Evento obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO evento " +
                            "(nome, expectativaParticipantes, descricao, mapaURL, data, modalidade, instituicao_id, categoria_nome) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getExpectativaParticipantes());
            st.setString(3, obj.getDescricao());
            st.setString(4, obj.getMapaURL());
            st.setDate(5,  new java.sql.Date(obj.getData().getTime()));
            st.setString(6, obj.getModalidade().toString());
            st.setInt(7, obj.getInstituicao().getId());
            st.setString(8, obj.getCategoria().getNome());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
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
    public void update(Evento obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Evento findById(Integer id) {
        return null;
    }

    @Override
    public List<Evento> findAllByOrgId(Integer id) {
        return null;
    }

    @Override
    public List<Evento> findByUserId(Integer id) {
        return null;
    }
}
