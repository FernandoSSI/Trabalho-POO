package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.gui.util.Constraints;
import com.example.gestaodeeventos.gui.util.Utils;
import com.example.gestaodeeventos.model.entities.Organizador;
import com.example.gestaodeeventos.model.entities.User;
import com.example.gestaodeeventos.model.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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
    public void cadastrar() throws IOException {

        user = getFormData();
        service.saveOrUpdate(user);

        Stage stage = (Stage) cadastroButton.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage loginStage = new Stage();
        loginStage.setScene(scene);
        loginStage.show();
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

       /* if(organizadorRadioBtn.isSelected()){

        }*/

        return user;
    }

    @FXML
    public void cancelar() throws IOException{

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) cadastroButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
