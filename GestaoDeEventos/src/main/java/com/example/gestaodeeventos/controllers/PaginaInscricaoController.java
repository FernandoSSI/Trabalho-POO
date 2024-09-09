package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.model.entities.Evento;
import com.example.gestaodeeventos.model.entities.Feedback;
import com.example.gestaodeeventos.model.entities.Inscricao;
import com.example.gestaodeeventos.model.entities.Modalidade;
import com.example.gestaodeeventos.model.services.EventoService;
import com.example.gestaodeeventos.model.services.FeedbackService;
import com.example.gestaodeeventos.model.services.InscricaoService;
import com.example.gestaodeeventos.model.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaginaInscricaoController extends PaginaController{

    private InscricaoService inscricaoService;
    private FeedbackService feedbackService;

    private Evento evento;



    @FXML
    private Text inscricaoTitulo;
    @FXML
    private Text nomeParticipante;
    @FXML
    private Text inscricaoId;
    @FXML
    private Label nomeEvento;
    @FXML
    private Button voltarBtn;
    @FXML
    private Pane inscricaoPane;
    @FXML
    private Button resgatarCertificados;
    @FXML
    private TextArea feedbackTextArea;


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

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("inscricoes.fxml"));
            Parent root = loader.load();

            InscricoesController inscricoesController = loader.getController();
            inscricoesController.setUser(user);

            Scene scene = new Scene(root);
            Stage stage = currentStage;

            stage.setScene(scene);

            stage.setX(currentX);
            stage.setY(currentY);

            stage.show();

            inscricoesController.atualizarInformacoes();
            inscricoesController.adicionarBotaoCriarEvento();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void inscrever(ActionEvent event) {
        if(inscricaoService.findInscricaoById(user.getId(), evento.getId()) == null){
            Inscricao inscricao = new Inscricao();
            inscricao.setEvento(evento);
            inscricao.setParticipante(user);
            inscricaoService.createInscricao(inscricao);
        }

        inscricaoPane.setVisible(true);

    }

    @Override
    public void atualizarInformacoes(){
        if (evento != null){
            nomeEvento.setText(evento.getNome());
            inscricaoTitulo.setText(inscricaoTitulo.getText() + evento.getNome());
            nomeParticipante.setText(nomeParticipante.getText() + user.getNome());
            //inscricaoId.setText();

            if(inscricaoService.findInscricaoById(user.getId(), evento.getId()) != null){
                inscricaoPane.setVisible(true);
            }
        }


    }


    public void cancelarInscricao(ActionEvent event) {
        try {
            inscricaoService.deleteInscricaoById(user.getId(), evento.getId());
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double currentX = currentStage.getX();
            double currentY = currentStage.getY();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("inscricoes.fxml"));
            Parent root = loader.load();

            InscricoesController inscricoesController = loader.getController();
            inscricoesController.setUser(user);

            Scene scene = new Scene(root);
            Stage stage = currentStage;

            stage.setScene(scene);

            stage.setX(currentX);
            stage.setY(currentY);

            stage.show();

            inscricoesController.atualizarInformacoes();
            inscricoesController.adicionarBotaoCriarEvento();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void resgatarCertificados(ActionEvent event) {

    }

    public void enviarFeedback(ActionEvent event) {
        String text = feedbackTextArea.getText();
        Feedback feedback = new Feedback();
        feedback.setComentario(text);
        feedback.setEvento(evento);
        feedback.setUsuario(user);
        feedbackService.saveOrUpdate(feedback);
        feedbackTextArea.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.inscricaoService = new InscricaoService();
        this.feedbackService = new FeedbackService();

    }

}
