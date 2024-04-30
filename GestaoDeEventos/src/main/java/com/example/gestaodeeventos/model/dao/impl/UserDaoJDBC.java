package com.example.gestaodeeventos.model.dao.impl;

import com.example.gestaodeeventos.db.DB;
import com.example.gestaodeeventos.db.DbException;
import com.example.gestaodeeventos.model.dao.UserDao;
import com.example.gestaodeeventos.model.entities.User;

import java.sql.*;
import java.util.List;

public class UserDaoJDBC implements UserDao {

    private Connection con;

    public UserDaoJDBC(Connection con){
        this.con=con;
    }

    public void insert(User obj) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(
                    "INSERT INTO users "
                            + "(cpf, cep, nome, email, senha, data_nascimento, organizador) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getCpf());
            st.setString(2, obj.getCep());
            st.setString(3, obj.getNome());
            st.setString(4, obj.getEmail());
            st.setString(5, obj.getSenha());
            st.setDate(6, new java.sql.Date(obj.getData_nascimento().getTime()));
            st.setInt(7, obj.getOrganizador());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(User obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    @Override
    public User findByEmailAndPassword(String email, String senha){

        PreparedStatement st = null;
        try{
            st = con.prepareStatement("SELECT * FROM users WHERE email = ? AND senha = ?");
            st.setString(1, email);
            st.setString(2, senha);
            ResultSet resultSet = st.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setNome(resultSet.getString("nome"));
                user.setEmail(resultSet.getString("email"));
                user.setSenha(resultSet.getString("senha"));
                user.setCpf(resultSet.getString("cpf"));
                user.setCep(resultSet.getString("cep"));
                user.setData_nascimento(resultSet.getDate("data_nascimento"));
                user.setOrganizador(resultSet.getInt("organizador"));
                return user;
            }

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}