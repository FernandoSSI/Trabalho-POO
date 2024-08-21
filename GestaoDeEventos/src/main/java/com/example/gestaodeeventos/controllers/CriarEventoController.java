package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.model.entities.Categoria;
import com.example.gestaodeeventos.model.entities.Instituicao;
import com.example.gestaodeeventos.model.entities.Organizador;
import com.example.gestaodeeventos.model.services.OrganizadorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CriarEventoController extends PaginaController {


    private OrganizadorService organizadorService;
    private Instituicao instituicao;
    private Categoria categoria;
    private List<Organizador> organizadores = new ArrayList<>();

    @FXML
    private TextField nomeEventoTextField;
    @FXML
    private TextField expectativaTextField;
    @FXML
    private TextField mapaUrlTextField;
    @FXML
    private DatePicker DataPickerEvento;
    @FXML
    private Text nomeInstituicao;
    @FXML
    private Text nomeCategoria;
    @FXML
    private TextField idOrganizadores;

    @FXML
    private RadioButton presecialBtn;
    @FXML
    private RadioButton hibridoBtn;
    @FXML
    private RadioButton remotoBtn;
    @FXML
    private TextArea descricaoTextArea;
    @FXML
    private ListView<String> listaOrganizadores;

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

            instituicao = addInstituicaoController.getInstituicao();
            nomeInstituicao.setText(instituicao.getNome());

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

            categoria = addCategoriaController.getCategoria();
            nomeCategoria.setText(categoria.getNome());

            addCategoriaController.atualizarInformacoes("");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void criarEvento(ActionEvent event) {
    }

    public void addOrganizadores(ActionEvent event) {
        String id = idOrganizadores.getText();

        Organizador organizador = organizadorService.findById(Integer.parseInt(id));

        if (organizador != null && !organizadores.contains(organizador)) {
            listaOrganizadores.getItems().add(organizador.getNome());
            organizadores.add(organizador);
            System.out.println(organizadores);
        }
        if(organizador == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Organizador não encontrado");
            alert.setHeaderText(null);
            alert.setContentText("Nenhum organizador encontrado com o ID: " + id);
            alert.showAndWait();
        }

        idOrganizadores.clear();
    }

    @Override
    public void atualizarInformacoes() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.organizadorService = new OrganizadorService();

    }

}
