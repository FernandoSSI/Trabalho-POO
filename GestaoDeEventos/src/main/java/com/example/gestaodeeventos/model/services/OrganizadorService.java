package com.example.gestaodeeventos.model.services;

import com.example.gestaodeeventos.model.dao.DaoFactory;
import com.example.gestaodeeventos.model.dao.OrganizadorDao;
import com.example.gestaodeeventos.model.entities.Organizador;
import com.example.gestaodeeventos.model.entities.User;

import java.util.List;

public class OrganizadorService {

    private OrganizadorDao dao = DaoFactory.createOrganizadorDao();
    private UserService userService = UserService.getInstance(); // Singleton
    private static OrganizadorService instance;

    private OrganizadorService() {}

    public static synchronized OrganizadorService getInstance() {
        if (instance == null) {
            instance = new OrganizadorService();
        }
        return instance;
    }


    public List<Organizador> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Organizador obj) {
        //if(obj.getId() == null) {
        if (findById(obj.getId()) == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public Organizador findById(Integer id) {
        Organizador organizador = dao.findById(id);
        if (organizador != null){
            User user = userService.findById(id);
            organizador.setNome(user.getNome());
            organizador.setCpf(user.getCpf());
            organizador.setCep(user.getCep());
            organizador.setEmail(user.getEmail());
            organizador.setSenha(user.getSenha());
            organizador.setData_nascimento(user.getData_nascimento());
        }

        return organizador;
    }

    public void remove(Organizador obj) {
        dao.deleteById(obj.getId());
    }

    public List<Organizador> findAllByEventId(int eventId) {
        return dao.findAllByEventId(eventId);
    }
}
