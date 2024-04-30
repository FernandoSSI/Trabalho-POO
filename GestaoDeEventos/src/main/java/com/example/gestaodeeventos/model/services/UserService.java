package com.example.gestaodeeventos.model.services;

import com.example.gestaodeeventos.model.dao.DaoFactory;
import com.example.gestaodeeventos.model.dao.UserDao;
import com.example.gestaodeeventos.model.entities.User;

import java.util.List;

public class UserService {

    private UserDao dao = DaoFactory.createUserDao();

    public List<User> findAll() {
        return dao.findAll();
    }

    public User findByEmailAndPassword(String email, String senha){
        User user = dao.findByEmailAndPassword(email, senha);

        if (user != null){
            return user;
        }
        return null;
    }



    public void saveOrUpdate(User obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        }
        else {
            dao.update(obj);
        }
    }

    public void remove(User obj) {
        dao.deleteById(obj.getId());
    }
}
