package com.example.gestaodeeventos.model.entities;

public class Inscricao {
    private long id;
    private User participante;
    private Evento evento;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getParticipante() {
        return participante;
    }

    public void setParticipante(User participante) {
        this.participante = participante;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
