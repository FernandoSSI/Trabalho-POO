package com.example.gestaodeeventos.model.dao;

import com.example.gestaodeeventos.db.DB;
import com.example.gestaodeeventos.model.dao.impl.*;

public class DaoFactory {

    public static UserDao createUserDao() {
        return new UserDaoJDBC(DB.getConnection());
    }

    public static OrganizadorDao createOrganizadorDao() {
        return new OrganizadorDaoJDBC(DB.getConnection());
    }

    public static ColaboradorDao createColaboradorDao(){
        return new ColaboradorDaoJDBC(DB.getConnection());
    }

    public  static EventoDao createEventoDao(){
        return new EventoDaoJDBC(DB.getConnection());
    }

    public static InstituicaoDao createInstituicaoDao(){
        return new InstituicaoDaoJDBC(DB.getConnection());
    }

    public static CategoriaDao createCategoriaDao(){
        return new CategoriaDaoJDBC(DB.getConnection());
    }

    public static AtividadeDao createAtividadeDao(){
        return new AtividadeDaoJDBC(DB.getConnection());
    }

    public static InscricaoDaoJDBC createInscricaoDao(){
        return new InscricaoDaoJDBC(DB.getConnection());
    }

    public static FeedbackDaoJDBC createFeedbackDao(){
        return new FeedbackDaoJDBC(DB.getConnection());
    }


    public static CertificadoDao createCertificadoDao() {
        return new CertificadoDaoJDBC(DB.getConnection());
    }
}