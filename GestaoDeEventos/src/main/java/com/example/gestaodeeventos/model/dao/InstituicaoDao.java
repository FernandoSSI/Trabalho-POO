package com.example.gestaodeeventos.model.dao;

import com.example.gestaodeeventos.model.entities.Instituicao;

import java.util.List;

public interface InstituicaoDao {


    void insert(Instituicao obj);
    void update(Instituicao obj);
    void deleteById(Integer id);
    Instituicao findById(Integer id);
    Instituicao findByName(String name);
    List<Instituicao> findAll();
}
