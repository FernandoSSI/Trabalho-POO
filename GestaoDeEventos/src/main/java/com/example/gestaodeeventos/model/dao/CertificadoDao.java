package com.example.gestaodeeventos.model.dao;

import com.example.gestaodeeventos.model.entities.Certificado;

import java.util.List;

public interface CertificadoDao {

    void insert(Certificado obj);
    void update(Certificado obj);
    void deleteById(Integer id);
    Certificado findById(Integer id);
    Certificado findByAtividadeAndUser(Integer atividadeId, Integer userId );
    List<Certificado> findAll();
    List<Certificado> findAllByUserId(Integer id);
    List<Certificado> findAllByEventId(Integer id);
    List<Certificado> findAllByAtividadeId(Integer id);



}
