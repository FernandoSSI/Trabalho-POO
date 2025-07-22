package com.example.gestaodeeventos.model.services;

import com.example.gestaodeeventos.model.dao.DaoFactory;
import com.example.gestaodeeventos.model.dao.EventoDao;
import com.example.gestaodeeventos.model.entities.Evento;

import java.util.List;

public class EventoService {


    private EventoDao dao = DaoFactory.createEventoDao();
    private static EventoService instance;

    private EventoService() {}

    public static synchronized EventoService getInstance() {
        if (instance == null) {
            instance = new EventoService();
        }
        return instance;
    }

    public List<Evento> findAllByOrgId(Integer id) {
        return dao.findAllByOrgId(id);
    }

    public void saveOrUpdate(Evento obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public Evento findById(Integer id){
        return dao.findById(id);
    }

    public List<Evento> findAll(){
        return dao.findAll();
    }

    public void deleteById(Integer id){
        dao.deleteById(id);
    }
}
