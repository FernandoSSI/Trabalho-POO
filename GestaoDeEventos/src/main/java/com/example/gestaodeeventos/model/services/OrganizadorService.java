package com.example.gestaodeeventos.model.services;

import com.example.gestaodeeventos.model.dao.DaoFactory;
import com.example.gestaodeeventos.model.dao.OrganizadorDao;
import com.example.gestaodeeventos.model.entities.Organizador;

import java.util.List;

public class OrganizadorService {

    private OrganizadorDao dao = DaoFactory.createOrganizadorDao();

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
        return dao.findById(id);
    }

    public void remove(Organizador obj) {
        dao.deleteById(obj.getId());
    }
}
