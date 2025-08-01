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
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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
    private ListView<Atividade> listaAtividades;



  
    @FXML
    private Label nomeEvento;

    public Evento getEvento() {
        return evento;

    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    // funcao para voltar pra pagina de eventos organizados
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
            eventosOrganizadosController.adicionarBotaoMeusCertificados();
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


    // funcao para abrir a pagina de adicionar atividades ao evento
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
            newStage.setOnHiding(event1 -> atualizarInformacoes());

            newStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //funcoes para colocar as informacoes nas listas

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
        Set<String> colaboradoresUnicos = new HashSet<>();

        for (Colaborador colaborador : colaboradores) {
            if (colaboradoresUnicos.add(colaborador.getNome())){
                listaColaboradores.getItems().add(colaborador.getNome());
            }
        }
    }

    private void putAtividades(Integer eventId){
        List<Atividade> atividades = atividadeService.findByEventoId(eventId);

        // Limpa a ListView existente
        listaAtividades.getItems().clear();

        // Define o cell factory para criar uma ListCell personalizada
        listaAtividades.setCellFactory(listView -> {
            ListCell<Atividade> cell = new ListCell<>() {
                @Override
                protected void updateItem(Atividade atividade, boolean empty) {
                    super.updateItem(atividade, empty);

                    if (empty || atividade == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(atividade.getTitulo());

                        // Adiciona um listener para abrir a tela de detalhes ao clicar
                        setOnMouseClicked(event -> {
                            try {
                                // Carrega a nova tela substituindo a atual
                                FXMLLoader loader = new FXMLLoader(Main.class.getResource("atividade.fxml"));
                                Parent root = loader.load();

                                AtividadeController atividadeController = loader.getController();
                                atividadeController.setUser(user);
                                atividadeController.setAtividade(atividade);
                                atividadeController.setEvento(evento);
                                atividadeController.atualizarInformacoes();

                                atividadeController.adicionarBotaoMeusCertificados();
                                atividadeController.adicionarBotaoEventosOrganizados();
                                atividadeController.adicionarBotaoCriarEvento();


                                // Obtém o estágio atual e substitui a cena
                                Stage currentStage = (Stage) listaAtividades.getScene().getWindow();
                                Scene newScene = new Scene(root);

                                currentStage.setScene(newScene);
                                currentStage.setTitle("Detalhes da Atividade");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            };

            return cell;
        });

        // Adiciona as atividades à ListView
        listaAtividades.getItems().addAll(atividades);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.inscricaoService = InscricaoService.getInstance();
        this.feedbackService = FeedbackService.getInstance();
        this.eventoService = EventoService.getInstance();
        this.colaboradorService = ColaboradorService.getInstance();
        this.organizadorService = OrganizadorService.getInstance();
        this.userService = UserService.getInstance();
        this.atividadeService = AtividadeService.getInstance();

    }

    public void deletarEvento(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Deleção");
        alert.setHeaderText("Você está prestes a deletar o evento.");
        alert.setContentText("Tem certeza que deseja continuar?");

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            eventoService.deleteById(evento.getId());
            try {
                // Carrega a nova tela substituindo a atual
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("eventosOrganizados.fxml"));
                Parent root = loader.load();

                EventosOrganizadosController eventosOrganizadosController = loader.getController();
                eventosOrganizadosController.setUser(user);
                eventosOrganizadosController.atualizarInformacoes();

                eventosOrganizadosController.adicionarBotaoMeusCertificados();
                eventosOrganizadosController.adicionarBotaoEventosOrganizados();
                eventosOrganizadosController.adicionarBotaoCriarEvento();


                // Obtém o estágio atual e substitui a cena
                Stage currentStage = (Stage) listaAtividades.getScene().getWindow();
                Scene newScene = new Scene(root);

                currentStage.setScene(newScene);
                currentStage.setTitle("Detalhes da Atividade");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Deleção cancelada");
        }
    }
}
