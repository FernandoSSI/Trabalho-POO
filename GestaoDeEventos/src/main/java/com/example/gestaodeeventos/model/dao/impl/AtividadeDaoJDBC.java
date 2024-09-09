package com.example.gestaodeeventos.model.dao.impl;

import com.example.gestaodeeventos.model.dao.AtividadeDao;
import com.example.gestaodeeventos.model.entities.Atividade;
import com.example.gestaodeeventos.model.entities.Evento;
import com.example.gestaodeeventos.model.services.EventoService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtividadeDaoJDBC implements AtividadeDao {

    private Connection conn;

    public AtividadeDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Atividade obj) {
        String sql = "INSERT INTO atividade (titulo, data, local, descricao, evento_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, obj.getTitulo());
            st.setDate(2, new java.sql.Date(obj.getData().getTime()));
            st.setString(3, obj.getLocal());
            st.setString(4, obj.getDescricao());
            st.setInt(5, obj.getEvento().getId());

            int affectedRows = st.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        obj.setId(rs.getInt(1));
                    }
                }
            } else {
                throw new SQLException("Failed to insert atividade, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Atividade obj) {
        String sql = "UPDATE atividade SET titulo = ?, data = ?, local = ?, descricao = ?, evento_id = ? WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, obj.getTitulo());
            st.setDate(2, new java.sql.Date(obj.getData().getTime()));
            st.setString(3, obj.getLocal());
            st.setString(4, obj.getDescricao());
            st.setInt(5, obj.getEvento().getId());
            st.setInt(6, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM atividade WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Atividade findById(Integer id) {
        String sql = "SELECT * FROM atividade WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Evento evento = instantiateEvento(rs);
                    return instantiateAtividade(rs, evento);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<Atividade> findAll() {
        String sql = "SELECT * FROM atividade";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Atividade> list = new ArrayList<>();

            while (rs.next()) {
                Evento evento = instantiateEvento(rs);
                Atividade obj = instantiateAtividade(rs, evento);
                list.add(obj);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Atividade> findByEventoId(Integer eventoId) {
        String sql = "SELECT * FROM atividade WHERE evento_id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, eventoId);

            try (ResultSet rs = st.executeQuery()) {
                List<Atividade> list = new ArrayList<>();

                while (rs.next()) {
                    Evento evento = instantiateEvento(rs);
                    Atividade obj = instantiateAtividade(rs, evento);
                    list.add(obj);
                }

                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Evento instantiateEvento(ResultSet rs) throws SQLException {
        Evento evento = new EventoService().findById(rs.getInt("evento_id"));
        return evento;
    }

    private Atividade instantiateAtividade(ResultSet rs, Evento evento) throws SQLException {
        Atividade atividade = new Atividade();
        atividade.setId(rs.getInt("id"));
        atividade.setTitulo(rs.getString("titulo"));
        atividade.setData(new java.util.Date(rs.getDate("data").getTime()));
        atividade.setLocal(rs.getString("local"));
        atividade.setDescricao(rs.getString("descricao"));
        atividade.setEvento(evento);

        return atividade;
    }
}
