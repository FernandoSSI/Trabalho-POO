package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.model.entities.Categoria;
import com.example.gestaodeeventos.model.entities.Instituicao;
import com.example.gestaodeeventos.model.services.CategoriaService;
import com.example.gestaodeeventos.model.services.InstituicaoService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddCategoriaController implements Initializable {


    private CategoriaService categoriaService;
    private List<Categoria> categorias;
    private Categoria categoria;


    @FXML
    public TextField nomeTextField;
    @FXML
    public TextField pesquisaCategoriaTextField;
    @FXML
    public ListView<String> listaCategorias;
    @FXML
    public TextArea descricaoTextArea;
    @FXML
    public Text categoriaSelecionada;

    public Categoria getCategoria() {
        return categoria;
    }

    public void atualizarInformacoes(String filtro) {
        listaCategorias.getItems().clear();
        categorias = categoriaService.findAll();
        for (Categoria categoria : categorias) {
            if (filtro == null || filtro.isEmpty() || categoria.getNome().toLowerCase().contains(filtro.toLowerCase())) {
                listaCategorias.getItems().add(categoria.getNome());
            }
        }
    }

    public void cadastrarCategoria(ActionEvent event) {
        try {
            Categoria categoria = new Categoria();
            categoria.setNome(nomeTextField.getText());
            categoria.setDescricao(descricaoTextArea.getText());

            categoriaService.saveOrUpdate(categoria);
            atualizarInformacoes("");

            nomeTextField.clear();
            descricaoTextArea.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void selecionarCategoria(ActionEvent event) {
        String selectedItem = listaCategorias.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            categoria = categoriaService.findByName(selectedItem);
            categoriaSelecionada.setText(selectedItem);

            Stage stage = (Stage) listaCategorias.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.categoriaService = new CategoriaService();
        atualizarInformacoes("");

        pesquisaCategoriaTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                atualizarInformacoes(newValue);
            }
        });

        listaCategorias.setOnMouseClicked(event -> {
            String selectedItem = listaCategorias.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                categoriaSelecionada.setText(selectedItem);
            }
        });
    }
}
