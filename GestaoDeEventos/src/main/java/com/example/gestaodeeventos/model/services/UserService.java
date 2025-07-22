package com.example.gestaodeeventos.model.services;

import com.example.gestaodeeventos.model.dao.DaoFactory;
import com.example.gestaodeeventos.model.dao.UserDao;
import com.example.gestaodeeventos.model.entities.User;

import java.util.List;

public class UserService {

    private UserDao dao = DaoFactory.createUserDao();
    private static UserService instance;

    private UserService() {}

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public List<User> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(User obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public User findById(Integer id) {
        return dao.findById(id);
    }

    public void remove(User obj) {
        dao.deleteById(obj.getId());
    }

    public User findByEmailAndPassword(String email, String senha) {
        return dao.findByEmailAndPassword(email, senha);
    }

    public List<User> findAllByEventId(int eventId) {
        return dao.findAllByEventId(eventId);
    }


}
