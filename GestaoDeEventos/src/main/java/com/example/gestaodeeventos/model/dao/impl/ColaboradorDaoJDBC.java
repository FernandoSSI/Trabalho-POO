package com.example.gestaodeeventos.model.dao.impl;

import com.example.gestaodeeventos.db.DB;
import com.example.gestaodeeventos.db.DbException;
import com.example.gestaodeeventos.model.dao.ColaboradorDao;
import com.example.gestaodeeventos.model.entities.Colaborador;
import com.example.gestaodeeventos.model.entities.User;
import com.example.gestaodeeventos.model.services.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDaoJDBC implements ColaboradorDao {

    private Connection con;

    public ColaboradorDaoJDBC(Connection con) {
        this.con = con;
    }

    @Override
    public void insert(Colaborador obj) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(
                    "INSERT INTO colaborador (id) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setInt(1, obj.getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Colaborador obj) {

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("DELETE FROM colaborador WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Colaborador findById(Integer id) {
        PreparedStatement st = null;
        ResultSet resultSet = null;
        try {
            st = con.prepareStatement("SELECT * FROM colaborador WHERE id = ?");
            st.setInt(1, id);
            resultSet = st.executeQuery();

            if (resultSet.next()) {
                UserService userService = new UserService();
                User user = userService.findById(id);
                if (user == null) {
                    return null;
                }

                Colaborador colaborador = new Colaborador();
                colaborador.setId(user.getId());
                colaborador.setNome(user.getNome());
                colaborador.setEmail(user.getEmail());
                colaborador.setSenha(user.getSenha());
                colaborador.setCpf(user.getCpf());
                colaborador.setCep(user.getCep());
                colaborador.setData_nascimento(user.getData_nascimento());

                return colaborador;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Colaborador> findAllByAtividadeId(Integer atividadeId) {
        List<Colaborador> colaboradores = new ArrayList<>();
        String sql = "SELECT c.* FROM colaborador c " +
                "INNER JOIN atividade_colaborador ac ON c.id = ac.colaborador_id " +
                "WHERE ac.atividade_id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, atividadeId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UserService userService = new UserService();
                User user = userService.findById(rs.getInt("id"));
                if (user == null) {
                    continue;  // Pula para o próximo registro se o usuário não for encontrado
                }
                Colaborador colaborador = new Colaborador();
                colaborador.setId(user.getId());
                colaborador.setNome(user.getNome());
                colaborador.setEmail(user.getEmail());
                colaborador.setSenha(user.getSenha());
                colaborador.setCpf(user.getCpf());
                colaborador.setCep(user.getCep());
                colaborador.setData_nascimento(user.getData_nascimento());

                colaboradores.add(colaborador);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

        return colaboradores;
    }

    @Override
    public List<Colaborador> findAll() {
        PreparedStatement st = null;
        ResultSet resultSet = null;
        try {
            st = con.prepareStatement("SELECT * FROM colaborador");
            resultSet = st.executeQuery();

            List<Colaborador> colaboradores = new ArrayList<>();
            while (resultSet.next()) {
                Colaborador colaborador = new Colaborador();
                colaborador.setId(resultSet.getInt("id"));
                colaboradores.add(colaborador);
            }
            return colaboradores;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Colaborador> findAllByEventId(int eventId) {
        List<Colaborador> colaboradores = new ArrayList<>();
        String sql = "SELECT c.* FROM colaborador c " +
                "INNER JOIN atividade_colaborador ac ON c.id = ac.colaborador_id " +
                "INNER JOIN atividade a ON ac.atividade_id = a.id " +
                "WHERE a.evento_id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UserService userService = new UserService();
                User user = userService.findById(rs.getInt("id"));
                if (user == null) {
                    return null;
                }
                Colaborador colaborador = new Colaborador();
                colaborador.setId(user.getId());
                colaborador.setNome(user.getNome());
                colaborador.setEmail(user.getEmail());
                colaborador.setSenha(user.getSenha());
                colaborador.setCpf(user.getCpf());
                colaborador.setCep(user.getCep());
                colaborador.setData_nascimento(user.getData_nascimento());

                colaboradores.add(colaborador);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

        return colaboradores;
    }

}
