package com.example.gestaodeeventos.model.entities;

public class Certificado {
    private long id;

    private Inscricao inscricao;
    private Atividade atividade;

    public Certificado(long id, Inscricao inscricao, Atividade atividade) {
        setId(id);
        setInscricao(inscricao);
        setAtividade(atividade);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
