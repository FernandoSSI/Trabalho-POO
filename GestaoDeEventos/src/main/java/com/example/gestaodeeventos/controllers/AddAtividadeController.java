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

public class AddAtividadeController implements Initializable {

    private Evento evento;
    private Atividade atividade;
    private List<Colaborador> colaboradores = new ArrayList<>();
    private AtividadeService atividadeService;
    private UserService userService;
    private ColaboradorService colaboradorService;


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

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

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

    public void cadastrarAtividadade(ActionEvent event) {
        this.atividade = getFormData();
        Integer id = atividadeService.saveOrUpdate(atividade);


        Stage stage = (Stage) listaColaboradores.getScene().getWindow();
        stage.close();
    }

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

    public void atualizarInformacoes() {
        idColaborador.clear();
        for (Colaborador colaborador : colaboradores) {
            listaColaboradores.getItems().add(colaboradorService.findById(colaborador.getId()).getNome());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.atividadeService = new AtividadeService();
        this.userService = new UserService();
        this.colaboradorService = new ColaboradorService();
    }
}
