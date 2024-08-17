package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.model.entities.Categoria;
import com.example.gestaodeeventos.model.entities.Instituicao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CriarEventoController extends PaginaController{

    private List<Instituicao> instituicoes;
    private List<Categoria> categorias;

    @FXML
    private TextField nomeEventoTextField;
    @FXML
    private TextField expectativaTextField;
    @FXML
    private TextField mapaUrlTextField;
    @FXML
    private DatePicker DataPickerEvento;
    @FXML
    private TextArea descricaoTextField;
    @FXML
    private Text nomeInstituicao;
    @FXML
    private TextField CpfOrganizadores;

    @FXML
    private RadioButton presecialBtn;
    @FXML
    private RadioButton hibridoBtn;
    @FXML
    private RadioButton remotoBtn;

    public void addInstituicao(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("adicionarInstituicao.fxml"));
            Parent root = loader.load();

            AddInstituicaoController addInstituicaoController = loader.getController();

            Scene scene = new Scene(root);
            Stage newStage = new Stage();

            newStage.setResizable(false);
            newStage.setScene(scene);
            newStage.setTitle("Instituições");

            newStage.initModality(Modality.WINDOW_MODAL);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            newStage.initOwner(currentStage);

            double currentX = currentStage.getX();
            double currentY = currentStage.getY();
            newStage.setX(currentX + 20);
            newStage.setY(currentY + 20);

            newStage.showAndWait();

            addInstituicaoController.atualizarInformacoes();
            //instituicao = addInstituicaoController.getintituicoes()

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCategorias(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("adicionarCategoria.fxml"));
            Parent root = loader.load();

            AddCategoriaController addCategoriaController = loader.getController();

            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setResizable(false);

            newStage.setScene(scene);
            newStage.setTitle("Categorias");

            newStage.initModality(Modality.WINDOW_MODAL);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            newStage.initOwner(currentStage);

            double currentX = currentStage.getX();
            double currentY = currentStage.getY();
            newStage.setX(currentX + 20);
            newStage.setY(currentY + 20);

            newStage.showAndWait();
            //categorias = addCategoriaController.getCategorias()

            addCategoriaController.atualizarInformacoes();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void criarEvento(ActionEvent event) {
    }

    public void addOrganizadores(ActionEvent event) {

    }

    @Override
    public void atualizarInformacoes() {

    }

}
