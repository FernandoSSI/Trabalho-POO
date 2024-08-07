package com.example.gestaodeeventos.model.dao;

import com.example.gestaodeeventos.model.entities.Organizador;

import java.util.List;

public interface OrganizadorDao {

    void insert(Organizador obj);
    void update(Organizador obj);
    void deleteById(Integer id);
    Organizador findById(Integer id);
    List<Organizador> findAll();
}
