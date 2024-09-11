package com.example.gestaodeeventos.model.entities;

public class Certificado {
    private Integer id;

    private Inscricao inscricao;
    private Atividade atividade;

    public Certificado(){

    }

    public Certificado(Integer id, Inscricao inscricao, Atividade atividade) {
        setId(id);
        setInscricao(inscricao);
        setAtividade(atividade);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero");
        }
        this.id = id;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        if (inscricao == null) {
            throw new IllegalArgumentException("Inscricao cannot be null");
        }
        this.inscricao = inscricao;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        if (atividade == null) {
            throw new IllegalArgumentException("Atividade cannot be null");
        }
        this.atividade = atividade;
    }
}
