package com.example.gestaodeeventos.model.entities;

import java.util.Date;
import java.util.List;

public class Colaborador extends User {
    private List<Atividade> atividades;

    public Colaborador(List<Atividade> atividades) {
        setAtividades(atividades);
    }

    public Colaborador(Integer id, String cpf, String cep, String nome, String email, String senha, Date data_nascimento, List<Evento> eventos, List<Atividade> atividades) {
        super(id, cpf, cep, nome, email, senha, data_nascimento, eventos);
        setAtividades(atividades);
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        if (atividades == null || atividades.isEmpty()) {
            throw new IllegalArgumentException("Atividades cannot be null or empty");
        }
        this.atividades = atividades;
    }
}
