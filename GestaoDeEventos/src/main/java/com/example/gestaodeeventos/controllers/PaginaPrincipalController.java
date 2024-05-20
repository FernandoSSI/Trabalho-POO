package com.example.gestaodeeventos.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.gestaodeeventos.Main;
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

public class PaginaPrincipalController implements Initializable {

    private User user;

    @FXML
    private Label labelNome;
    @FXML
    private Button perfilBtn;
    @FXML
    private Button inscricoesBtn;
    @FXML
    private Button eventosBtn;
    @FXML
    private Button configBtn;


    public PaginaPrincipalController() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PaginaPrincipalController(User user) {
        this.user = user;
    }

    public void abrirPagina(ActionEvent event, User user){
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
    }

    public void atualizarInformacoes() {
        if (user != null) {
            labelNome.setText("Bem vindo " + user.getNome() + "!");
        }
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {

    }


}

