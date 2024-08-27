package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.model.entities.*;
import com.example.gestaodeeventos.model.services.EventoService;
import com.example.gestaodeeventos.model.services.OrganizadorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CriarEventoController extends PaginaController {


    private EventoService eventoService;
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
    private DatePicker dataPickerEvento;
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
    private ToggleGroup modalidadeGroup;

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

    public void criarEvento(ActionEvent event) {

        Evento evento = eventoGetFormData();
        eventoService.saveOrUpdate(evento);

        limparCampos();
        System.out.println("Evento cadastrado");

    }

    private Evento eventoGetFormData(){
        Evento evento = new Evento();
        evento.setNome(nomeEventoTextField.getText());
        evento.setDescricao(descricaoTextArea.getText());
        LocalDate localDate = dataPickerEvento.getValue();
        Date date = (localDate != null) ? Date.valueOf(localDate) : null;
        evento.setData(date);
        evento.setInstituicao(instituicao);
        evento.setExpectativaParticipantes(Integer.parseInt(expectativaTextField.getText()));
        evento.setMapaURL(mapaUrlTextField.getText());
        evento.setOrganizadores(organizadores);
        evento.setCategoria(categoria);

        if (presecialBtn.isSelected()) {
            evento.setModalidade(Modalidade.PRESENCIAL);
        } else if (hibridoBtn.isSelected()) {
            evento.setModalidade(Modalidade.HIBRIDO);
        } else if (remotoBtn.isSelected()) {
            evento.setModalidade(Modalidade.ONLINE);
        }

        return evento;
    }

    private void limparCampos() {
        nomeEventoTextField.clear();
        expectativaTextField.clear();
        mapaUrlTextField.clear();
        dataPickerEvento.setValue(null);
        descricaoTextArea.clear();
        nomeInstituicao.setText("");
        nomeCategoria.setText("");
        idOrganizadores.clear();
        listaOrganizadores.getItems().clear();
        modalidadeGroup.selectToggle(null);
        organizadores.clear();
    }


    @Override
    public void atualizarInformacoes() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.organizadorService = new OrganizadorService();
        this.eventoService = new EventoService();
        modalidadeGroup = new ToggleGroup();
        presecialBtn.setToggleGroup(modalidadeGroup);
        hibridoBtn.setToggleGroup(modalidadeGroup);
        remotoBtn.setToggleGroup(modalidadeGroup);

    }

}
