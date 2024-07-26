package com.example.gestaodeeventos.model.entities;

import java.util.Date;
import java.util.List;

public class Organizador extends User{
    private int telefone;
    private List<Evento> eventos;

    public Organizador(int telefone, List<Evento> eventos) {
        this.telefone = telefone;
        this.eventos = eventos;
    }

    public Organizador(Integer id, String cpf, String cep, String nome, String email, String senha, Date data_nascimento, int telefone, List<Evento> eventos) {
        super(id, cpf, cep, nome, email, senha, data_nascimento);
        this.telefone = telefone;
        this.eventos = eventos;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
}
