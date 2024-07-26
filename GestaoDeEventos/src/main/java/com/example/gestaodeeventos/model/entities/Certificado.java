package com.example.gestaodeeventos.model.entities;

public class Certificado {
    private long id;
    private Inscricao inscricao;
    private Atividade atividade;

    public Certificado(long id, Inscricao inscricao, Atividade atividade) {
        this.id = id;
        this.inscricao = inscricao;
        this.atividade = atividade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public Atividade getAtividade(){
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }
}
