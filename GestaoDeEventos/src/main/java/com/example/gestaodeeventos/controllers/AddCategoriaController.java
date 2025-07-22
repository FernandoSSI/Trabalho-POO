package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.model.entities.Categoria;
import com.example.gestaodeeventos.model.services.CategoriaService;
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

/**
 * Controller responsável pelo cadastro e seleção de categorias no sistema de gestão de eventos.
 * Implementa a interface Initializable para garantir a inicialização dos serviços ao carregar a interface.
 */
public class AddCategoriaController implements Initializable {

    private CategoriaService categoriaService; // Serviço para manipulação de categorias
    private List<Categoria> categorias; // Lista de categorias disponíveis
    private Categoria categoria; // Categoria selecionada

    // Componentes da interface gráfica associados à categoria
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

    /**
     * Retorna a categoria atualmente selecionada.
     * @return Categoria selecionada.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Atualiza a lista de categorias na interface gráfica, filtrando pelo nome se necessário.
     * @param filtro Termo a ser usado para filtrar as categorias. Se for nulo ou vazio, todas as categorias são listadas.
     */
    public void atualizarInformacoes(String filtro) {
        listaCategorias.getItems().clear();
        categorias = categoriaService.findAll(); // Busca todas as categorias
        for (Categoria categoria : categorias) {
            if (filtro == null || filtro.isEmpty() || categoria.getNome().toLowerCase().contains(filtro.toLowerCase())) {
                listaCategorias.getItems().add(categoria.getNome()); // Adiciona a categoria à lista se corresponder ao filtro
            }
        }
    }

    /**
     * Manipula o evento de cadastro de uma nova categoria.
     * Salva ou atualiza a categoria e atualiza a lista de categorias na interface.
     * @param event Evento de ação disparado pelo botão de cadastro.
     */
    public void cadastrarCategoria(ActionEvent event) {
        try {
            Categoria categoria = new Categoria();
            categoria.setNome(nomeTextField.getText());
            categoria.setDescricao(descricaoTextArea.getText());

            categoriaService.saveOrUpdate(categoria); // Salva ou atualiza a categoria
            atualizarInformacoes(""); // Atualiza a lista de categorias

            // Limpa os campos de entrada após o cadastro
            nomeTextField.clear();
            descricaoTextArea.clear();

        } catch (Exception e) {
            e.printStackTrace(); // Log de erro em caso de falha
        }
    }

    /**
     * Manipula o evento de seleção de uma categoria na lista.
     * Define a categoria selecionada e fecha a janela atual.
     * @param event Evento de ação disparado pela seleção de uma categoria.
     */
    public void selecionarCategoria(ActionEvent event) {
        String selectedItem = listaCategorias.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            categoria = categoriaService.findByName(selectedItem); // Busca a categoria pelo nome
            categoriaSelecionada.setText(selectedItem); // Exibe o nome da categoria selecionada

            Stage stage = (Stage) listaCategorias.getScene().getWindow();
            stage.close(); // Fecha a janela após a seleção
        }
    }

    /**
     * Método de inicialização chamado após o carregamento da interface.
     * Inicializa o serviço de categorias e configura os listeners para interação com a interface.
     * @param url URL da localização do FXML.
     * @param resourceBundle Recursos utilizados na interface.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.categoriaService = CategoriaService.getInstance();
        atualizarInformacoes(""); // Atualiza a lista de categorias ao iniciar

        // Listener para o campo de pesquisa de categorias
        pesquisaCategoriaTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                atualizarInformacoes(newValue); // Atualiza a lista conforme o filtro de pesquisa
            }
        });

        // Listener para clique na lista de categorias
        listaCategorias.setOnMouseClicked(event -> {
            String selectedItem = listaCategorias.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                categoriaSelecionada.setText(selectedItem); // Exibe o nome da categoria selecionada
            }
        });
    }
}
