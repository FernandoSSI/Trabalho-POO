package com.example.gestaodeeventos.model.entities;

import java.util.Date;
import java.util.List;

public class Organizador extends User {

    private List<Evento> eventosOrganizados;

    public Organizador(int telefone, List<Evento> eventosOrganizados) {
        setEventosOrganizados(eventosOrganizados);
    }

    public Organizador(Integer id, String cpf, String cep, String nome, String email, String senha, Date data_nascimento, List<Evento> eventos, List<Evento> eventosOrganizados) {
        super(id, cpf, cep, nome, email, senha, data_nascimento, eventos);
        setEventosOrganizados(eventosOrganizados);
    }

    public Organizador() {
    }

    public List<Evento> getEventosOrganizados() {
        return eventosOrganizados;
    }

    public void setEventosOrganizados(List<Evento> eventosOrganizados) {
        if (eventosOrganizados == null) {
            throw new IllegalArgumentException("Eventos organizados cannot be null");
        }
        this.eventosOrganizados = eventosOrganizados;
    }



}

