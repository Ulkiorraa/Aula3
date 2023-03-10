package br.com.ulkiorra.controllers;

import br.com.ulkiorra.DAO.AlunoDAO;
import br.com.ulkiorra.model.Aluno;
import br.com.ulkiorra.model.Cursos;
import br.com.ulkiorra.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class CadastroController {

    @FXML
    private CheckBox bol_maioridade;

    @FXML
    private Button btn_cadastrar;

    @FXML
    private ComboBox<Cursos> txt_curso;

    @FXML
    private RadioButton txt_feminino;

    @FXML
    private RadioButton txt_masculino;

    @FXML
    private TextField txt_nome;

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

        ToggleGroup sexoToggleGroup = new ToggleGroup();
        txt_masculino.setToggleGroup(sexoToggleGroup);
        txt_feminino.setToggleGroup(sexoToggleGroup);

        List<Cursos> opList = new ArrayList<>();
        opList.add(Cursos.ADS);
        opList.add(Cursos.CCMP);
        opList.add(Cursos.ECMP);
        opList.add(Cursos.OUTROS);
        for (Cursos e : opList) {
            txt_curso.getItems().add(e);
        }

        btn_cadastrar.setOnAction(event -> sysCadastro());
    }

    private void sysCadastro(){
        AlunoDAO alunoDAO = new AlunoDAO();
        Alerts alerts = new Alerts();
        String nome = txt_nome.getText().trim();
        boolean maioridade = bol_maioridade.isSelected();
        String sexo;
        Cursos curso = txt_curso.getValue();

        if (nome.isEmpty()) {
            alerts.mostrarMensagemDeErro("Nome é um campo obrigatório.");
            return;
        }
        if (curso == null) {
            alerts.mostrarMensagemDeErro("Curso é um campo obrigatório.");
            return;
        }

        if (txt_masculino.isSelected()) {
            sexo = "masculino";
        } else if (txt_feminino.isSelected()) {
            sexo = "feminino";
        } else {
            alerts.mostrarMensagemDeErro("Selecione o sexo.");
            return;
        }

        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setMaioridade(maioridade);
        aluno.setCurso(curso);
        aluno.setSexo(sexo);

        boolean cadastradoComSucesso = alunoDAO.create(aluno);
        if (cadastradoComSucesso) {
            alerts.mostrarMensagem("Cadastro bem sucedido!");
        } else {
            alerts.mostrarMensagemDeErro("Cadastro falhou.");
        }
    }
}