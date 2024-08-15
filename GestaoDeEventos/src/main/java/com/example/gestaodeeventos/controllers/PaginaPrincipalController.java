package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.db.DB;
import com.example.gestaodeeventos.model.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;

public class PaginaPrincipalController extends PaginaController{

    public void atualizarInformacoes() {
        adicionarBotaoCriarEvento();
    }

}
