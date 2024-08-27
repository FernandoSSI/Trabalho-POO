package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.model.entities.Evento;
import com.example.gestaodeeventos.model.entities.Instituicao;
import com.example.gestaodeeventos.model.services.EventoService;
import com.example.gestaodeeventos.model.services.OrganizadorService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EventosController extends PaginaController{

    private EventoService eventoService;

    @FXML
    private ListView eventosList;

    /*instituicoes = instituicaoService.findAll();
        for (
    Instituicao instituicao : instituicoes) {
        if (filtro == null || filtro.isEmpty() || instituicao.getNome().toLowerCase().contains(filtro.toLowerCase())) {
            listaInstituicoes.getItems().add(instituicao.getNome());
        }
    }*/

    @Override
    public void atualizarInformacoes() {
        List<Evento> eventos = eventoService.findAll();
        for (Evento evento : eventos) {
                eventosList.getItems().add(evento.getNome());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.eventoService = new EventoService();
    }

}
