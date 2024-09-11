package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.model.entities.Atividade;
import com.example.gestaodeeventos.model.entities.Evento;
import com.example.gestaodeeventos.model.services.AtividadeService;
import com.example.gestaodeeventos.model.services.InscricaoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListaAtividadesController extends PaginaController{


    private AtividadeService atividadeService;

    private Evento evento;

    @FXML
    private Label nomeEvento;

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    @FXML
    public ListView<Atividade> listaAtividades;

    public void voltar(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double currentX = currentStage.getX();
            double currentY = currentStage.getY();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("PaginaEvento.fxml"));
            Parent root = loader.load();

            PaginaEventoController paginaEventoController = loader.getController();
            paginaEventoController.setUser(user);
            paginaEventoController.setEvento(evento);

            Scene scene = new Scene(root);
            Stage stage = currentStage;
            stage.setScene(scene);
            stage.setX(currentX);
            stage.setY(currentY);
            stage.show();

            paginaEventoController.atualizarInformacoes();
            paginaEventoController.adicionarBotaoMeusCertificados();
            paginaEventoController.adicionarBotaoCriarEvento();
            paginaEventoController.adicionarBotaoEventosOrganizados();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizarInformacoes(){
        putAtividades(evento.getId());
        nomeEvento.requestFocus();
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
                                FXMLLoader loader = new FXMLLoader(Main.class.getResource("atividadeEvento.fxml"));
                                Parent root = loader.load();

                                AtividadeEventoController atividadeEventoController = loader.getController();
                                atividadeEventoController.setUser(user);
                                atividadeEventoController.setAtividade(atividade);
                                atividadeEventoController.setEvento(evento);
                                atividadeEventoController.atualizarInformacoes();

                                atividadeEventoController.adicionarBotaoMeusCertificados();
                                atividadeEventoController.adicionarBotaoEventosOrganizados();
                                atividadeEventoController.adicionarBotaoCriarEvento();


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
        this.atividadeService = new AtividadeService();

    }
}
