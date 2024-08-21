package com.example.gestaodeeventos.model.services;

import com.example.gestaodeeventos.model.dao.CategoriaDao;
import com.example.gestaodeeventos.model.dao.DaoFactory;
import com.example.gestaodeeventos.model.entities.Categoria;

import java.util.List;

public class CategoriaService {
    private CategoriaDao dao = DaoFactory.createCategoriaDao();

    public List<Categoria> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Categoria obj) {
        if (findByName(obj.getNome()) == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public Categoria findByName(String nome){
        return dao.findByName(nome);
    }
}
