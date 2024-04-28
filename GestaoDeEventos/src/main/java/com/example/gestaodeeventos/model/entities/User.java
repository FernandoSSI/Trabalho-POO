package com.example.gestaodeeventos.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class User implements Serializable {

    private Integer id;
    private String cpf;
    private String cep;
    private String nome;
    private String email;
    private String senha;
    private Date data_nascimento;
    private Integer organizador = 0;

    public User(){

    }

    public User(Integer id, String cpf, String cep, String nome, String email, String senha, Date data_nascimento) {
        this.id = id;
        this.cpf = cpf;
        this.cep = cep;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.data_nascimento = data_nascimento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public Integer getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Integer organizador) {
        this.organizador = organizador;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(cpf, user.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf);
    }
}
