package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.model.entities.Instituicao;
import com.example.gestaodeeventos.model.services.InstituicaoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddInstituicaoController implements Initializable {

    private InstituicaoService instituicaoService;
    private List<Instituicao> instituicoes;
    private Instituicao instituicao;

    @FXML
    public TextField nomeTextField;
    @FXML
    public TextField cnpjTextField;
    @FXML
    public TextField estadoTextField;
    @FXML
    public TextField cidadeTextField;
    @FXML
    public TextField bairroTextField;
    @FXML
    public TextField ruaTextField;
    @FXML
    public TextField numeroTextField;
    @FXML
    public TextField telefoneTextField;
    @FXML
    public TextField emailTextField;
    @FXML
    public Text instituicaoSelecionada;


    @FXML
    private TextField pesquisaInstituicaoTextField;
    @FXML
    private ListView<String> listaInstituicoes;


    @FXML
    public void CadastrarInstituicao(ActionEvent event) {
        try {
            Instituicao instituicao = new Instituicao();
            instituicao.setNome(nomeTextField.getText());
            instituicao.setCnpj(cnpjTextField.getText());
            instituicao.setEstado(estadoTextField.getText());
            instituicao.setCidade(cidadeTextField.getText());
            instituicao.setBairro(bairroTextField.getText());
            instituicao.setRua(ruaTextField.getText());
            instituicao.setNumeroResidencial(numeroTextField.getText());
            instituicao.setTelefone(telefoneTextField.getText());

            System.out.println(emailTextField.getText());

            instituicao.setEmail(emailTextField.getText());

            instituicaoService.saveOrUpdate(instituicao);

            atualizarInformacoes();

            limparCampos();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limparCampos() {
        nomeTextField.clear();
        cnpjTextField.clear();
        estadoTextField.clear();
        cidadeTextField.clear();
        bairroTextField.clear();
        ruaTextField.clear();
        numeroTextField.clear();
        telefoneTextField.clear();
        emailTextField.clear();
    }

    public void SelecionarInstituicao(ActionEvent event) {
        String selectedItem = listaInstituicoes.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            instituicaoSelecionada.setText(selectedItem);
        }
    }

    public void atualizarInformacoes() {
        listaInstituicoes.getItems().clear();  // Limpar a lista antes de adicionar os itens novamente
        instituicoes = instituicaoService.findAll();
        for (Instituicao instituicao : instituicoes) {
            listaInstituicoes.getItems().add(instituicao.getNome());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.instituicaoService = new InstituicaoService();
        atualizarInformacoes();  // Atualizar a lista de instituições ao inicializar a tela

        listaInstituicoes.setOnMouseClicked(event -> {
            String selectedItem = listaInstituicoes.getSelectionModel().getSelectedItem();
            instituicao = instituicaoService.findByName(selectedItem);
            if (selectedItem != null) {
                instituicaoSelecionada.setText(selectedItem);
            }
        });
    }
}
