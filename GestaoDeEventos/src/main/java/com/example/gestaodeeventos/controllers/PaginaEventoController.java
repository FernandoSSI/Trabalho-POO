package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.model.entities.Evento;
import com.example.gestaodeeventos.model.entities.Inscricao;
import com.example.gestaodeeventos.model.entities.Modalidade;
import com.example.gestaodeeventos.model.services.EventoService;
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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class PaginaEventoController extends PaginaController{



    private Evento evento;
    private InscricaoService inscricaoService;

    @FXML
    private Text descricaoEvento;
    @FXML
    private Text instituicaoText;
    @FXML
    private Text endereco;
    @FXML
    private Text cidade;
    @FXML
    private Text categoria;
    @FXML
    private Text inscricaoTitulo;
    @FXML
    private Text nomeParticipante;
    @FXML
    private Text mapaUrl;
    @FXML
    private Text linkLabel;
    @FXML
    private Button inscrevaseBtn;
    @FXML
    private Label nomeEvento;
    @FXML
    private Pane inscricaoPane;
    @FXML
    private Text dataText;

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

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("eventos.fxml"));
            Parent root = loader.load();

            EventosController eventosController = loader.getController();
            eventosController.setUser(user);

            Scene scene = new Scene(root);
            Stage stage = currentStage;

            stage.setScene(scene);

            stage.setX(currentX);
            stage.setY(currentY);

            stage.show();

            eventosController.adicionarBotaoMeusCertificados();
            eventosController.atualizarInformacoes();
            eventosController.adicionarBotaoCriarEvento();
            eventosController.adicionarBotaoEventosOrganizados();


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
        inscrevaseBtn.setVisible(false);


    }

    @Override
    public void atualizarInformacoes(){
        if (evento != null){
            nomeEvento.setText(evento.getNome());
            descricaoEvento.setText(evento.getDescricao());
            categoria.setText(categoria.getText() + evento.getCategoria().getNome());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dataFormatada = sdf.format(evento.getData());
            dataText.setText(dataFormatada);

            if (evento.getModalidade() == Modalidade.HIBRIDO || evento.getModalidade() == Modalidade.PRESENCIAL ){
                instituicaoText.setText(evento.getInstituicao().getNome());
                endereco.setText(evento.getInstituicao().getBairro() + ", " + evento.getInstituicao().getRua()+ ", " + evento.getInstituicao().getNumeroResidencial());
                cidade.setText(evento.getInstituicao().getCidade() + ", " + evento.getInstituicao().getEstado());
                mapaUrl.setText(evento.getMapaURL());
            } else {
                instituicaoText.setText("Evento Online");
                linkLabel.setText("");
            }
            inscricaoTitulo.setText(inscricaoTitulo.getText() + evento.getNome());
            nomeParticipante.setText(nomeParticipante.getText() + user.getNome());
            //inscricaoId.setText();

            if(inscricaoService.findInscricaoById(user.getId(), evento.getId()) != null){
                inscricaoPane.setVisible(true);
                inscrevaseBtn.setDisable(true);
                inscrevaseBtn.setVisible(false);
            }


        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.inscricaoService = new InscricaoService();

    }


    public void abrirAtividades(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double currentX = currentStage.getX();
            double currentY = currentStage.getY();

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("listaAtividades.fxml"));
            Parent root = loader.load();

            ListaAtividadesController listaAtividadesController = loader.getController();
            listaAtividadesController.setUser(user);
            listaAtividadesController.setEvento(evento);

            Scene scene = new Scene(root);
            Stage stage = currentStage;

            stage.setScene(scene);

            stage.setX(currentX);
            stage.setY(currentY);

            stage.show();

            listaAtividadesController.adicionarBotaoMeusCertificados();
            listaAtividadesController.atualizarInformacoes();
            listaAtividadesController.adicionarBotaoCriarEvento();
            listaAtividadesController.adicionarBotaoEventosOrganizados();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
