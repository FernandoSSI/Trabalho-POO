package com.example.gestaodeeventos.model.entities;

import java.util.Date;
import java.util.List;

public class Organizador extends User {
    private int telefone;

    private List<Evento> eventosOrganizados;

    public Organizador(int telefone, List<Evento> eventosOrganizados) {
        setTelefone(telefone);
        setEventosOrganizados(eventosOrganizados);
    }

    public Organizador(Integer id, String cpf, String cep, String nome, String email, String senha, Date data_nascimento, List<Evento> eventos, int telefone, List<Evento> eventosOrganizados) {
        super(id, cpf, cep, nome, email, senha, data_nascimento, eventos);
        setTelefone(telefone);
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

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        if (telefone <= 0) {
            throw new IllegalArgumentException("Telefone must be a positive number");
        }
        this.telefone = telefone;
    }
}

