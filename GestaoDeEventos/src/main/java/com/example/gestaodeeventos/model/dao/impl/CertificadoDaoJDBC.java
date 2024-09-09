package com.example.gestaodeeventos.model.dao.impl;

import com.example.gestaodeeventos.model.dao.CertificadoDao;
import com.example.gestaodeeventos.model.entities.*;
import com.example.gestaodeeventos.model.services.AtividadeService;
import com.example.gestaodeeventos.model.services.EventoService;
import com.example.gestaodeeventos.model.services.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CertificadoDaoJDBC implements CertificadoDao {

    private Connection conn;

    public CertificadoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Certificado obj) {
        String sql = "INSERT INTO certificado (inscricao_user_id, inscricao_evento_id, atividade_id) VALUES (?, ?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, obj.getInscricao().getParticipante().getId());
            st.setInt(2, obj.getInscricao().getEvento().getId());
            st.setInt(3, obj.getAtividade().getId());

            int affectedRows = st.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        obj.setId(rs.getInt(1));
                    }
                }
            } else {
                throw new SQLException("Failed to insert certificado, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Certificado obj) {
        String sql = "UPDATE certificado SET inscricao_user_id = ?, inscricao_evento_id = ?, atividade_id = ? WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, obj.getInscricao().getParticipante().getId());
            st.setInt(2, obj.getInscricao().getEvento().getId());
            st.setInt(3, obj.getAtividade().getId());
            st.setInt(4, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM certificado WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Certificado findById(Integer id) {
        String sql = "SELECT * FROM certificado WHERE id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Inscricao inscricao = instantiateInscricao(rs);
                    Atividade atividade = instantiateAtividade(rs);
                    return new Certificado(rs.getInt("id"), inscricao, atividade);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Certificado findByAtividadeAndUser(Integer atividadeId, Integer userId) {
        return null;
    }

    @Override
    public List<Certificado> findAll() {
        String sql = "SELECT * FROM certificado";

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Certificado> list = new ArrayList<>();

            while (rs.next()) {
                Inscricao inscricao = instantiateInscricao(rs);
                Atividade atividade = instantiateAtividade(rs);
                Certificado obj = new Certificado(rs.getInt("id"), inscricao, atividade);
                list.add(obj);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Certificado> findAllByUserId(Integer userId) {
        String sql = "SELECT * FROM certificado WHERE inscricao_user_id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, userId);

            try (ResultSet rs = st.executeQuery()) {
                List<Certificado> list = new ArrayList<>();

                while (rs.next()) {
                    Inscricao inscricao = instantiateInscricao(rs);
                    Atividade atividade = instantiateAtividade(rs);
                    Certificado obj = new Certificado(rs.getInt("id"), inscricao, atividade);
                    list.add(obj);
                }

                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Certificado> findAllByEventId(Integer eventId) {
        String sql = "SELECT * FROM certificado WHERE inscricao_evento_id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, eventId);

            try (ResultSet rs = st.executeQuery()) {
                List<Certificado> list = new ArrayList<>();

                while (rs.next()) {
                    Inscricao inscricao = instantiateInscricao(rs);
                    Atividade atividade = instantiateAtividade(rs);
                    Certificado obj = new Certificado(rs.getInt("id"), inscricao, atividade);
                    list.add(obj);
                }

                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Certificado> findAllByAtividadeId(Integer atividadeId) {
        String sql = "SELECT * FROM certificado WHERE atividade_id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, atividadeId);

            try (ResultSet rs = st.executeQuery()) {
                List<Certificado> list = new ArrayList<>();

                while (rs.next()) {
                    Inscricao inscricao = instantiateInscricao(rs);
                    Atividade atividade = instantiateAtividade(rs);
                    Certificado obj = new Certificado(rs.getInt("id"), inscricao, atividade);
                    list.add(obj);
                }

                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Inscricao instantiateInscricao(ResultSet rs) throws SQLException {
        Integer userId = rs.getInt("inscricao_user_id");
        Integer eventoId = rs.getInt("inscricao_evento_id");

        User participante = new UserService().findById(userId);
        Evento evento = new EventoService().findById(eventoId);

        return new Inscricao(participante, evento);
    }

    private Atividade instantiateAtividade(ResultSet rs) throws SQLException {
        return new AtividadeService().findById(rs.getInt("atividade_id"));
    }
}
