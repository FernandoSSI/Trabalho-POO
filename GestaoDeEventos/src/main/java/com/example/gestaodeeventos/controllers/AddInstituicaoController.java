package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.model.entities.Instituicao;
import com.example.gestaodeeventos.model.services.InstituicaoService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller responsável pelo cadastro e seleção de instituições no sistema de gestão de eventos.
 * Implementa a interface Initializable para garantir a inicialização dos serviços ao carregar a interface.
 */
public class AddInstituicaoController implements Initializable {

    private InstituicaoService instituicaoService; // Serviço para manipulação de instituições
    private List<Instituicao> instituicoes; // Lista de instituições disponíveis
    private Instituicao instituicao; // Instituição selecionada

    // Componentes da interface gráfica associados à instituição
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

    /**
     * Retorna a instituição atualmente selecionada.
     * @return Instituição selecionada.
     */
    public Instituicao getInstituicao() {
        return instituicao;
    }

    /**
     * Manipula o evento de cadastro de uma nova instituição.
     * Salva ou atualiza a instituição e atualiza a lista de instituições na interface.
     * @param event Evento de ação disparado pelo botão de cadastro.
     */
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

            instituicao.setEmail(emailTextField.getText());

            instituicaoService.saveOrUpdate(instituicao); // Salva ou atualiza a instituição
            atualizarInformacoes(""); // Atualiza a lista de instituições

            limparCampos(); // Limpa os campos de entrada após o cadastro

        } catch (Exception e) {
            e.printStackTrace(); // Log de erro em caso de falha
        }
    }

    /**
     * Limpa os campos de entrada no formulário de cadastro de instituições.
     */
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

    /**
     * Manipula o evento de seleção de uma instituição na lista.
     * Define a instituição selecionada e fecha a janela atual.
     * @param event Evento de ação disparado pela seleção de uma instituição.
     */
    public void SelecionarInstituicao(ActionEvent event) {
        String selectedItem = listaInstituicoes.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            instituicao = instituicaoService.findByName(selectedItem); // Busca a instituição pelo nome
            instituicaoSelecionada.setText(selectedItem); // Exibe o nome da instituição selecionada

            Stage stage = (Stage) listaInstituicoes.getScene().getWindow();
            stage.close(); // Fecha a janela após a seleção
        }
    }

    /**
     * Atualiza a lista de instituições na interface gráfica, filtrando pelo nome se necessário.
     * @param filtro Termo a ser usado para filtrar as instituições. Se for nulo ou vazio, todas as instituições são listadas.
     */
    public void atualizarInformacoes(String filtro) {
        listaInstituicoes.getItems().clear();
        instituicoes = instituicaoService.findAll(); // Busca todas as instituições
        for (Instituicao instituicao : instituicoes) {
            if (filtro == null || filtro.isEmpty() || instituicao.getNome().toLowerCase().contains(filtro.toLowerCase())) {
                listaInstituicoes.getItems().add(instituicao.getNome()); // Adiciona a instituição à lista se corresponder ao filtro
            }
        }
    }

    /**
     * Método de inicialização chamado após o carregamento da interface.
     * Inicializa o serviço de instituições e configura os listeners para interação com a interface.
     * @param url URL da localização do FXML.
     * @param resourceBundle Recursos utilizados na interface.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.instituicaoService = InstituicaoService.getInstance();
        atualizarInformacoes(""); // Atualiza a lista de instituições ao iniciar

        // Listener para o campo de pesquisa de instituições
        pesquisaInstituicaoTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                atualizarInformacoes(newValue); // Atualiza a lista conforme o filtro de pesquisa
            }
        });

        // Listener para clique na lista de instituições
        listaInstituicoes.setOnMouseClicked(event -> {
            String selectedItem = listaInstituicoes.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                instituicaoSelecionada.setText(selectedItem); // Exibe o nome da instituição selecionada
            }
        });
    }
}
