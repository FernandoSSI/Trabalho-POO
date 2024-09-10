package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.model.entities.*;
import com.example.gestaodeeventos.model.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EventoOrganizadoController extends PaginaController{

    private Evento evento;
    private InscricaoService inscricaoService;
    private EventoService eventoService;
    private FeedbackService feedbackService;
    private ColaboradorService colaboradorService;
    private OrganizadorService organizadorService;
    private AtividadeService atividadeService;
    private UserService userService;

    @FXML
    private ListView<String> listaFeedbacks;
    @FXML
    private ListView<String> listaParticipantes;
    @FXML
    private ListView<String> listaOrganizadores;
    @FXML
    private ListView<String> listaColaboradores;
    @FXML
    private ListView<String> listaAtividades;



  
    @FXML
    private Label nomeEvento;

    public Evento getEvento() {
        return evento;

    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public void voltar(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double currentX = currentStage.getX();
            double currentY = currentStage.getY();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("eventosOrganizados.fxml"));
            Parent root = loader.load();

            EventosOrganizadosController eventosOrganizadosController = loader.getController();
            eventosOrganizadosController.setUser(user);

            Scene scene = new Scene(root);
            Stage stage = currentStage;

            stage.setScene(scene);

            stage.setX(currentX);
            stage.setY(currentY);

            stage.show();

            eventosOrganizadosController.atualizarInformacoes();
            eventosOrganizadosController.adicionarBotaoCriarEvento();
            eventosOrganizadosController.adicionarBotaoEventosOrganizados();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void atualizarInformacoes(){
        if (evento != null){
            nomeEvento.setText(evento.getNome());
            putFeedbacks(evento.getId());
            putParticipantes(evento.getId());
            putOrganizadores(evento.getId());
            putColaboradores(evento.getId());
            putAtividades(evento.getId());
        }
    }


    public void addAtividades(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("addAtividade.fxml"));
            Parent root = loader.load();

            AddAtividadeController addAtividadeController = loader.getController();
            addAtividadeController.setEvento(evento);

            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setResizable(false);

            newStage.setScene(scene);
            newStage.setTitle("Adicione Atividades");

            newStage.initModality(Modality.WINDOW_MODAL);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            newStage.initOwner(currentStage);

            double currentX = currentStage.getX();
            double currentY = currentStage.getY();
            newStage.setX(currentX + 20);
            newStage.setY(currentY + 20);

            newStage.showAndWait();




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void putFeedbacks(Integer eventId){
        List<Feedback> feedbacks = feedbackService.findAllByEventId(eventId);
        for (Feedback feedback : feedbacks) {
            listaFeedbacks.getItems().add(feedback.getComentario());
        }
    }

    private void putParticipantes(Integer eventId){
        List<User> users = userService.findAllByEventId(eventId);
        for (User user1 : users) {
            listaParticipantes.getItems().add(user1.getNome());
        }
    }

    private void putOrganizadores(Integer eventId){
        List<Organizador> organizadores = organizadorService.findAllByEventId(eventId);
        for (Organizador organizador : organizadores) {
            listaOrganizadores.getItems().add(organizador.getNome());
        }
    }

    private void putColaboradores(Integer eventId){
        List<Colaborador> colaboradores = colaboradorService.findAllByEventId(eventId);
        for (Colaborador colaborador : colaboradores) {
            listaColaboradores.getItems().add(colaborador.getNome());
        }
    }

    private void putAtividades(Integer eventId){
        List<Atividade> atividades = atividadeService.findByEventoId(eventId);
        for(Atividade atividade: atividades){
            listaAtividades.getItems().add(atividade.getTitulo());
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.inscricaoService = new InscricaoService();
        this.feedbackService = new FeedbackService();
        this.eventoService = new EventoService();
        this.colaboradorService = new ColaboradorService();
        this.organizadorService = new OrganizadorService();
        this.userService = new UserService();
        this.atividadeService = new AtividadeService();

    }
}
