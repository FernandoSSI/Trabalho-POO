package com.example.gestaodeeventos.controllers;

import com.example.gestaodeeventos.model.entities.Certificado;
import com.example.gestaodeeventos.model.services.AtividadeService;
import com.example.gestaodeeventos.model.services.CertificadoService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MeusCertificadosController extends PaginaController{

    private CertificadoService certificadoService;

    @FXML
    private ListView<String> listaCertificados;

    @Override
    public void atualizarInformacoes(){
        List<Certificado> certificados = certificadoService.findAllByUserId(user.getId());

        for (Certificado c: certificados){
            System.out.println(c.getAtividade().getTitulo() + " - " + c.getInscricao().getEvento().getNome());
            listaCertificados.getItems().add(c.getAtividade().getTitulo() + " - " + c.getInscricao().getEvento().getNome());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.certificadoService = CertificadoService.getInstance();

    }
}
