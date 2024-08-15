package com.example.gestaodeeventos.model.entities;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class Evento {
    private long id;
    private String nome; //
    private int expectativaParticipantes; //
    private String mapaURL; //
    private Date data; //
    private String descricao; //

    private Modalidade modalidade; //
    private Instituicao instituicao; //
    private Categoria categoria; //
    private List<Organizador> organizadores;//
    private List<User> participantes;

    public Evento(long id, String nome, int expectativaParticipantes, String descricao, String mapaURL, Modalidade modalidade, Instituicao instituicao, Categoria categoria, List<Organizador> organizadores, List<User> participantes, Date data) {
        setId(id);
        setNome(nome);
        setExpectativaParticipantes(expectativaParticipantes);
        setMapaURL(mapaURL);
        setModalidade(modalidade);
        setInstituicao(instituicao);
        setCategoria(categoria);
        setOrganizadores(organizadores);
        setParticipantes(participantes);
        setData(data);
        setDescricao(descricao);
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

    public int getExpectativaParticipantes() {
        return expectativaParticipantes;
    }

    public void setExpectativaParticipantes(int expectativaParticipantes) {
        if (expectativaParticipantes <= 0) {
            throw new IllegalArgumentException("Expectativa de participantes must be greater than zero");
        }
        this.expectativaParticipantes = expectativaParticipantes;
    }

    public String getMapaURL() {
        return mapaURL;
    }

    public void setMapaURL(String mapaURL) {
        if (mapaURL == null || mapaURL.trim().isEmpty() || !Pattern.compile("^(http|https)://.*$").matcher(mapaURL).matches()) {
            throw new IllegalArgumentException("Mapa URL must be a valid URL");
        }
        this.mapaURL = mapaURL;
    }

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        if (modalidade == null) {
            throw new IllegalArgumentException("Modalidade cannot be null");
        }
        this.modalidade = modalidade;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria cannot be null");
        }
        this.categoria = categoria;
    }

    public List<Organizador> getOrganizadores() {
        return organizadores;
    }

    public void setOrganizadores(List<Organizador> organizadores) {
        if (organizadores == null || organizadores.isEmpty()) {
            throw new IllegalArgumentException("Organizadores cannot be null or empty");
        }
        this.organizadores = organizadores;
    }

    public List<User> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<User> participantes) {
        this.participantes = participantes;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        if (data == null || data.before(new Date())) {
            throw new IllegalArgumentException("Data must be a future date");
        }
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
