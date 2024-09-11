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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class AtividadeEventoController extends PaginaController {

    // Declaração de variáveis para armazenar a atividade e o evento atuais
    private Atividade atividade;
    private Evento evento;

    // Serviço para manipular colaboradores associados à atividade
    private ColaboradorService colaboradorService;

    // Elementos da interface gráfica (UI) associados à tela de detalhes da atividade
    @FXML
    private ListView<String> listaColaboradores; // Lista para exibir os nomes dos colaboradores
    @FXML
    private Text descricao; // Campo de texto para exibir a descrição da atividade
    @FXML
    private Text local; // Campo de texto para exibir o local da atividade
    @FXML
    private Text data; // Campo de texto para exibir a data da atividade
    @FXML
    private Label nomeAtividade; // Label para exibir o título da atividade

    // Getter e Setter para a atividade
    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    // Getter e Setter para o evento
    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    // Método para voltar à lista de atividades
    public void voltar(ActionEvent event) {
        try {
            // Carrega a tela de lista de atividades
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("listaAtividades.fxml"));
            Parent root = loader.load();

            // Obtém o controlador da tela de lista de atividades
            ListaAtividadesController listaAtividadesController = loader.getController();
            listaAtividadesController.setUser(user); // Define o usuário atual
            listaAtividadesController.setEvento(evento); // Define o evento atual
            listaAtividadesController.atualizarInformacoes(); // Atualiza as informações na tela

            // Adiciona botões específicos na tela de lista de atividades
            listaAtividadesController.adicionarBotaoMeusCertificados();
            listaAtividadesController.adicionarBotaoEventosOrganizados();
            listaAtividadesController.adicionarBotaoCriarEvento();

            // Substitui a cena atual pela nova cena carregada
            Stage currentStage = (Stage) listaColaboradores.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);
            currentStage.setTitle("Detalhes da Atividade"); // Define o título da janela

        } catch (IOException e) {
            e.printStackTrace(); // Exibe o erro no console em caso de falha no carregamento da tela
        }
    }

    // Método para atualizar as informações da tela de detalhes da atividade
    @Override
    public void atualizarInformacoes() {
        // Define o texto dos elementos da UI com as informações da atividade
        nomeAtividade.setText(atividade.getTitulo());
        descricao.setText(atividade.getDescricao());
        local.setText(evento.getInstituicao().getNome() + ", " + atividade.getLocal());

        // Formata a data da atividade para exibição
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = sdf.format(atividade.getData());
        data.setText(dataFormatada);

        // Busca e adiciona os nomes dos colaboradores na lista
        List<Colaborador> colaboradores = colaboradorService.findByAtividadeId(atividade.getId());
        for (Colaborador colaborador : colaboradores) {
            listaColaboradores.getItems().add(colaborador.getNome());
        }
    }

    // Método chamado automaticamente na inicialização da tela
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializa o serviço de colaboradores
        this.colaboradorService = new ColaboradorService();
    }
}
