package br.com.ulkiorra.controllers;

import br.com.ulkiorra.DAO.AlunoDAO;
import br.com.ulkiorra.DAO.CursoDAO;
import br.com.ulkiorra.listeners.DataChangeListener;
import br.com.ulkiorra.model.Aluno;
import br.com.ulkiorra.model.Curso;
import br.com.ulkiorra.util.Alerts;
import br.com.ulkiorra.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class UpdateAlunoController {

    private Aluno entity;

    private final List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txt_matricula;

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

    public void setAluno(Aluno entity){
        this.entity = entity;
    }

    public void subscribeDataChangeListener(DataChangeListener listner){
        dataChangeListeners.add(listner);
    }

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

        btn_cadastrar.setOnAction(this::sysCadastro);
    }

    private void sysCadastro(ActionEvent event){
        AlunoDAO alunoDAO = new AlunoDAO();
        Alerts alerts = new Alerts();
        String id = txt_matricula.getText().trim();
        Long matricula = Long.parseLong(id);
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
        aluno.setMatricula(matricula);
        aluno.setNome(nome);
        aluno.setMaioridade(maioridade);
        aluno.setCurso(curso);
        aluno.setSexo(sexo);

        Aluno cadastradoComSucesso = alunoDAO.update(aluno);
        if (cadastradoComSucesso != null) {
            alerts.mostrarMensagem("Information", null, "Update bem sucedido!");
            notifyDataChangeListeners();
        } else {
            alerts.mostrarMensagemDeErro("Erro", null, "Update falhou!");
        }

        Utils.currentStage(event).close();
    }

    public void updateFormData(){
        txt_matricula.setText(String.valueOf(entity.getMatricula()));
        txt_nome.setText(entity.getNome());
        bol_maioridade.setSelected(entity.isMaioridade());
        txt_curso.setValue(entity.getCurso());
        String sexo = entity.getSexo();
        if(sexo.equals("masculino")){
            txt_masculino.setSelected(true);
        } else if (sexo.equals("feminino")) {
            txt_feminino.setSelected(true);
        }
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }
}