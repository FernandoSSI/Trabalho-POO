package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.Main;
import com.example.gestaodeeventos.gui.util.Constraints;
import com.example.gestaodeeventos.gui.util.Utils;
import com.example.gestaodeeventos.model.entities.Organizador;
import com.example.gestaodeeventos.model.entities.User;
import com.example.gestaodeeventos.model.services.OrganizadorService;
import com.example.gestaodeeventos.model.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controlador da tela de cadastro de usuário.
 */
public class CadastroController implements Initializable {

    private User user; // Instância da entidade User que será manipulada
    private UserService service; // Serviço para operações relacionadas à entidade User

    @FXML
    private TextField cpfTextField; // Campo de texto para o CPF
    @FXML
    private TextField nomeTextField; // Campo de texto para o nome
    @FXML
    private TextField cepTextField; // Campo de texto para o CEP
    @FXML
    private DatePicker nascimentoDataField; // Campo de seleção de data de nascimento
    @FXML
    private TextField emailTextField; // Campo de texto para o e-mail
    @FXML
    private TextField senhaTextField; // Campo de texto para a senha
    @FXML
    private Button cadastroButton; // Botão para confirmar o cadastro
    @FXML
    private Button cancelarButton; // Botão para cancelar o cadastro
    @FXML
    private RadioButton organizadorRadioBtn; // Botão de rádio para indicar se o usuário será um organizador

    /**
     * Define o usuário que será manipulado.
     *
     * @param user Instância de User.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Define o serviço que será utilizado para operações com User.
     *
     * @param service Instância de UserService.
     */
    public void setService(UserService service) {
        this.service = service;
    }

    /**
     * Método acionado ao clicar no botão de cadastro.
     * Ele coleta os dados do formulário, cria ou atualiza o usuário no banco de dados,
     * e, se selecionado, cria um organizador.
     *
     * @throws IOException Caso ocorra um erro ao carregar a tela de login.
     */
    @FXML
    public void cadastrar() throws IOException {
        // Coleta os dados do formulário e cria/atualiza o usuário
        user = getFormData();
        service.saveOrUpdate(user);

        // Se o botão de organizador estiver selecionado, cria uma instância de Organizador
        if (organizadorRadioBtn.isSelected()) {
            Organizador organizador = new Organizador();

            // Associa os dados do usuário ao organizador
            organizador.setId(service.findByEmailAndPassword(user.getEmail(), user.getSenha()).getId());
            organizador.setCpf(user.getCpf());
            organizador.setCep(user.getCep());
            organizador.setNome(user.getNome());
            organizador.setEmail(user.getEmail());
            organizador.setSenha(user.getSenha());
            organizador.setData_nascimento(user.getData_nascimento());
            organizador.setEventos(new ArrayList<>());
            organizador.setEventosOrganizados(new ArrayList<>());

            // Salva ou atualiza o organizador no banco de dados
            OrganizadorService organizadorService = new OrganizadorService();
            organizadorService.saveOrUpdate(organizador);
        }

        // Fecha a janela atual
        Stage stage = (Stage) cadastroButton.getScene().getWindow();
        stage.close();

        // Carrega a tela de login
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage loginStage = new Stage();
        loginStage.setScene(scene);
        loginStage.show();
    }

    /**
     * Coleta os dados do formulário de cadastro e cria uma instância de User com esses dados.
     *
     * @return Instância de User com os dados preenchidos.
     */
    private User getFormData() {
        User user = new User();
        user.setCpf(cpfTextField.getText());
        user.setCep(cepTextField.getText());
        user.setNome(nomeTextField.getText());
        user.setEmail(emailTextField.getText());
        LocalDate localDate = nascimentoDataField.getValue();
        Date date = (localDate != null) ? Date.valueOf(localDate) : null;
        user.setData_nascimento(date);
        user.setSenha(senhaTextField.getText());
        return user;
    }

    /**
     * Método acionado ao clicar no botão de cancelar.
     * Ele fecha a tela de cadastro e retorna à tela de login.
     *
     * @throws IOException Caso ocorra um erro ao carregar a tela de login.
     */
    @FXML
    public void cancelar() throws IOException {
        // Carrega a tela de login
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) cadastroButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Inicializa os campos de texto com restrições, como apenas números e tamanho máximo.
     */
    private void initializeNodes() {
        Constraints.setTextFieldInteger(cpfTextField); // Restringe o campo CPF para apenas números
        Constraints.setTextFieldInteger(cepTextField); // Restringe o campo CEP para apenas números
        Constraints.setTextFieldMaxLength(cpfTextField, 12); // Define o tamanho máximo do CPF
        Constraints.setTextFieldMaxLength(cepTextField, 9); // Define o tamanho máximo do CEP
    }

    /**
     * Inicializa o controlador.
     * Este método é chamado automaticamente após o carregamento do FXML.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.service = new UserService(); // Inicializa o serviço de usuário
        initializeNodes(); // Configura as restrições dos campos de texto
    }
}
