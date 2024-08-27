package com.example.gestaodeeventos.model.services;

import com.example.gestaodeeventos.model.dao.DaoFactory;
import com.example.gestaodeeventos.model.dao.InstituicaoDao;
import com.example.gestaodeeventos.model.entities.Instituicao;

import java.util.List;

public class InstituicaoService {


    private InstituicaoDao dao = DaoFactory.createInstituicaoDao();

    public List<Instituicao> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Instituicao obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public Instituicao findByName(String nome){
        return dao.findByName(nome);
    }

    public Instituicao findById(Integer id){
        return dao.findById(id);
    }
}
