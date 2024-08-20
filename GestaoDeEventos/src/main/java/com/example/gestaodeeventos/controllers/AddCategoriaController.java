package com.example.gestaodeeventos.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCategoriaController implements Initializable {

    @FXML
    public TextField pesquisaInstituicaoTextField;
    @FXML
    public ListView listaInstituicoes;
    @FXML
    public TextField nomeTextField;
    @FXML
    public TextField cnpjTextField;
    @FXML
    public TextField estadoTextField;
    @FXML
    public TextField cidadeTextField;
    @FXML
    public TextField ruaTextField;
    @FXML
    public TextField bairroTextField;
    @FXML
    public TextField numeroTextField;
    @FXML
    public TextField telefoneTextField;
    @FXML
    public TextField emailTextField;
    @FXML
    public Text instituicaoSelecionada;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void atualizarInformacoes() {
    }

    public void CadastrarInstituicao(ActionEvent event) {
    }

    public void SelecionarInstituicao(ActionEvent event) {
    }
}
