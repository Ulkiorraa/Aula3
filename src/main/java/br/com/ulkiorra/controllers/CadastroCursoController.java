package br.com.ulkiorra.controllers;

import br.com.ulkiorra.DAO.CursoDAO;
import br.com.ulkiorra.model.Areas;
import br.com.ulkiorra.model.Curso;
import br.com.ulkiorra.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class CadastroCursoController {

    @FXML
    private Button btn_cadastrar;

    @FXML
    private ComboBox<Areas> txt_area;

    @FXML
    private TextField txt_nome;

    @FXML
    private TextField txt_sigla;

    @FXML
    private AnchorPane window_cad;

    public void initialize(){
        TextFormatter<String> noNumbersFormatter = new TextFormatter<>(change -> {
            if (change.isDeleted()) {
                return change;
            }
            String newText = change.getControlNewText();
            if (newText.matches("\\D*")) {
                return change;
            }
            return null;
        });
        txt_nome.setTextFormatter(noNumbersFormatter);

        TextFormatter<String> noNumbersFormatter2 = new TextFormatter<>(change -> {
            if (change.isDeleted()) {
                return change;
            }
            String newText = change.getControlNewText();
            if (newText.matches("\\D*")) {
                return change;
            }
            return null;
        });
        txt_sigla.setTextFormatter(noNumbersFormatter2);

        List<Areas> opList = new ArrayList<>();
        opList.add(Areas.ARTES);
        opList.add(Areas.BIOLOGICAS);
        opList.add(Areas.EXATAS);
        opList.add(Areas.HUMANAS);
        for (Areas e : opList) {
            txt_area.getItems().add(e);
        }

        btn_cadastrar.setOnAction(event -> sysCadastro());
    }

    private void sysCadastro(){
        CursoDAO cursoDAO = new CursoDAO();
        Alerts alerts = new Alerts();
        String nome = txt_nome.getText().trim();
        String sigla = txt_sigla.getText().trim();
        Areas area = txt_area.getValue();

        if (nome.isEmpty()) {
            alerts.mostrarMensagemDeErro("Erro", null, "Nome é um campo obrigatório.");
            return;
        }
        if (sigla.isEmpty()) {
            alerts.mostrarMensagemDeErro("Erro", null, "Sigla é um campo obrigatório.");
            return;
        }
        if (area == null) {
            alerts.mostrarMensagemDeErro("Erro", null, "Area é um campo obrigatório.");
            return;
        }

        Curso curso = new Curso();
        curso.setNome(nome);
        curso.setSigla(sigla);
        curso.setArea(area);

        Curso cadastradoComSucesso = cursoDAO.create(curso);
        if (cadastradoComSucesso != null) {
            alerts.mostrarMensagemDeErro("Information", null, "Cadastro bem sucedido!");
        } else {
            alerts.mostrarMensagemDeErro("Erro", null, "Cadastro falhou!");
        }
    }
}
