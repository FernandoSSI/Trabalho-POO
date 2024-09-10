package com.example.gestaodeeventos.model.dao;

import com.example.gestaodeeventos.model.entities.Evento;
import com.example.gestaodeeventos.model.entities.Organizador;

import java.util.List;

public interface EventoDao {
    void insert(Evento obj);
    void update(Evento obj);
    void deleteById(Integer id);
    void deleteOrganizadores(Integer eventoId);
    Evento findById(Integer id);
    List<Evento> findAllByOrgId(Integer id);
    List<Evento> findAll();

}
