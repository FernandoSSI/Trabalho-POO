package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.gui.util.Alerts;
import com.example.gestaodeeventos.model.entities.*;
import com.example.gestaodeeventos.model.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class AtividadeController extends PaginaController{


    private Evento evento;
    private Atividade atividade;
    private InscricaoService inscricaoService;
    private EventoService eventoService;
    private ColaboradorService colaboradorService;
    private OrganizadorService organizadorService;
    private AtividadeService atividadeService;
    private UserService userService;
    private CertificadoService certificadoService;

    @FXML
    private ListView listaParticipantes;
    @FXML
    private ListView listaColaboradores;
    @FXML
    private TextField tituloTextField;
    @FXML
    private TextArea descricaoTextArea;
    @FXML
    private TextField localTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label nomeAtividade;

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }



    public void emitirCertificados(ActionEvent event) {
        List<Inscricao> inscricoes = inscricaoService.findAllInscricoesByEventId(evento.getId());

        for(Inscricao i : inscricoes){
            Certificado certificado = new Certificado();
            certificado.setAtividade(atividade);
            certificado.setInscricao(i);
            certificadoService.saveOrUpdate(certificado);
        }

        Alerts.showAlert(
                "Certificados Emitidos",
                "Operação Concluída",
                "Os certificados foram criados e emitidos por e-mail com sucesso.",
                Alert.AlertType.INFORMATION
        );
    }

    public void voltar(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double currentX = currentStage.getX();
            double currentY = currentStage.getY();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("eventoOrganizado.fxml"));
            Parent root = loader.load();

            EventoOrganizadoController eventoOrganizadoController = loader.getController();
            eventoOrganizadoController.setUser(user);
            eventoOrganizadoController.setEvento(evento);
            eventoOrganizadoController.adicionarBotaoEventosOrganizados();
            eventoOrganizadoController.adicionarBotaoCriarEvento();

            Scene scene = new Scene(root);
            Stage stage = currentStage;
            stage.setScene(scene);
            stage.setX(currentX);
            stage.setY(currentY);
            stage.show();

            eventoOrganizadoController.adicionarBotaoMeusCertificados();
            eventoOrganizadoController.adicionarBotaoCriarEvento();
            eventoOrganizadoController.adicionarBotaoEventosOrganizados();
            eventoOrganizadoController.atualizarInformacoes();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateAtividade(ActionEvent event) {
        if(localTextField.getText() != ""){
            atividade.setLocal(localTextField.getText());
        }
        if(tituloTextField.getText()!= ""){
            atividade.setTitulo(tituloTextField.getText());
        }
        if(descricaoTextArea.getText() != ""){
            atividade.setDescricao(descricaoTextArea.getText());
        }

        LocalDate localDate = datePicker.getValue();
        if (localDate != null) {
            Date date = Date.valueOf(localDate);
            atividade.setData(date);
        }

        atividadeService.saveOrUpdate(atividade);
        atualizarInformacoes();
    }


    @Override
    public void atualizarInformacoes(){
        if (evento != null && atividade !=null){
            nomeAtividade.setText(atividade.getTitulo());
            putParticipantes(evento.getId());
            putColaboradores(evento.getId());
            tituloTextField.setPromptText(atividade.getTitulo());
            descricaoTextArea.setPromptText(atividade.getDescricao());
            localTextField.setPromptText(atividade.getLocal());
            datePicker.setPromptText(atividade.getData().toString());

            nomeAtividade.requestFocus();
        }
    }

    private void putParticipantes(Integer eventId){
        List<User> users = userService.findAllByEventId(eventId);
        for (User user1 : users) {
            listaParticipantes.getItems().add(user1.getNome());
        }
    }

    private void putColaboradores(Integer eventId){
        List<Colaborador> colaboradores = colaboradorService.findAllByEventId(eventId);
        Set<String> colaboradoresUnicos = new HashSet<>();

        for (Colaborador colaborador : colaboradores) {
            if (colaboradoresUnicos.add(colaborador.getNome())){
                listaColaboradores.getItems().add(colaborador.getNome());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.inscricaoService = new InscricaoService();
        this.eventoService = new EventoService();
        this.colaboradorService = new ColaboradorService();
        this.organizadorService = new OrganizadorService();
        this.userService = new UserService();
        this.atividadeService = new AtividadeService();
        this.certificadoService = new CertificadoService();

    }


}
