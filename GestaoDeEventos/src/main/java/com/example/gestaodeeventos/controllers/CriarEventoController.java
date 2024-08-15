package com.example.gestaodeeventos.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CriarEventoController extends PaginaController{

    @FXML
    private TextField nomeEventoTextField;
    @FXML
    private TextField expectativaTextField;
    @FXML
    private TextField mapaUrlTextField;
    @FXML
    private DatePicker DataPickerEvento;
    @FXML
    private TextArea descricaoTextField;
    @FXML
    private Text nomeInstituicao;
    @FXML
    private TextField CpfOrganizadores;

    @FXML
    private RadioButton presecialBtn;
    @FXML
    private RadioButton hibridoBtn;
    @FXML
    private RadioButton remotoBtn;

    public void addInstituicao(ActionEvent event) {
    }

    public void addCategorias(ActionEvent event) {
    }

    public void criarEvento(ActionEvent event) {
    }

    public void addOrganizadores(ActionEvent event) {
    }

    @Override
    public void atualizarInformacoes() {

    }
}
