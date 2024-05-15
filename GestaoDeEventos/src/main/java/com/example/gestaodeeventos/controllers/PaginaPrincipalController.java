package com.example.gestaodeeventos.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.gui.util.Alerts;
import com.example.gestaodeeventos.model.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PaginaPrincipalController implements Initializable {

    private User user;

    @FXML
    private Label labelNome;

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

    public void abrirPagina(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("paginaPrincipal.fxml"));
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

    public void atualizarInformacoes() {
        if (user != null) {
            labelNome.setText(user.getNome());
        }
    }

    @Override
    public void initialize(URL uri, ResourceBundle rb) {

    }


}

