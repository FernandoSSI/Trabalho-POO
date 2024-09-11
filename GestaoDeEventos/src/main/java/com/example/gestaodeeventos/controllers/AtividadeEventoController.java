package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.model.entities.Atividade;
import com.example.gestaodeeventos.model.entities.Colaborador;
import com.example.gestaodeeventos.model.entities.Evento;
import com.example.gestaodeeventos.model.services.AtividadeService;
import com.example.gestaodeeventos.model.services.ColaboradorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AtividadeEventoController extends PaginaController{

    private Atividade atividade;
    private Evento evento;

    private ColaboradorService colaboradorService;

    @FXML
    private ListView<String> listaColaboradores;
    @FXML
    private Text descricao;
    @FXML
    private Text local;
    @FXML
    private Text data;
    @FXML
    private Label nomeAtividade;

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public void voltar(ActionEvent event) {
        try {
            // Carrega a nova tela substituindo a atual
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("listaAtividades.fxml"));
            Parent root = loader.load();

            ListaAtividadesController listaAtividadesController = loader.getController();
            listaAtividadesController.setUser(user);
            listaAtividadesController.setEvento(evento);
            listaAtividadesController.atualizarInformacoes();

            listaAtividadesController.adicionarBotaoEventosOrganizados();
            listaAtividadesController.adicionarBotaoCriarEvento();

            // Obtém o estágio atual e substitui a cena
            Stage currentStage = (Stage) listaColaboradores.getScene().getWindow();
            Scene newScene = new Scene(root);

            currentStage.setScene(newScene);
            currentStage.setTitle("Detalhes da Atividade");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizarInformacoes(){
        nomeAtividade.setText(atividade.getTitulo());
        descricao.setText(atividade.getDescricao());
        local.setText(evento.getInstituicao().getNome() + ", " + atividade.getLocal());
        data.setText(atividade.getData().toString());

        List<Colaborador> colaboradores = colaboradorService.findByAtividadeId(atividade.getId());
        for(Colaborador colaborador : colaboradores){
            listaColaboradores.getItems().add(colaborador.getNome());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.colaboradorService = new ColaboradorService();

    }


}
