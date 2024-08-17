package com.example.gestaodeeventos.model.dao.impl;

import com.example.gestaodeeventos.model.dao.InstituicaoDao;
import com.example.gestaodeeventos.model.entities.Instituicao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstituicaoDaoJDBC implements InstituicaoDao {


    private Connection conn;

    public InstituicaoDaoJDBC(Connection conn) {
        this.conn = conn;
    }
    @Override
    public void insert(Instituicao obj) {
        String sql = "INSERT INTO instituicao (nome, cnpj, estado, cidade, bairro, rua, numeroResidencial, telefone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, obj.getNome());
            st.setString(2, obj.getCnpj());
            st.setString(3, obj.getEstado());
            st.setString(4, obj.getCidade());
            st.setString(5, obj.getBairro());
            st.setString(6, obj.getRua());
            st.setString(7, obj.getNumeroResidencial());
            st.setString(8, obj.getTelefone());
            st.setString(9, obj.getEmail());

            int affectedRows = st.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        obj.setId(rs.getInt(1));
                    }
                }
            } else {
                throw new SQLException("Error: No rows affected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(Instituicao obj) {
        String sql = "UPDATE instituicao SET nome = ?, cnpj = ?, estado = ?, cidade = ?, bairro = ?, rua = ?, numeroResidencial = ?, telefone = ?, email = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, obj.getNome());
            st.setString(2, obj.getCnpj());
            st.setString(3, obj.getEstado());
            st.setString(4, obj.getCidade());
            st.setString(5, obj.getBairro());
            st.setString(6, obj.getRua());
            st.setString(7, obj.getNumeroResidencial());
            st.setString(8, obj.getTelefone());
            st.setString(9, obj.getEmail());
            st.setInt(10, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM instituicao WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Instituicao findById(Integer id) {
        String sql = "SELECT * FROM instituicao WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateInstituicao(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Instituicao findByName(String name) {
        String sql = "SELECT * FROM instituicao WHERE nome = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, name);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instantiateInstituicao(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Instituicao> findAll() {
        String sql = "SELECT * FROM instituicao";
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            List<Instituicao> list = new ArrayList<>();

            while (rs.next()) {
                list.add(instantiateInstituicao(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Instituicao instantiateInstituicao(ResultSet rs) throws SQLException {
        return new Instituicao(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cnpj"),
                rs.getString("estado"),
                rs.getString("cidade"),
                rs.getString("bairro"),
                rs.getString("rua"),
                rs.getString("numeroResidencial"),
                rs.getString("telefone"),
                rs.getString("email")
        );
    }
}

