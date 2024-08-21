package com.example.gestaodeeventos.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class User {

    private Integer id;
    private String cpf;
    private String cep;
    private String nome;
    private String email;
    private String senha;
    private Date data_nascimento;

    private List<Evento> eventos;

    public User() {
    }

    public User(Integer id, String cpf, String cep, String nome, String email, String senha, Date data_nascimento, List<Evento> eventos) {
        setId(id);
        setCpf(cpf);
        setCep(cep);
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setData_nascimento(data_nascimento);
        setEventos(eventos);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero");
        }
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF must be 11 digits");
        }
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        if (cep == null || !cep.matches("\\d{8}")) {
            throw new IllegalArgumentException("CEP must be 8 digits");
        }
        this.cep = cep;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matcher(email).matches()) {
            throw new IllegalArgumentException("Email is not valid");
        }
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.length() < 3) {
            throw new IllegalArgumentException("Senha must be at least 8 characters long");
        }
        this.senha = senha;
    }

    public Date getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Date data_nascimento) {
        if (data_nascimento == null || data_nascimento.after(new Date())) {
            throw new IllegalArgumentException("Data de nascimento must be a date in the past");
        }
        this.data_nascimento = data_nascimento;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        if (eventos == null) {
            throw new IllegalArgumentException("Eventos cannot be null");
        }
        this.eventos = eventos;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

