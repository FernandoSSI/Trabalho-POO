package com.example.gestaodeeventos.model.entities;

import java.util.Date;
import java.util.List;

public class Atividade {
    private Evento evento;
    private String titulo;
    private Date data;
    private String local;
    private List<Colaborador> colaboradores;
    private Certificado certificado;

    public Atividade(Evento evento, String titulo, Date data, String local, List<Colaborador> colaboradores, Certificado certificado) {
        this.evento = evento;
        this.titulo = titulo;
        this.data = data;
        this.local = local;
        this.colaboradores = colaboradores;
        this.certificado = certificado;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

    public Certificado getCertificado() {
        return certificado;
    }

    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }
}
