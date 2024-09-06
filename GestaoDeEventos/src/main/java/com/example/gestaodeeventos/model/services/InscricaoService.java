package com.example.gestaodeeventos.model.services;

import com.example.gestaodeeventos.model.dao.DaoFactory;
import com.example.gestaodeeventos.model.dao.EventoDao;
import com.example.gestaodeeventos.model.dao.InscricaoDao;
import com.example.gestaodeeventos.model.entities.Inscricao;

import java.util.List;

public class InscricaoService {

    private InscricaoDao dao = DaoFactory.createInscricaoDao();

    public void createInscricao(Inscricao inscricao) {
        dao.insert(inscricao);
    }

    public void updateInscricao(Inscricao inscricao) {
        dao.update(inscricao);
    }

    public void deleteInscricaoById(Integer userId, Integer eventoId) {
        dao.deleteById(userId, eventoId);
    }

    public Inscricao findInscricaoById(Integer userId, Integer eventoId) {
        return dao.findById(userId, eventoId);
    }

    public List<Inscricao> findAllInscricoesByEventId(Integer eventId) {
        return dao.findAllByEventId(eventId);
    }

    public List<Inscricao> findAllInscricoesByUserId(Integer userId) {
        return dao.findAllByUserId(userId);
    }

    public void deleteAllInscricoesByEventId(Integer eventId) {
        dao.deleteAllByEventId(eventId);
    }
}
