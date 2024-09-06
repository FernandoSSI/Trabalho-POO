package com.example.gestaodeeventos.model.dao.impl;

import com.example.gestaodeeventos.model.dao.InscricaoDao;
import com.example.gestaodeeventos.model.entities.Evento;
import com.example.gestaodeeventos.model.entities.Inscricao;
import com.example.gestaodeeventos.model.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InscricaoDaoJDBC implements InscricaoDao {
    private Connection conn;

    public InscricaoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Inscricao obj) {
        String sql = "INSERT INTO inscricao (user_id, evento_id) VALUES (?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, obj.getParticipante().getId());
            st.setInt(2, obj.getEvento().getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Inscricao obj) {

    }

    @Override
    public void deleteAllByEventId(Integer eventId) {
        String sql = "DELETE FROM inscricao WHERE evento_id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, eventId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting inscriptions by event ID: " + eventId, e);
        }
    }

    @Override
    public void deleteById(Integer userId, Integer eventoId) {
        String sql = "DELETE FROM inscricao WHERE user_id = ? AND evento_id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, userId);
            st.setInt(2, eventoId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Inscricao findById(Integer userId, Integer eventoId) {
        String sql = "SELECT * FROM inscricao WHERE user_id = ? AND evento_id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, userId);
            st.setInt(2, eventoId);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    User user = new UserDaoJDBC(conn).findById(rs.getInt("user_id"));
                    Evento evento = new EventoDaoJDBC(conn).findById(rs.getInt("evento_id"));

                    return new Inscricao(user, evento); // O ID é nulo pois a tabela não possui uma coluna de ID.
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Inscricao> findAllByEventId(Integer eventoId) {
        String sql = "SELECT * FROM inscricao WHERE evento_id = ?";
        List<Inscricao> list = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, eventoId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    User user = new UserDaoJDBC(conn).findById(rs.getInt("user_id"));
                    Evento evento = new EventoDaoJDBC(conn).findById(rs.getInt("evento_id"));

                    list.add(new Inscricao(user, evento));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Inscricao> findAllByUserId(Integer userId) {
        String sql = "SELECT * FROM inscricao WHERE user_id = ?";
        List<Inscricao> list = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, userId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    User user = new UserDaoJDBC(conn).findById(rs.getInt("user_id"));
                    Evento evento = new EventoDaoJDBC(conn).findById(rs.getInt("evento_id"));

                    list.add(new Inscricao(user, evento));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
