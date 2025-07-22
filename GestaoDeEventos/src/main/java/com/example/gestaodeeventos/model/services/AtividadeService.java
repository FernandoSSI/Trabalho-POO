package com.example.gestaodeeventos.model.services;

import com.example.gestaodeeventos.model.dao.AtividadeDao;
import com.example.gestaodeeventos.model.dao.DaoFactory;
import com.example.gestaodeeventos.model.entities.Atividade;

import java.util.List;

public class AtividadeService {
    private AtividadeDao dao = DaoFactory.createAtividadeDao();
    private static AtividadeService instance;

    // Construtor privado para evitar instanciação externa
    private AtividadeService() {}

    // Método estático para obter a instância única
    public static synchronized AtividadeService getInstance() {
        if (instance == null) {
            instance = new AtividadeService();
        }
        return instance;
    }

    public Integer saveOrUpdate(Atividade obj) {
        if (obj.getId() == null) {
            return dao.insert(obj);
        } else {
            dao.update(obj);
            return obj.getId();
        }
    }

    public void remove(Integer id) {
        dao.deleteById(id);
    }

    public Atividade findById(Integer id) {
        return dao.findById(id);
    }

    public List<Atividade> findAll() {
        return dao.findAll();
    }

    public List<Atividade> findByEventoId(Integer eventoId) {
        return dao.findByEventoId(eventoId);
    }
}
