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

public class AtividadeController extends PaginaController {

    private Evento evento; // Armazena o evento associado à atividade
    private Atividade atividade; // Armazena a atividade em si
    private InscricaoService inscricaoService; // Serviço para manipular inscrições
    private EventoService eventoService; // Serviço para manipular eventos
    private ColaboradorService colaboradorService; // Serviço para manipular colaboradores
    private OrganizadorService organizadorService; // Serviço para manipular organizadores
    private AtividadeService atividadeService; // Serviço para manipular atividades
    private UserService userService; // Serviço para manipular usuários
    private CertificadoService certificadoService; // Serviço para manipular certificados

    @FXML
    private ListView listaParticipantes; // Lista para exibir os participantes
    @FXML
    private ListView listaColaboradores; // Lista para exibir os colaboradores
    @FXML
    private TextField tituloTextField; // Campo de texto para o título da atividade
    @FXML
    private TextArea descricaoTextArea; // Área de texto para a descrição da atividade
    @FXML
    private TextField localTextField; // Campo de texto para o local da atividade
    @FXML
    private DatePicker datePicker; // Seletor de data para a data da atividade
    @FXML
    private Label nomeAtividade; // Label para exibir o nome da atividade

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento; // Define o evento atual
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade; // Define a atividade atual
    }

    // Método para emitir certificados para todos os participantes do evento
    public void emitirCertificados(ActionEvent event) {
        // Obtém todas as inscrições associadas ao evento
        List<Inscricao> inscricoes = inscricaoService.findAllInscricoesByEventId(evento.getId());

        // Cria e salva um certificado para cada inscrição
        for (Inscricao i : inscricoes) {
            Certificado certificado = new Certificado();
            certificado.setAtividade(atividade);
            certificado.setInscricao(i);
            certificadoService.saveOrUpdate(certificado);
        }

        // Exibe uma mensagem de confirmação para o usuário
        Alerts.showAlert(
                "Certificados Emitidos",
                "Operação Concluída",
                "Os certificados foram criados e emitidos por e-mail com sucesso.",
                Alert.AlertType.INFORMATION
        );
    }

    // Método para voltar para a tela anterior (evento organizado)
    public void voltar(ActionEvent event) {
        try {
            // Obtém o estágio atual e suas coordenadas
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double currentX = currentStage.getX();
            double currentY = currentStage.getY();

            // Carrega a tela de evento organizado
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("eventoOrganizado.fxml"));
            Parent root = loader.load();

            // Configura o controlador da tela de evento organizado
            EventoOrganizadoController eventoOrganizadoController = loader.getController();
            eventoOrganizadoController.setUser(user);
            eventoOrganizadoController.setEvento(evento);
            eventoOrganizadoController.adicionarBotaoEventosOrganizados();
            eventoOrganizadoController.adicionarBotaoCriarEvento();

            // Configura e exibe a nova cena no estágio atual
            Scene scene = new Scene(root);
            Stage stage = currentStage;
            stage.setScene(scene);
            stage.setX(currentX);
            stage.setY(currentY);
            stage.show();

            // Atualiza as informações e botões da tela de evento organizado
            eventoOrganizadoController.adicionarBotaoMeusCertificados();
            eventoOrganizadoController.adicionarBotaoCriarEvento();
            eventoOrganizadoController.adicionarBotaoEventosOrganizados();
            eventoOrganizadoController.atualizarInformacoes();

        } catch (IOException e) {
            e.printStackTrace(); // Imprime o erro no console em caso de exceção
        }
    }

    // Método para atualizar os dados da atividade
    public void updateAtividade(ActionEvent event) {
        // Atualiza o local da atividade se o campo não estiver vazio
        if(localTextField.getText() != ""){
            atividade.setLocal(localTextField.getText());
        }
        // Atualiza o título da atividade se o campo não estiver vazio
        if(tituloTextField.getText()!= ""){
            atividade.setTitulo(tituloTextField.getText());
        }
        // Atualiza a descrição da atividade se o campo não estiver vazio
        if(descricaoTextArea.getText() != ""){
            atividade.setDescricao(descricaoTextArea.getText());
        }

        // Atualiza a data da atividade se uma data foi selecionada
        LocalDate localDate = datePicker.getValue();
        if (localDate != null) {
            Date date = Date.valueOf(localDate);
            atividade.setData(date);
        }

        // Salva as alterações da atividade
        atividadeService.saveOrUpdate(atividade);
        atualizarInformacoes(); // Atualiza a interface com as novas informações
    }

    @Override
    public void atualizarInformacoes(){
        // Verifica se o evento e a atividade estão definidos
        if (evento != null && atividade !=null){
            // Atualiza os campos da interface com as informações da atividade
            nomeAtividade.setText(atividade.getTitulo());
            putParticipantes(evento.getId());
            putColaboradores(evento.getId());
            tituloTextField.setPromptText(atividade.getTitulo());
            descricaoTextArea.setPromptText(atividade.getDescricao());
            localTextField.setPromptText(atividade.getLocal());
            datePicker.setPromptText(atividade.getData().toString());

            nomeAtividade.requestFocus(); // Foca no nome da atividade
        }
    }

    // Método para adicionar participantes à lista de participantes
    private void putParticipantes(Integer eventId){
        List<User> users = userService.findAllByEventId(eventId);
        for (User user1 : users) {
            listaParticipantes.getItems().add(user1.getNome()); // Adiciona o nome de cada participante à lista
        }
    }

    // Método para adicionar colaboradores à lista de colaboradores
    private void putColaboradores(Integer eventId){
        List<Colaborador> colaboradores = colaboradorService.findAllByEventId(eventId);
        Set<String> colaboradoresUnicos = new HashSet<>();

        for (Colaborador colaborador : colaboradores) {
            // Adiciona o nome do colaborador se ainda não foi adicionado (evita duplicatas)
            if (colaboradoresUnicos.add(colaborador.getNome())){
                listaColaboradores.getItems().add(colaborador.getNome());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializa os serviços utilizados pelo controlador
        this.inscricaoService = InscricaoService.getInstance();
        this.eventoService =EventoService.getInstance();
        this.colaboradorService = ColaboradorService.getInstance();
        this.organizadorService = OrganizadorService.getInstance();
        this.userService = UserService.getInstance();
        this.atividadeService = AtividadeService.getInstance();
        this.certificadoService = CertificadoService.getInstance();
    }
}
