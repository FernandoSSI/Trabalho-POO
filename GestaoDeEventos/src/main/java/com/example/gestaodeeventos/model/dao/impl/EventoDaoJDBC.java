package com.example.gestaodeeventos.model.dao.impl;

import com.example.gestaodeeventos.db.DB;
import com.example.gestaodeeventos.db.DbException;
import com.example.gestaodeeventos.model.dao.EventoDao;
import com.example.gestaodeeventos.model.entities.*;
import com.example.gestaodeeventos.model.services.CategoriaService;
import com.example.gestaodeeventos.model.services.InstituicaoService;
import com.example.gestaodeeventos.model.services.OrganizadorService;

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

                    insertOrganizadores(obj);
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

    public void insertOrganizadores(Evento evento) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO organizador_evento " +
                            "(organizador_id, evento_id) " +
                            "VALUES (?, ?)");

            for (Organizador organizador : evento.getOrganizadores()) {
                st.setInt(1, organizador.getId());
                st.setInt(2, evento.getId());
                st.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Evento obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE evento " +
                            "SET nome = ?, expectativaParticipantes = ?, descricao = ?, mapaURL = ?, data = ?, modalidade = ?, instituicao_id = ?, categoria_nome = ? " +
                            "WHERE id = ?");

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getExpectativaParticipantes());
            st.setString(3, obj.getDescricao());
            st.setString(4, obj.getMapaURL());
            st.setDate(5, new java.sql.Date(obj.getData().getTime()));
            st.setString(6, obj.getModalidade().toString());
            st.setInt(7, obj.getInstituicao().getId());
            st.setString(8, obj.getCategoria().getNome());
            st.setInt(9, obj.getId());

            st.executeUpdate();

            // Atualizar a relação de organizadores
            deleteOrganizadores(obj.getId());
            insertOrganizadores(obj);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            // Deletar relação de organizadores
            deleteOrganizadores(id);

            // Deletar evento
            st = conn.prepareStatement("DELETE FROM evento WHERE id = ?");

            st.setInt(1, id);
            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Nenhum evento encontrado com o ID: " + id);
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteOrganizadores(Integer eventoId) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM organizador_evento WHERE evento_id = ?");

            st.setInt(1, eventoId);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Evento findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM evento WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Evento evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setNome(rs.getString("nome"));
                evento.setExpectativaParticipantes(rs.getInt("expectativaParticipantes"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setMapaURL(rs.getString("mapaURL"));
                evento.setData(rs.getDate("data"));
                evento.setCategoria(CategoriaService.getInstance().findByName(rs.getString("categoria_nome")));
                evento.setInstituicao(InstituicaoService.getInstance().findById(rs.getInt("instituicao_id")));
                evento.setOrganizadores(findOrganizadores(evento.getId()));
                evento.setParticipantes(findParticipantesByEventoId(evento.getId()));

                String modalidadeString = rs.getString("modalidade");
                if (modalidadeString != null) {
                    Modalidade modalidade = Modalidade.valueOf(modalidadeString.toUpperCase());
                    evento.setModalidade(modalidade);
                }

                return evento;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Evento> findAllByOrgId(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT e.* FROM evento e " +
                            "INNER JOIN organizador_evento oe ON e.id = oe.evento_id " +
                            "WHERE oe.organizador_id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            List<Evento> list = new ArrayList<>();

            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setNome(rs.getString("nome"));
                evento.setExpectativaParticipantes(rs.getInt("expectativaParticipantes"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setMapaURL(rs.getString("mapaURL"));
                evento.setData(rs.getDate("data"));
                evento.setCategoria(CategoriaService.getInstance().findByName(rs.getString("categoria_nome")));
                evento.setInstituicao(InstituicaoService.getInstance().findById(rs.getInt("instituicao_id")));
                evento.setOrganizadores(findOrganizadores(evento.getId()));
                evento.setParticipantes(findParticipantesByEventoId(evento.getId()));

                String modalidadeString = rs.getString("modalidade");
                Modalidade modalidade = Modalidade.valueOf(modalidadeString.toUpperCase());
                evento.setModalidade(modalidade);

                list.add(evento);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }



    @Override
    public List<Evento> findAll() {
        String sql = "SELECT * FROM evento";
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Evento> list = new ArrayList<>();

            while (rs.next()) {
                Evento evento = new Evento();
                List<Organizador> organizadores = new ArrayList<Organizador>();

                evento.setId(rs.getInt("id"));
                evento.setNome(rs.getString("nome"));
                evento.setExpectativaParticipantes(rs.getInt("expectativaParticipantes"));
                evento.setDescricao( rs.getString("descricao"));
                evento.setMapaURL(rs.getString("mapaURL"));
                evento.setData(rs.getDate("data"));
                evento.setCategoria(CategoriaService.getInstance().findByName(rs.getString("categoria_nome")));
                evento.setInstituicao(InstituicaoService.getInstance().findById(rs.getInt("instituicao_id")));
                evento.setOrganizadores(findOrganizadores(evento.getId()));
                evento.setParticipantes(findParticipantesByEventoId(evento.getId()));

                String modalidadeString = rs.getString("modalidade");
                Modalidade modalidade = Modalidade.valueOf(modalidadeString.toUpperCase());
                evento.setModalidade(modalidade);

                list.add(evento);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Organizador> findOrganizadores(Integer eventoId){
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            Connection conn = DB.getConnection();
            st = conn.prepareStatement(
                    "SELECT organizador.* FROM organizador " +
                            "INNER JOIN organizador_evento " +
                            "ON organizador.id = organizador_evento.organizador_id " +
                            "WHERE organizador_evento.evento_id = ?");

            st.setInt(1, eventoId);
            rs = st.executeQuery();

            List<Organizador> organizadores = new ArrayList<>();

            while (rs.next()) {
                OrganizadorService organizadorService = OrganizadorService.getInstance();
                Organizador organizador = organizadorService.findById(rs.getInt("id"));
                organizadores.add(organizador);
            }
            return organizadores;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private List<User> findParticipantesByEventoId(int eventoId) {
        String sql = "SELECT u.id, u.nome, u.email " +
                "FROM inscricao i " +
                "INNER JOIN user u ON i.user_id = u.id " +
                "WHERE i.evento_id = ?";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, eventoId);
            try (ResultSet rs = st.executeQuery()) {
                List<User> participantes = new ArrayList<>();
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setNome(rs.getString("nome"));
                    user.setEmail(rs.getString("email"));
                    participantes.add(user);
                }
                return participantes;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
