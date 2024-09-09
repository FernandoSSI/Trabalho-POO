package com.example.gestaodeeventos.model.dao;

import com.example.gestaodeeventos.model.entities.Atividade;

import java.util.List;

public interface AtividadeDao {
    void insert(Atividade obj);
    void update(Atividade obj);
    void deleteById(Integer id);
    Atividade findById(Integer id);
    List<Atividade> findAll();
    List<Atividade> findByEventoId(Integer eventoId);
}
