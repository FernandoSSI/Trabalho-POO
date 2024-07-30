package com.example.gestaodeeventos.model.entities;

public class Sala {
    private int numero;
    private String nome;

    private Instituicao instituicao;

    public Sala(Instituicao instituicao, int numero, String nome) {
        setInstituicao(instituicao);
        setNumero(numero);
        setNome(nome);
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        if (instituicao == null) {
            throw new IllegalArgumentException("Instituicao cannot be null");
        }
        this.instituicao = instituicao;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Numero must be greater than zero");
        }
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome cannot be null or empty");
        }
        this.nome = nome;
    }
}
