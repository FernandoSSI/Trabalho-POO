package com.example.gestaodeeventos.model.entities;

public class Feedback {
    private User usuario;
    private String comentario;
    private Evento evento;

    public Feedback(User usuario, String comentario, Evento evento) {
        setUsuario(usuario);
        setComentario(comentario);
        setEvento(evento);
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario cannot be null");
        }
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        if (comentario == null || comentario.trim().isEmpty()) {
            throw new IllegalArgumentException("Comentario cannot be null or empty");
        }
        this.comentario = comentario;
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
