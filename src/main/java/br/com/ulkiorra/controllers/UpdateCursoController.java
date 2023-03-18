package br.com.ulkiorra.controllers;

import br.com.ulkiorra.DAO.CursoDAO;
import br.com.ulkiorra.listeners.DataChangeListener;
import br.com.ulkiorra.model.Areas;
import br.com.ulkiorra.model.Curso;
import br.com.ulkiorra.util.Alerts;
import br.com.ulkiorra.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.ArrayList;
import java.util.List;

public class UpdateCursoController {

    private Curso entity;

    private final List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private Button btn_update;

    @FXML
    private TextField txt_codigo;

    @FXML
    private ComboBox<Areas> txt_area;

    @FXML
    private TextField txt_nome;

    @FXML
    private TextField txt_sigla;

    public void setCurso(Curso entity){
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

        btn_update.setOnAction(this::sysUpdate);
    }

    private void sysUpdate(ActionEvent event){
        CursoDAO cursoDAO = new CursoDAO();
        Alerts alerts = new Alerts();
        String id = txt_codigo.getText().trim();
        Long codigo = Long.parseLong(id);
        String nome = txt_nome.getText().trim();
        String sigla = txt_sigla.getText().toUpperCase().trim();
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
        curso.setCodigo(codigo);
        curso.setNome(nome);
        curso.setSigla(sigla);
        curso.setArea(area);

        Curso cadastradoComSucesso = cursoDAO.update(curso);
        if (cadastradoComSucesso != null) {
            alerts.mostrarMensagem("Information", null, "Update bem sucedido!");
            notifyDataChangeListeners();
        } else {
            alerts.mostrarMensagemDeErro("Erro", null, "Update falhou!");
        }
        Utils.currentStage(event).close();
    }

    public void updateFormData(){
        txt_codigo.setText(String.valueOf(entity.getCodigo()));
        txt_nome.setText(entity.getNome());
        txt_sigla.setText(entity.getSigla());
        txt_area.setValue(entity.getArea());
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }
}
