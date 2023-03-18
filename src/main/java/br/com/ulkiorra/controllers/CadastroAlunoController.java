package br.com.ulkiorra.controllers;

import br.com.ulkiorra.DAO.AlunoDAO;
import br.com.ulkiorra.DAO.CursoDAO;
import br.com.ulkiorra.model.Aluno;
import br.com.ulkiorra.model.Curso;
import br.com.ulkiorra.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class CadastroAlunoController {

    @FXML
    private CheckBox bol_maioridade;

    @FXML
    private Button btn_cadastrar;

    @FXML
    private ComboBox<String> txt_curso;

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

        CursoDAO cursoDAO = new CursoDAO();
        List<Curso> opList = cursoDAO.findAll();
        for (Curso e : opList) {
            txt_curso.getItems().add(e.getNome());
        }

        btn_cadastrar.setOnAction(event -> sysCadastro());
    }

    private void sysCadastro(){
        AlunoDAO alunoDAO = new AlunoDAO();
        Alerts alerts = new Alerts();
        String nome = txt_nome.getText().trim();
        boolean maioridade = bol_maioridade.isSelected();
        String sexo;
        String curso = txt_curso.getValue();

        if (nome.isEmpty()) {
            alerts.mostrarMensagemDeErro("Erro", null, "Nome é um campo obrigatório.");
            return;
        }
        if (curso == null) {
            alerts.mostrarMensagemDeErro("Erro", null, "Curso é um campo obrigatório.");
            return;
        }

        if (txt_masculino.isSelected()) {
            sexo = "masculino";
        } else if (txt_feminino.isSelected()) {
            sexo = "feminino";
        } else {
            alerts.mostrarMensagemDeErro("Erro", null, "Sexo é um campo obrigatório.");
            return;
        }

        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setMaioridade(maioridade);
        aluno.setCurso(curso);
        aluno.setSexo(sexo);

        Aluno cadastradoComSucesso = alunoDAO.create(aluno);
        if (cadastradoComSucesso != null) {
            alerts.mostrarMensagem("Information", null, "Cadastro bem sucedido!");
        } else {
            alerts.mostrarMensagemDeErro("Erro", null, "Cadastro falhou!");
        }
    }
}