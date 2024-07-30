package com.example.gestaodeeventos.model.entities;

import java.util.regex.Pattern;

public class Instituicao {
    private long id;
    private String nome;
    private String cnpj;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private int numeroResidencial;
    private int telefone;
    private String email;

    public Instituicao(long id, String nome, String cnpj, String estado, String cidade, String bairro, String rua, int numeroResidencial, int telefone, String email) {
        setId(id);
        setNome(nome);
        setCnpj(cnpj);
        setEstado(estado);
        setCidade(cidade);
        setBairro(bairro);
        setRua(rua);
        setNumeroResidencial(numeroResidencial);
        setTelefone(telefone);
        setEmail(email);
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome cannot be null or empty");
        }
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        if (cnpj == null || !cnpj.matches("\\d{14}")) {
            throw new IllegalArgumentException("CNPJ must be 14 digits");
        }
        this.cnpj = cnpj;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("Estado cannot be null or empty");
        }
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        if (cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade cannot be null or empty");
        }
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        if (bairro == null || bairro.trim().isEmpty()) {
            throw new IllegalArgumentException("Bairro cannot be null or empty");
        }
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        if (rua == null || rua.trim().isEmpty()) {
            throw new IllegalArgumentException("Rua cannot be null or empty");
        }
        this.rua = rua;
    }

    public int getNumeroResidencial() {
        return numeroResidencial;
    }

    public void setNumeroResidencial(int numeroResidencial) {
        if (numeroResidencial <= 0) {
            throw new IllegalArgumentException("Numero residencial must be greater than zero");
        }
        this.numeroResidencial = numeroResidencial;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        if (telefone <= 0) {
            throw new IllegalArgumentException("Telefone must be a positive number");
        }
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matcher(email).matches()) {
            throw new IllegalArgumentException("Email is not valid");
        }
        this.email = email;
    }
}
