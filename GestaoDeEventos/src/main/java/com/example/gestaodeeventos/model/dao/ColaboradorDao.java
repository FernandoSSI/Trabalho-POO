package com.example.gestaodeeventos.model.dao;

import com.example.gestaodeeventos.model.entities.Colaborador;

import java.util.List;

public interface ColaboradorDao {
    void insert(Colaborador obj);

    void update(Colaborador obj);
    void deleteById(Integer id);
    Colaborador findById(Integer id);
    List<Colaborador> findAll();

    List<Colaborador> findAllByEventId(int eventId);
}
