package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.model.entities.Evento;
import com.example.gestaodeeventos.model.entities.Inscricao;
import com.example.gestaodeeventos.model.services.EventoService;
import com.example.gestaodeeventos.model.services.InscricaoService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class InscricoesController extends PaginaController{
    private static final Random RANDOM = new Random();
    private InscricaoService inscricaoService;

    @FXML
    private GridPane eventosGrid;

    private Pane createEventPane(Evento evento) {
        Pane eventPane = new Pane();
        eventPane.setCursor(Cursor.HAND);
        eventPane.setMaxSize(197.0, 168.0);
        eventPane.setStyle("-fx-border-color: #434343;");

        Text eventNameText = new Text(evento.getNome());
        eventNameText.setLayoutX(1.0);
        eventNameText.setLayoutY(124.0);
        eventNameText.setTextAlignment(TextAlignment.CENTER);
        eventNameText.setWrappingWidth(195.0);
        eventNameText.setFont(new Font("System Bold", 14.0));

        Separator separator = new Separator();
        separator.setLayoutX(1.0);
        separator.setLayoutY(102.0);
        separator.setPrefSize(195.0, 4.0);

        Text institutionText = new Text(evento.getInstituicao().getNome());
        institutionText.setLayoutY(142.0);
        institutionText.setTextAlignment(TextAlignment.CENTER);
        institutionText.setWrappingWidth(196.0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = dateFormat.format(evento.getData());
        Text dateText = new Text(dateStr);
        dateText.setFill(javafx.scene.paint.Color.valueOf("#434343"));
        dateText.setLayoutY(159.0);
        dateText.setTextAlignment(TextAlignment.CENTER);
        dateText.setWrappingWidth(196.0);

        Pane imagePane = new Pane();
        imagePane.setLayoutX(1.0);
        imagePane.setLayoutY(1.0);
        imagePane.setPrefSize(195.0, 101.0);
        Color pastelColor = getRandomPastelColor();
        imagePane.setStyle("-fx-background-color: "+ toRgbString(pastelColor) + ";");

        eventPane.getChildren().addAll(imagePane, eventNameText, separator, institutionText, dateText);

        // Configurar evento de clique
        eventPane.setOnMouseClicked(event -> {
            try {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                double currentX = currentStage.getX();
                double currentY = currentStage.getY();

                FXMLLoader loader = new FXMLLoader(Main.class.getResource("PaginaInscricao.fxml"));
                Parent root = loader.load();

                PaginaInscricaoController paginaInscricaoController = loader.getController();
                paginaInscricaoController.setUser(user);
                paginaInscricaoController.setEvento(evento);

                Scene scene = new Scene(root);
                Stage stage = currentStage;
                stage.setScene(scene);
                stage.setX(currentX);
                stage.setY(currentY);
                stage.show();

                paginaInscricaoController.atualizarInformacoes();
                paginaInscricaoController.adicionarBotaoMeusCertificados();
                paginaInscricaoController.adicionarBotaoCriarEvento();
                paginaInscricaoController.adicionarBotaoEventosOrganizados();



            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return eventPane;
    }

    private Color getRandomPastelColor() {
        float r = getRandomPastelComponent();
        float g = getRandomPastelComponent();
        float b = getRandomPastelComponent();
        return Color.rgb((int)(r * 255), (int)(g * 255), (int)(b * 255));
    }

    private float getRandomPastelComponent() {
        return 0.5f + RANDOM.nextFloat() * 0.5f;
    }

    private String toRgbString(Color color) {
        return String.format("#%02X%02X%02X",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255));
    }

    @Override
    public void atualizarInformacoes(){
        eventosGrid.getChildren().clear();

        List<Inscricao> inscricoes = inscricaoService.findAllInscricoesByUserId(user.getId());
        int col = 0;
        int row = 0;

        for (Inscricao inscricao : inscricoes) {
            Pane eventPane = createEventPane(inscricao.getEvento());
            eventosGrid.add(eventPane, col, row);

            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.inscricaoService = new InscricaoService();
    }
}
