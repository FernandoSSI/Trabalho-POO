package com.example.gestaodeeventos.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.model.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class PaginaPrincipalController extends PaginaController implements Initializable {

    public PaginaPrincipalController() { }

    public void atualizarInformacoes() {
        if (user != null) {
            labelNome.setText("Bem vindo " + user.getNome() + "!");
        }
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {

    }


}

