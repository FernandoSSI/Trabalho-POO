package com.example.gestaodeeventos.model.entities;

import java.util.Date;
import java.util.List;

public class Organizador extends User{
    private int telefone;
    private List<Evento> eventosOrganizados;

    public Organizador(int telefone, List<Evento> eventosOrganizados) {
        this.telefone = telefone;
        this.eventosOrganizados = eventosOrganizados;
    }

    public Organizador(Integer id, String cpf, String cep, String nome, String email, String senha, Date data_nascimento, Integer organizador, List<Evento> eventos, int telefone, List<Evento> eventosOrganizados) {
        super(id, cpf, cep, nome, email, senha, data_nascimento, organizador, eventos);
        this.telefone = telefone;
        this.eventosOrganizados = eventosOrganizados;
    }

    public List<Evento> getEventosOrganizados() {
        return eventosOrganizados;
    }

    public void setEventosOrganizados(List<Evento> eventosOrganizados) {
        this.eventosOrganizados = eventosOrganizados;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
}
