package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.model.entities.User;
import com.example.gestaodeeventos.model.services.UserService;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private User user;
    private UserService service;

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

    public void setUser(User user){
        this.user = user;
    }

    public void setService(UserService service) {
        this.service = service;
    }

    @FXML
    public void abrirTelaCadastro(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("cadastro.fxml"));
            Parent root = loader.load();

            CadastroController cadastroController = loader.getController();


            Scene scene = new Scene(root, 600, 455);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void fazerLogin(ActionEvent event){

        String email = emailTextField.getText();
        String senha = senhaPasswordField.getText();

        user = service.findByEmailAndPassword(email, senha);

        if (user != null){
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("paginaPrincipal.fxml"));
                Parent root = loader.load();

                PaginaPrincipalController paginaPrincipalController = loader.getController();
                paginaPrincipalController.setUser(user);


                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

                double centerX = screenSize.getMinX() + (screenSize.getWidth() / 2);
                double centerY = screenSize.getMinY() + (screenSize.getHeight() / 2);
                stage.setX(centerX - (stage.getWidth() / 2));
                stage.setY(centerY - (stage.getHeight() / 2));
                stage.show();

                paginaPrincipalController.atualizarInformacoes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorMsg.setText("Usuário não encontrado!");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.service = new UserService();
    }
}
