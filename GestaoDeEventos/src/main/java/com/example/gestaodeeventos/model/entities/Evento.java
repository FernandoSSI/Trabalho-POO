package com.example.gestaodeeventos.model.entities;

import java.util.Date;

public class Evento {
    private long id;
    private String nome;
    private int expectativaParticipantes;
    private String mapaURL;
    private Modalidade modalidade;
    private Instituicao instituicao;
    private Categoria categoria;
    //private UsuarioOrganizador usuarioOrganizador;
    private Date data;

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

    /*public UsuarioOrganizador getUsuarioOrganizador(){
        return usuarioOrganizador;
    }

    public void setUsuarioOrganizador(UsuarioOrganizador usuarioOrganizador){
        this.usuarioOrganizador = usuarioOrganizador;
    }*/

    public Date getData(){
        return data;
    }

    public void setData(Date data){
        this.data = data;
    }

}
