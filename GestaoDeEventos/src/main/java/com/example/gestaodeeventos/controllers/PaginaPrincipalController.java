package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.model.entities.Organizador;
import com.example.gestaodeeventos.model.entities.User;
import com.example.gestaodeeventos.model.services.OrganizadorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaginaPrincipalController extends PaginaController{

    @FXML
    private Label labelNome;

    private boolean isOrganizer;

    public void abrirPagina(ActionEvent event, User user, String fxml, boolean isOrganizer) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            PaginaPrincipalController controller = loader.getController();
            controller.setUser(user);
            this.isOrganizer = isOrganizer;

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUser(User user) {
        String welcomeMessage = "Bem-vindo(a), " + user.getNome();
        if (user instanceof Organizador){
            welcomeMessage += " (Organizador)";
        }
        labelNome.setText(welcomeMessage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);


    }
}
