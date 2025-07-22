package com.example.gestaodeeventos.model.dao.impl;

import com.example.gestaodeeventos.model.dao.FeedbackDao;
import com.example.gestaodeeventos.model.entities.Evento;
import com.example.gestaodeeventos.model.entities.Feedback;
import com.example.gestaodeeventos.model.entities.User;
import com.example.gestaodeeventos.model.services.EventoService;
import com.example.gestaodeeventos.model.services.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDaoJDBC implements FeedbackDao {

    private Connection conn;

    public FeedbackDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Feedback obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO feedback (user_id, evento_id, comentario) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setInt(1, obj.getUsuario().getId());
            st.setInt(2, obj.getEvento().getId());
            st.setString(3, obj.getComentario());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    obj.setId(rs.getInt(1));
                }
                System.out.println("Feedback criado");
                rs.close();
            } else {
                throw new SQLException("Unexpected error: No rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(st);
        }
    }

    @Override
    public void update(Feedback obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE feedback SET user_id = ?, evento_id = ?, comentario = ? WHERE id = ?");

            st.setInt(1, obj.getUsuario().getId());
            st.setInt(2, obj.getEvento().getId());
            st.setString(3, obj.getComentario());
            st.setInt(4, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM feedback WHERE id = ?");

            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(st);
        }
    }

    @Override
    public Feedback findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT feedback.*, user.id as userId, evento.id as eventoId " +
                            "FROM feedback " +
                            "INNER JOIN user ON feedback.user_id = user.id " +
                            "INNER JOIN evento ON feedback.evento_id = evento.id " +
                            "WHERE feedback.id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Feedback obj = instantiateFeedback(rs);
                return obj;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResultSet(rs);
            closeStatement(st);
        }
    }

    @Override
    public List<Feedback> findAllByEventId(Integer eventoId) {
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT f.*, u.nome AS usuario_nome " +
                "FROM feedback f " +
                "INNER JOIN user u ON f.user_id = u.id " +
                "WHERE f.evento_id = ?";

        try  {
            st = conn.prepareStatement(sql);
            st.setInt(1, eventoId);
             rs = st.executeQuery();
            List<Feedback> feedbacks = new ArrayList<>();

            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id"));
                feedback.setUsuario(UserService.getInstance().findById(rs.getInt("user_id")));
                feedback.setEvento(EventoService.getInstance().findById(rs.getInt("evento_id")));
                feedback.setComentario(rs.getString("comentario"));

                feedbacks.add(feedback);
            }
            return feedbacks;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResultSet(rs);
            closeStatement(st);
        }
    }

    private Feedback instantiateFeedback(ResultSet rs) throws SQLException {
        User user = UserService.getInstance().findById(rs.getInt("user_id"));

        Evento evento = EventoService.getInstance().findById(rs.getInt("eventoId"));

        Feedback obj = new Feedback();
        obj.setId(rs.getInt("id"));
        obj.setUsuario(user);
        obj.setEvento(evento);
        obj.setComentario(rs.getString("comentario"));
        return obj;
    }

    private void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
