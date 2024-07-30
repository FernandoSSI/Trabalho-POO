package com.example.gestaodeeventos.model.entities;

public class Categoria {
    private String nome;
    private String descricao;

    public Categoria(String nome, String descricao) {
        setNome(nome);
        setDescricao(descricao);
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao != null && descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descricao cannot be empty if provided");
        }
        this.descricao = descricao;
    }
}
