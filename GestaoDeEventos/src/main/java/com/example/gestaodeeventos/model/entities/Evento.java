package com.example.gestaodeeventos.model.entities;

import java.util.Date;
import java.util.List;

public class Evento {
    private long id;
    private String nome;
    private int expectativaParticipantes;
    private String mapaURL;
    private Modalidade modalidade;
    private Instituicao instituicao;
    private Categoria categoria;
    private List<Organizador> Organizadores;
    private List<User> participantes;
    private Date data;

    public Evento(long id, String nome, int expectativaParticipantes, String mapaURL, Modalidade modalidade, Instituicao instituicao, Categoria categoria, List<Organizador> organizadores, List<User> participantes, Date data) {
        this.id = id;
        this.nome = nome;
        this.expectativaParticipantes = expectativaParticipantes;
        this.mapaURL = mapaURL;
        this.modalidade = modalidade;
        this.instituicao = instituicao;
        this.categoria = categoria;
        Organizadores = organizadores;
        this.participantes = participantes;
        this.data = data;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public int getExpectativaParticipantes(){
        return expectativaParticipantes;
    }

    public void setExpectativaParticipantes(int expectativaParticipantes){
        this.expectativaParticipantes = expectativaParticipantes;
    }

    public String getMapaURL(){
        return mapaURL;
    }

    public void setMapaURL(String mapaURL){
        this.mapaURL = mapaURL;
    }

    public Modalidade getModalidade(){
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade){
        this.modalidade = modalidade;
    }

    public Instituicao getInstituicao(){
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao){
        this.instituicao = instituicao;
    }

    public Categoria getCategoria(){
        return categoria;
    }

    public void setCategoria(Categoria categoria){
        this.categoria = categoria;
    }

    public List<Organizador> getOrganizadores() {
        return Organizadores;
    }

    public void setOrganizadores(List<Organizador> organizadores) {
        Organizadores = organizadores;
    }

    public List<User> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<User> participantes) {
        this.participantes = participantes;
    }

    public Date getData(){
        return data;
    }

    public void setData(Date data){
        this.data = data;
    }

}
