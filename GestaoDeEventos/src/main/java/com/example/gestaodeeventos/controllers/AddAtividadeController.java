package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.model.entities.*;
import com.example.gestaodeeventos.model.services.AtividadeService;
import com.example.gestaodeeventos.model.services.ColaboradorService;
import com.example.gestaodeeventos.model.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller responsável pelo cadastro de atividades no sistema de gestão de eventos.
 * Implementa a interface Initializable para garantir a inicialização dos serviços ao carregar a interface.
 */
public class AddAtividadeController implements Initializable {

    private Evento evento; // Evento ao qual a atividade está associada
    private Atividade atividade; // Objeto Atividade a ser criado ou atualizado
    private List<Colaborador> colaboradores = new ArrayList<>(); // Lista de colaboradores associados à atividade
    private AtividadeService atividadeService; // Serviço para manipulação de atividades
    private UserService userService; // Serviço para manipulação de usuários
    private ColaboradorService colaboradorService; // Serviço para manipulação de colaboradores

    // Componentes da interface gráfica associados à atividade
    @FXML
    private TextField idColaborador;
    @FXML
    private TextArea descricaoTextArea;
    @FXML
    private DatePicker dataPicker;
    @FXML
    private TextField localTextField;
    @FXML
    private TextField nomeTextField;
    @FXML
    private ListView<String> listaColaboradores;

    /**
     * Retorna o evento ao qual a atividade está associada.
     * @return Evento atual.
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * Define o evento ao qual a atividade está associada.
     * @param evento O evento a ser associado à atividade.
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     * Obtém os dados do formulário e cria um objeto Atividade.
     * @return Atividade criada a partir dos dados do formulário.
     */
    private Atividade getFormData(){
        Atividade atividade = new Atividade();
        atividade.setDescricao(descricaoTextArea.getText());
        atividade.setTitulo(nomeTextField.getText());
        LocalDate localDate = dataPicker.getValue();
        if (localDate != null) {
            Date date = Date.valueOf(localDate);
            atividade.setData(date);
        }
        atividade.setEvento(evento);
        atividade.setLocal(localTextField.getText());
        atividade.setColaboradores(colaboradores);

        return atividade;
    }

    /**
     * Manipula o evento de cadastro de uma nova atividade.
     * Salva ou atualiza a atividade e fecha a janela atual.
     * @param event Evento de ação disparado pelo botão de cadastro.
     */
    public void cadastrarAtividadade(ActionEvent event) {
        this.atividade = getFormData();
        Integer id = atividadeService.saveOrUpdate(atividade);

        // Fecha a janela atual após o cadastro
        Stage stage = (Stage) listaColaboradores.getScene().getWindow();
        stage.close();
    }

    /**
     * Manipula o evento de adicionar um colaborador à lista de colaboradores da atividade.
     * @param event Evento de ação disparado pelo botão de adicionar colaborador.
     */
    public void addColaborador(ActionEvent event) {
        Integer id = Integer.parseInt(idColaborador.getText());
        User user = userService.findById(id);

        if(user != null){
            Colaborador colaborador = new Colaborador();
            colaborador.setId(id);
            colaboradorService.saveOrUpdate(colaborador);
            colaboradores.add(colaborador);
            atualizarInformacoes();
        }
    }

    /**
     * Atualiza as informações na interface gráfica, como a lista de colaboradores.
     */
    public void atualizarInformacoes() {
        idColaborador.clear();
        for (Colaborador colaborador : colaboradores) {
            listaColaboradores.getItems().add(colaboradorService.findById(colaborador.getId()).getNome());
        }
    }

    /**
     * Método de inicialização chamado após o carregamento da interface.
     * Inicializa os serviços necessários para a manipulação das entidades.
     * @param url URL da localização do FXML.
     * @param resourceBundle Recursos utilizados na interface.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.atividadeService = new AtividadeService();
        this.userService = new UserService();
        this.colaboradorService = new ColaboradorService();
    }
}
