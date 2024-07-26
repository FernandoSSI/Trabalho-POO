package com.example.gestaodeeventos.model.entities;

public class Colaborador extends User{
    private Atividade atividade;

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

}
