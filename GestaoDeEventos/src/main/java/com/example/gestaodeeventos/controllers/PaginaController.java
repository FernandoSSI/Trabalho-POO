package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.db.DB;
import com.example.gestaodeeventos.model.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaginaController implements Initializable {

    protected User user;
    private boolean btnAtivo = false;


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
    @FXML
    protected VBox vboxMenu;

    public PaginaController() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void atualizarInformacoes() {
        adicionarBotaoCriarEvento();
    }

    public void abrirPaginaPrincipal(ActionEvent event, User user) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double currentX = currentStage.getX();
            double currentY = currentStage.getY();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("paginaPrincipal.fxml"));
            Parent root = loader.load();

            PaginaPrincipalController paginaPrincipalController = loader.getController();
            paginaPrincipalController.setUser(user);

            Scene scene = new Scene(root);
            Stage stage = currentStage;

            stage.setScene(scene);

            stage.setX(currentX);
            stage.setY(currentY);

            stage.show();

            paginaPrincipalController.atualizarInformacoes();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirPerfil(ActionEvent event){
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double currentX = currentStage.getX();
            double currentY = currentStage.getY();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("perfil.fxml"));
            Parent root = loader.load();

            PerfilController perfilController = loader.getController();
            perfilController.setUser(user);

            Scene scene = new Scene(root);
            Stage stage = currentStage;

            stage.setScene(scene);

            stage.setX(currentX);
            stage.setY(currentY);

            stage.show();

            perfilController.atualizarInformacoes();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirInscricoes(ActionEvent event){

        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double currentX = currentStage.getX();
            double currentY = currentStage.getY();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("inscricoes.fxml"));
            Parent root = loader.load();

            InscricoesController inscricoesController = loader.getController();
            inscricoesController.setUser(user);

            Scene scene = new Scene(root);
            Stage stage = currentStage;

            stage.setScene(scene);

            stage.setX(currentX);
            stage.setY(currentY);

            stage.show();

            inscricoesController.atualizarInformacoes();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirEventos(ActionEvent event){
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double currentX = currentStage.getX();
            double currentY = currentStage.getY();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("eventos.fxml"));
            Parent root = loader.load();

            EventosController eventosController = loader.getController();
            eventosController.setUser(user);

            Scene scene = new Scene(root);
            Stage stage = currentStage;

            stage.setScene(scene);

            stage.setX(currentX);
            stage.setY(currentY);

            stage.show();

            eventosController.atualizarInformacoes();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirCriarEventos(ActionEvent event){
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double currentX = currentStage.getX();
            double currentY = currentStage.getY();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("criarEvento.fxml"));
            Parent root = loader.load();

            CriarEventoController criarEventoController = loader.getController();
            criarEventoController.setUser(user);

            Scene scene = new Scene(root);
            Stage stage = currentStage;

            stage.setScene(scene);

            stage.setX(currentX);
            stage.setY(currentY);

            stage.show();

            criarEventoController.atualizarInformacoes();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirConfiguracoes(ActionEvent event){

    }

    @FXML
    public void adicionarBotaoCriarEvento() {
        if (user != null && btnAtivo == false) {
            if (DB.isOrganizer(user.getId())) {
                Button novoBotao = new Button("Criar Evento");
                novoBotao.setStyle("-fx-background-color: #212B3D; -fx-text-fill: WHITE; -fx-font-size: 17px;");
                novoBotao.setPrefHeight(45.0);
                novoBotao.setPrefWidth(200.0);

                novoBotao.setCursor(Cursor.HAND);

                novoBotao.setOnAction(this::abrirCriarEventos);

                vboxMenu.getChildren().add(novoBotao);
                Separator separador = new Separator();
                separador.setOpacity(0.0);
                vboxMenu.getChildren().add(separador);

                btnAtivo = true;
            }
        }

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
