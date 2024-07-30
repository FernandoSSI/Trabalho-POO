package com.example.gestaodeeventos.model.entities;

public class Inscricao {
    private long id;

    private User participante;
    private Evento evento;

    public Inscricao(long id, User participante, Evento evento) {
        setId(id);
        setParticipante(participante);
        setEvento(evento);
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

    public User getParticipante() {
        return participante;
    }

    public void setParticipante(User participante) {
        if (participante == null) {
            throw new IllegalArgumentException("Participante cannot be null");
        }
        this.participante = participante;
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
}
