package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.model.entities.Organizador;
import com.example.gestaodeeventos.model.entities.User;
import com.example.gestaodeeventos.model.services.OrganizadorService;
import com.example.gestaodeeventos.model.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private User user;
    private UserService userService;
    private OrganizadorService organizadorService;

    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField senhaPasswordField;
    @FXML
    private Label errorMsg;
    @FXML
    private Button loginButton;
    @FXML
    private Button cadastroButton;

    public void setUser(User user) {
        this.user = user;
    }

    public void setService(UserService service) {
        this.userService = service;
    }

    //funcao para abrir a tela de cadastro
    @FXML
    public void abrirTelaCadastro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("cadastro.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 455);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //funcao para fazer login
    @FXML
    public void fazerLogin(ActionEvent event) {
        String email = emailTextField.getText();
        String senha = senhaPasswordField.getText();
        user = userService.findByEmailAndPassword(email, senha);


        if (user != null) {
            Organizador organizador = organizadorService.findById(user.getId());
            PaginaPrincipalController paginaPrincipalController = new PaginaPrincipalController();

            if(organizador == null){
                paginaPrincipalController.abrirPaginaPrincipal(event, user);

            } else {
                organizador.setNome(user.getNome());
                organizador.setCpf(user.getCpf());
                organizador.setCep(user.getCep());
                organizador.setEmail(user.getEmail());
                organizador.setSenha(user.getSenha());
                organizador.setData_nascimento(user.getData_nascimento());

                paginaPrincipalController.abrirPaginaPrincipal(event, organizador);

            }

        } else {
            errorMsg.setText("Usuário não encontrado!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.userService = UserService.getInstance();
        this.organizadorService = OrganizadorService.getInstance();
    }
}
