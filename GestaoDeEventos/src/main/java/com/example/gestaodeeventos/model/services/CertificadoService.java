package com.example.gestaodeeventos.model.services;

import com.example.gestaodeeventos.model.dao.CertificadoDao;
import com.example.gestaodeeventos.model.dao.DaoFactory;
import com.example.gestaodeeventos.model.entities.Certificado;

import java.util.List;

public class CertificadoService {

    private CertificadoDao dao = DaoFactory.createCertificadoDao();
    private static CertificadoService instance;

    private CertificadoService() {}

    public static synchronized CertificadoService getInstance() {
        if (instance == null) {
            instance = new CertificadoService();
        }
        return instance;
    }

    public void saveOrUpdate(Certificado obj) {
        if (obj.getId() == null) {
            System.out.println("service:" + obj.getAtividade().getTitulo());
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public void remove(Certificado obj) {
        dao.deleteById(obj.getId());
    }

    public Certificado findById(Integer id) {
        return dao.findById(id);
    }

    public Certificado findByAtividadeAndUser(Integer atividadeId, Integer userId) {
        return dao.findByAtividadeAndUser(atividadeId, userId);
    }

    public List<Certificado> findAll() {
        return dao.findAll();
    }

    public List<Certificado> findAllByUserId(Integer userId) {
        return dao.findAllByUserId(userId);
    }

    public List<Certificado> findAllByEventId(Integer eventId) {
        return dao.findAllByEventId(eventId);
    }

    public List<Certificado> findAllByAtividadeId(Integer atividadeId) {
        return dao.findAllByAtividadeId(atividadeId);
    }
}
