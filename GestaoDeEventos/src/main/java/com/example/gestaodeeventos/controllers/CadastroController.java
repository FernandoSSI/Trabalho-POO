package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CadastroController implements Initializable {

    @FXML
    private TextField cpfTextField;
    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField cepTextField;
    @FXML
    private DatePicker nascimentoDataField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField senhaTextField;
    @FXML
    private Button cadastroButton;
    @FXML
    private Button cancelarButton;


    @FXML
    public void cadastrar(){
        System.out.println("Cadastrou");
    }

    @FXML
    public void cancelar(){
        System.out.println("Cancelou");
    }

    private void initializeNodes(){
        Constraints.setTextFieldInteger(cpfTextField);
        Constraints.setTextFieldInteger(cepTextField);
        Constraints.setTextFieldMaxLength(cpfTextField, 12);
        Constraints.setTextFieldMaxLength(cepTextField, 9);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
