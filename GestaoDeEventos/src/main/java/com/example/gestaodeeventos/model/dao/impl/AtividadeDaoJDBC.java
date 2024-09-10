package com.example.gestaodeeventos.model.dao.impl;

import com.example.gestaodeeventos.model.dao.AtividadeDao;
import com.example.gestaodeeventos.model.entities.Atividade;
import com.example.gestaodeeventos.model.entities.Colaborador;
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
    public int insert(Atividade obj) {
        String sqlAtividade = "INSERT INTO atividade (titulo, data, local, descricao, evento_id) VALUES (?, ?, ?, ?, ?)";
        String sqlAtividadeColaborador = "INSERT INTO atividade_colaborador (atividade_id, colaborador_id) VALUES (?, ?)";
        int generatedId = -1;

        try (PreparedStatement stAtividade = conn.prepareStatement(sqlAtividade, Statement.RETURN_GENERATED_KEYS)) {
            // Inserir a atividade
            stAtividade.setString(1, obj.getTitulo());
            stAtividade.setDate(2, new java.sql.Date(obj.getData().getTime()));
            stAtividade.setString(3, obj.getLocal());
            stAtividade.setString(4, obj.getDescricao());
            stAtividade.setInt(5, obj.getEvento().getId());

            int affectedRows = stAtividade.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stAtividade.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                        obj.setId(generatedId); // Atualizar o objeto Atividade com o ID gerado
                    }
                }

                // Inserir as relações na tabela atividade_colaborador
                try (PreparedStatement stAtividadeColaborador = conn.prepareStatement(sqlAtividadeColaborador)) {
                    for (Colaborador colaborador : obj.getColaboradores()) {
                        stAtividadeColaborador.setInt(1, generatedId);
                        stAtividadeColaborador.setInt(2, colaborador.getId());
                        stAtividadeColaborador.addBatch(); // Adiciona a inserção no batch
                    }
                    stAtividadeColaborador.executeBatch(); // Executa todas as inserções em um único batch
                }
            } else {
                throw new SQLException("Failed to insert atividade, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return generatedId;
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
