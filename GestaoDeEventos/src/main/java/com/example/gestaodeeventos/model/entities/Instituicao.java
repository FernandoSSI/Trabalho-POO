package com.example.gestaodeeventos.model.entities;

public class Instituicao {
    private long id;
    private String nome;
    private long cnpj;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private int numeroResidencial;
    private int telefone;
    private String email;

    public Instituicao(long id, String nome, long cnpj, String estado, String cidade, String bairro, String rua, int numeroResidencial, int telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numeroResidencial = numeroResidencial;
        this.telefone = telefone;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getCnpj() {
        return cnpj;
    }

    public void setCnpj(long cnpj) {
        this.cnpj = cnpj;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumeroResidencial() {
        return numeroResidencial;
    }

    public void setNumeroResidencial(int numeroResidencial) {
        this.numeroResidencial = numeroResidencial;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
