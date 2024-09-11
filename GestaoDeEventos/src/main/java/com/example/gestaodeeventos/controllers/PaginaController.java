package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.db.DB;
import com.example.gestaodeeventos.model.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaginaController implements Initializable {

    protected User user;
    private boolean btnAddCriarEventoAtivo = false;
    private boolean btnEventosOrganizados = false;
    private boolean btnCertificados = false;

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

            paginaPrincipalController.adicionarBotaoMeusCertificados();
            paginaPrincipalController.atualizarInformacoes();

            paginaPrincipalController.adicionarBotaoCriarEvento();
            paginaPrincipalController.adicionarBotaoEventosOrganizados();




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

            perfilController.adicionarBotaoMeusCertificados();
            perfilController.atualizarInformacoes();

            perfilController.adicionarBotaoCriarEvento();
            perfilController.adicionarBotaoEventosOrganizados();


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
            inscricoesController.adicionarBotaoMeusCertificados();
            inscricoesController.adicionarBotaoCriarEvento();
            inscricoesController.adicionarBotaoEventosOrganizados();



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
            eventosController.adicionarBotaoMeusCertificados();
            eventosController.adicionarBotaoCriarEvento();
            eventosController.adicionarBotaoEventosOrganizados();



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
            criarEventoController.adicionarBotaoMeusCertificados();
            criarEventoController.adicionarBotaoCriarEvento();
            criarEventoController.adicionarBotaoEventosOrganizados();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void adicionarBotaoMeusCertificados() {
        if (user != null && btnCertificados == false) {

                Button novoBotao = new Button("Meus certificados");
                if(this instanceof MeusCertificadosController){
                    novoBotao.setStyle("-fx-background-color: #212B3D; -fx-text-fill: WHITE; -fx-font-size: 17px; -fx-border-width: 0 0 1px 0; -fx-border-color: white;");
                } else {
                    novoBotao.setStyle("-fx-background-color: #212B3D; -fx-text-fill: WHITE; -fx-font-size: 17px;");
                }

                novoBotao.setPrefHeight(45.0);
                novoBotao.setPrefWidth(200.0);

                novoBotao.setCursor(Cursor.HAND);

                novoBotao.setOnAction(this::abrirMeusCertificados);


                vboxMenu.getChildren().add(novoBotao);
                Separator separador = new Separator();
                separador.setPrefHeight(12);
                separador.setPrefWidth(200);
                separador.setOpacity(0.0);
                vboxMenu.getChildren().add(separador);

                btnCertificados = true;

        }

    }

    @FXML
    public void abrirMeusCertificados(ActionEvent event){
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double currentX = currentStage.getX();
            double currentY = currentStage.getY();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("meusCertificados.fxml"));
            Parent root = loader.load();

            MeusCertificadosController meusCertificadosController = loader.getController();
            meusCertificadosController.setUser(user);

            Scene scene = new Scene(root);
            Stage stage = currentStage;

            stage.setScene(scene);

            stage.setX(currentX);
            stage.setY(currentY);

            stage.show();

            meusCertificadosController.atualizarInformacoes();
            meusCertificadosController.adicionarBotaoMeusCertificados();
            meusCertificadosController.adicionarBotaoCriarEvento();
            meusCertificadosController.adicionarBotaoEventosOrganizados();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirConfiguracoes(ActionEvent event){

    }

    @FXML
    public void adicionarBotaoCriarEvento() {
        if (user != null && btnAddCriarEventoAtivo == false) {
            if (DB.isOrganizer(user.getId())) {
                Button novoBotao = new Button("Criar Evento");
                if(this instanceof CriarEventoController){
                    novoBotao.setStyle("-fx-background-color: #212B3D; -fx-text-fill: WHITE; -fx-font-size: 17px; -fx-border-width: 0 0 1px 0; -fx-border-color: white;");
                } else {
                    novoBotao.setStyle("-fx-background-color: #212B3D; -fx-text-fill: WHITE; -fx-font-size: 17px;");
                }
                novoBotao.setPrefHeight(45.0);
                novoBotao.setPrefWidth(200.0);

                novoBotao.setCursor(Cursor.HAND);

                novoBotao.setOnAction(this::abrirCriarEventos);

                vboxMenu.getChildren().add(novoBotao);
                Separator separador = new Separator();
                separador.setPrefHeight(12);
                separador.setPrefWidth(200);
                separador.setOpacity(0.0);
                vboxMenu.getChildren().add(separador);

                btnAddCriarEventoAtivo = true;
            }
        }

    }

    @FXML
    public void adicionarBotaoEventosOrganizados() {
        if (user != null && btnEventosOrganizados == false) {
            if (DB.isOrganizer(user.getId()) || DB.isColaborator(user.getId())) {
                Button novoBotao = new Button("Eventos organizados");
                if(this instanceof EventosOrganizadosController){
                    novoBotao.setStyle("-fx-background-color: #212B3D; -fx-text-fill: WHITE; -fx-font-size: 17px; -fx-border-width: 0 0 1px 0; -fx-border-color: white;");
                } else {
                    novoBotao.setStyle("-fx-background-color: #212B3D; -fx-text-fill: WHITE; -fx-font-size: 17px;");
                }

                novoBotao.setPrefHeight(45.0);
                novoBotao.setPrefWidth(200.0);

                novoBotao.setCursor(Cursor.HAND);

                novoBotao.setOnAction(this::abrirEventosOrganizados);





                vboxMenu.getChildren().add(novoBotao);
                Separator separador = new Separator();
                separador.setPrefHeight(12);
                separador.setPrefWidth(200);
                separador.setOpacity(0.0);
                vboxMenu.getChildren().add(separador);

                btnEventosOrganizados = true;
            }
        }

    }

    public void abrirEventosOrganizados (ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double currentX = currentStage.getX();
            double currentY = currentStage.getY();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("eventosOrganizados.fxml"));
            Parent root = loader.load();

            EventosOrganizadosController eventosOrganizadosController = loader.getController();
            eventosOrganizadosController.setUser(user);

            Scene scene = new Scene(root);
            Stage stage = currentStage;

            stage.setScene(scene);

            stage.setX(currentX);
            stage.setY(currentY);

            stage.show();

            eventosOrganizadosController.atualizarInformacoes();
            eventosOrganizadosController.adicionarBotaoMeusCertificados();
            eventosOrganizadosController.adicionarBotaoCriarEvento();
            eventosOrganizadosController.adicionarBotaoEventosOrganizados();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
