package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.gui.util.Constraints;
import com.example.gestaodeeventos.gui.util.Utils;
import com.example.gestaodeeventos.model.entities.User;
import com.example.gestaodeeventos.model.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ResourceBundle;

public class CadastroController implements Initializable {

    private User user;
    private UserService service;

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
    private RadioButton organizadorRadioBtn;

    public void setUser(User user){
        this.user = user;
    }

    public void setService(UserService service) {
        this.service = service;
    }

    @FXML
    public void cadastrar(){

        user = getFormData();
        service.saveOrUpdate(user);
    }

    private User getFormData() {
        User user = new User();


        user.setCpf(cpfTextField.getText());
        user.setCep(cepTextField.getText());
        user.setNome(nomeTextField.getText());
        user.setEmail(emailTextField.getText());
        LocalDate localDate = nascimentoDataField.getValue();
        Date date = (localDate != null) ? Date.valueOf(localDate) : null;
        user.setData_nascimento(date);
        user.setData_nascimento(date);
        user.setSenha(senhaTextField.getText());
        if(organizadorRadioBtn.isSelected()){
            user.setOrganizador(1);
        }

        return user;
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
        this.service = new UserService();
    }
}
