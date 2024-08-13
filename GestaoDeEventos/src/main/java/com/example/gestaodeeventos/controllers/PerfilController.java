package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.gui.util.Constraints;
import com.example.gestaodeeventos.model.entities.User;
import com.example.gestaodeeventos.model.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PerfilController extends PaginaController  {

    @FXML
    private Text letraInicial;
    @FXML
    private Pane containerLetra;
    @FXML
    private Text nomePerfil;
    @FXML
    private Text emailPerfil;
    @FXML
    private Text numeroInscricoes;

    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField cpfTextField;
    @FXML
    private TextField cepTextField;
    @FXML
    private DatePicker nascimentoDatepicker;
    @FXML
    private TextField senhaTextField;

    private UserService userService;

    private User getFormData() {
        User user = this.user;

        if (cpfTextField.getText() != "") {
            user.setCpf(cpfTextField.getText());
        }
        if (cepTextField.getText() != "") {
            user.setCep(cepTextField.getText());
        }
        if (nomeTextField.getText() != "" ) {
            user.setNome(nomeTextField.getText());
        }
        if (emailTextField.getText() != "") {
            user.setEmail(emailTextField.getText());
        }
        LocalDate localDate = nascimentoDatepicker.getValue();
        if (localDate != null) {
            Date date = Date.valueOf(localDate);
            user.setData_nascimento(date);
        }
        if (senhaTextField.getText() != "") {
            user.setSenha(senhaTextField.getText());
        }

        return user;
    }



    public void AtualizarDados(ActionEvent event) {
        user = getFormData();
        userService.saveOrUpdate(user);
        atualizarInformacoes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(cpfTextField);
        Constraints.setTextFieldInteger(cepTextField);
        Constraints.setTextFieldMaxLength(cpfTextField, 12);
        Constraints.setTextFieldMaxLength(cepTextField, 9);
    }

    @Override
    public void atualizarInformacoes(){
        letraInicial.setText(user.getNome().charAt(0) + "");
        nomePerfil.setText(user.getNome());
        emailPerfil.setText(user.getEmail());

        nomeTextField.setPromptText(user.getNome());
        emailTextField.setPromptText(user.getEmail());
        cpfTextField.setPromptText(user.getCpf());
        cepTextField.setPromptText(user.getCep());
        nascimentoDatepicker.setPromptText(user.getData_nascimento().toString());
        senhaTextField.setPromptText(user.getSenha());

        containerLetra.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userService = new UserService();
        initializeNodes();

    }



}
