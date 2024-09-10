package com.example.gestaodeeventos.model.services;

import com.example.gestaodeeventos.model.dao.ColaboradorDao;
import com.example.gestaodeeventos.model.dao.DaoFactory;
import com.example.gestaodeeventos.model.dao.OrganizadorDao;
import com.example.gestaodeeventos.model.entities.Colaborador;
import com.example.gestaodeeventos.model.entities.Organizador;
import com.example.gestaodeeventos.model.entities.User;

import java.util.List;

public class ColaboradorService {
    private ColaboradorDao dao = DaoFactory.createColaboradorDao();
    private UserService userService = new UserService();

    public List<Colaborador> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Colaborador obj) {
        //if(obj.getId() == null) {
        if (findById(obj.getId()) == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public Colaborador findById(Integer id) {
        Colaborador colaborador = dao.findById(id);
        if (colaborador != null){
            User user = userService.findById(id);
            colaborador.setNome(user.getNome());
            colaborador.setCpf(user.getCpf());
            colaborador.setCep(user.getCep());
            colaborador.setEmail(user.getEmail());
            colaborador.setSenha(user.getSenha());
            colaborador.setData_nascimento(user.getData_nascimento());
        }

        return colaborador;
    }

    public void remove(Colaborador obj) {
        dao.deleteById(obj.getId());
    }


    public List<Colaborador> findAllByEventId(int eventId) {
        return dao.findAllByEventId(eventId);
    }
}
