package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private Button cadastroButton;

    @FXML
    public void abrirTelaCadastro(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("cadastro.fxml"));
            Parent root = loader.load();

            CadastroController cadastroController = loader.getController();


            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
