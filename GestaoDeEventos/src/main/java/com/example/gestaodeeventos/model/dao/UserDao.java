package com.example.gestaodeeventos.model.dao;

import com.example.gestaodeeventos.model.entities.User;

import java.util.List;

public interface UserDao {

    void insert(User obj);
    void update(User obj);
    void deleteById(Integer id);
    User findById(Integer id);
    List<User> findAll();
}
