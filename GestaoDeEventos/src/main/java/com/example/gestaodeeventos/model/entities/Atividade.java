package com.example.gestaodeeventos.model.entities;

import java.util.Date;
import java.util.List;

public class Atividade {
    private String titulo;
    private Date data;
    private String local;
    private String descricao;

    private Evento evento;
    private List<Colaborador> colaboradores;
    private Certificado certificado;

    public Atividade(Evento evento, String titulo, Date data, String local, String descricao, List<Colaborador> colaboradores, Certificado certificado) {
        setEvento(evento);
        setTitulo(titulo);
        setData(data);
        setLocal(local);
        setColaboradores(colaboradores);
        setCertificado(certificado);
        setDescricao(descricao);
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        if (evento == null) {
            throw new IllegalArgumentException("Evento cannot be null");
        }
        this.evento = evento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Titulo cannot be null or empty");
        }
        this.titulo = titulo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        if (local != null && local.trim().isEmpty()) {
            throw new IllegalArgumentException("Local cannot be empty if provided");
        }
        this.local = local;
    }

    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<Colaborador> colaboradores) {
        if (colaboradores != null && colaboradores.isEmpty()) {
            throw new IllegalArgumentException("Colaboradores cannot be empty if provided");
        }
        this.colaboradores = colaboradores;
    }

    public Certificado getCertificado() {
        return certificado;
    }

    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
