package com.example.gestaodeeventos.model.entities;

public class Certificado {
    private long id;
    private int inscricao;
    private Atividade atividade;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setInscricao(int inscricao) {
        this.inscricao = inscricao;
    }

    public int getInscricao() {
        return inscricao;
    }

    public Atividade getAtividade(){
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }
}
