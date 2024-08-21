package com.example.gestaodeeventos.model.dao;

import com.example.gestaodeeventos.model.entities.Categoria;

import java.util.List;

public interface CategoriaDao {

    void insert(Categoria obj);
    void update(Categoria obj);
    void deleteByName(String nome);
    Categoria findByName(String nome);
    List<Categoria> findAll();

}
