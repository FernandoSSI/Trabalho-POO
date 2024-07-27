package com.example.gestaodeeventos.model.entities;

import java.util.Date;
import java.util.List;

public class Colaborador extends User{
    private List<Atividade> atividades;

    public Colaborador(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    public Colaborador(Integer id, String cpf, String cep, String nome, String email, String senha, Date data_nascimento, Integer organizador, List<Evento> eventos, List<Atividade> atividades) {
        super(id, cpf, cep, nome, email, senha, data_nascimento, organizador, eventos);
        this.atividades = atividades;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }
}
