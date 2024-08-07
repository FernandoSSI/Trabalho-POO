package com.example.gestaodeeventos.model.dao.impl;

import com.example.gestaodeeventos.db.DB;
import com.example.gestaodeeventos.db.DbException;
import com.example.gestaodeeventos.model.dao.OrganizadorDao;
import com.example.gestaodeeventos.model.entities.Organizador;
import com.example.gestaodeeventos.model.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizadorDaoJDBC implements OrganizadorDao {

    private Connection con;

    public OrganizadorDaoJDBC(Connection con) {
        this.con = con;
    }

    @Override
    public void insert(Organizador obj) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(
                    "INSERT INTO organizador (id) VALUES (?)",
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
    public void update(Organizador obj) {
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement("DELETE FROM organizador WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Organizador findById(Integer id) {
        PreparedStatement st = null;
        ResultSet resultSet = null;
        try {
            st = con.prepareStatement("SELECT * FROM organizador WHERE id = ?");
            st.setInt(1, id);
            resultSet = st.executeQuery();

            if (resultSet.next()) {
                Organizador organizador = new Organizador();
                organizador.setId(resultSet.getInt("id"));
                return organizador;
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
    public List<Organizador> findAll() {
        PreparedStatement st = null;
        ResultSet resultSet = null;
        try {
            st = con.prepareStatement("SELECT * FROM organizador");
            resultSet = st.executeQuery();

            List<Organizador> organizadores = new ArrayList<>();
            while (resultSet.next()) {
                Organizador organizador = new Organizador();
                organizador.setId(resultSet.getInt("id"));
                organizadores.add(organizador);
            }
            return organizadores;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(resultSet);
        }
    }

    private Organizador instantiateOrganizador(ResultSet resultSet) throws SQLException {
        Organizador organizador = new Organizador();
        organizador.setId(resultSet.getInt("id"));
        organizador.setNome(resultSet.getString("nome"));
        organizador.setEmail(resultSet.getString("email"));
        organizador.setSenha(resultSet.getString("senha"));
        organizador.setCpf(resultSet.getString("cpf"));
        organizador.setCep(resultSet.getString("cep"));
        organizador.setData_nascimento(resultSet.getDate("data_nascimento"));
        return organizador;
    }
}
