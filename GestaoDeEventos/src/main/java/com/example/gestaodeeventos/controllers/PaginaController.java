package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.db.DB;
import com.example.gestaodeeventos.model.entities.User;
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
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaginaController implements Initializable {

    protected User user;


    @FXML
    protected Label labelNome;
    @FXML
    protected Button perfilBtn;
    @FXML
    protected Button inscricoesBtn;
    @FXML
    protected Button eventosBtn;
    @FXML
    protected Button configBtn;

    public PaginaController() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void abrirPagina(ActionEvent event, User user, String arquivo){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(arquivo));
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
    }

    public void atualizarInformacoes() {
        if (user != null) {
            String welcomeMessage = "Bem vindo " + user.getNome();
            if (DB.isOrganizer(user.getId())) {
                welcomeMessage += " Organizador";
            }
            labelNome.setText(welcomeMessage + "!");
        }
    }


    @FXML
    public void abrirPerfil(ActionEvent event){
        abrirPagina(event, user, "perfil.fxml");
    }

    @FXML
    public void abrirInscricoes(ActionEvent event){
        abrirPagina(event, user, "inscricoes.fxml");
    }

    @FXML
    public void abrirEventos(ActionEvent event){
        abrirPagina(event, user, "eventos.fxml");
    }

    @FXML
    public void abrirConfiguracoes(ActionEvent event){
        abrirPagina(event, user, "configuracoes.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
