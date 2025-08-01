package com.example.gestaodeeventos.model.dao.impl;

import com.example.gestaodeeventos.db.DB;
import com.example.gestaodeeventos.db.DbException;
import com.example.gestaodeeventos.model.dao.UserDao;
import com.example.gestaodeeventos.model.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBC implements UserDao {

    private Connection con;

    public UserDaoJDBC(Connection con){
        this.con=con;
    }

    public Integer insert(User obj) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(
                    "INSERT INTO user "
                            + "(cpf, cep, nome, email, senha, data_nascimento) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getCpf());
            st.setString(2, obj.getCep());
            st.setString(3, obj.getNome());
            st.setString(4, obj.getEmail());
            st.setString(5, obj.getSenha());
            st.setDate(6, new java.sql.Date(obj.getData_nascimento().getTime()));

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
        return null;
    }

    @Override
    public void update(User obj) {
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(
                    "UPDATE user SET cpf = ?, cep = ?, nome = ?, email = ?, senha = ?, data_nascimento = ? WHERE id = ?");

            st.setString(1, obj.getCpf());
            st.setString(2, obj.getCep());
            st.setString(3, obj.getNome());
            st.setString(4, obj.getEmail());
            st.setString(5, obj.getSenha());
            st.setDate(6, new java.sql.Date(obj.getData_nascimento().getTime()));
            st.setInt(7, obj.getId());

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = con.prepareStatement("DELETE FROM user WHERE id = ?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public User findById(Integer id) {
        PreparedStatement st = null;
        ResultSet resultSet = null;
        try{
            st = con.prepareStatement("SELECT * FROM user WHERE id = ?");
            st.setInt(1, id);
            resultSet = st.executeQuery();

            if (resultSet.next()) {
                User user = instantiateUser(resultSet);
                return user;
            }
            return null;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String senha){

        PreparedStatement st = null;
        ResultSet resultSet = null;
        try{
            st = con.prepareStatement("SELECT * FROM user WHERE email = ? AND senha = ?");
            st.setString(1, email);
            st.setString(2, senha);
            resultSet = st.executeQuery();

            if (resultSet.next()) {
                User user = instantiateUser(resultSet);
                return user;
            }
            return null;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<User> findAll() {
        PreparedStatement st = null;
        ResultSet resultSet = null;
        try{
            st = con.prepareStatement("SELECT * FROM user ");
            resultSet = st.executeQuery();

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = instantiateUser(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<User> findAllByEventId(int eventId) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.* FROM user u " +
                "INNER JOIN inscricao i ON u.id = i.user_id " +
                "WHERE i.evento_id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = instantiateUser(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

        return users;
    }

    private User instantiateUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setNome(resultSet.getString("nome"));
        user.setEmail(resultSet.getString("email"));
        user.setSenha(resultSet.getString("senha"));
        user.setCpf(resultSet.getString("cpf"));
        user.setCep(resultSet.getString("cep"));
        user.setData_nascimento(resultSet.getDate("data_nascimento"));
        return user;
    }
}