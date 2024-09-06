package com.example.gestaodeeventos.model.entities;

public class Inscricao {

    private User participante;
    private Evento evento;

    public Inscricao() {

    }

    public Inscricao(User participante, Evento evento) {
        setParticipante(participante);
        setEvento(evento);
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
