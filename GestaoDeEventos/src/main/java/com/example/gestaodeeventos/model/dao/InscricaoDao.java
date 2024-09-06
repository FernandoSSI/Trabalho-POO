package com.example.gestaodeeventos.model.dao;

import com.example.gestaodeeventos.model.entities.Inscricao;

import java.util.List;

public interface InscricaoDao {

    void insert(Inscricao obj);
    void update(Inscricao obj);
    void deleteAllByEventId(Integer eventId);
    void deleteById(Integer userId, Integer eventoId);
    Inscricao findById(Integer userId, Integer eventoId);

    List<Inscricao> findAllByEventId(Integer id);
    List<Inscricao> findAllByUserId(Integer id);

}
