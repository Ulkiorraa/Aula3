package br.com.ulkiorra.controllers;

import br.com.ulkiorra.DAO.AlunoDAO;
import br.com.ulkiorra.Principal;
import br.com.ulkiorra.listeners.DataChangeListener;
import br.com.ulkiorra.model.Aluno;
import br.com.ulkiorra.util.Alerts;
import br.com.ulkiorra.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AlunoController implements Initializable, DataChangeListener {

    private AlunoDAO alunoDAO;

    @FXML
    private TableColumn<Aluno, Long> collum_id;

    @FXML
    private TableColumn<Aluno, String> collum_name;

    @FXML
    private TableColumn<Aluno, String> colum_curso;

    @FXML
    private TableColumn<Aluno, String> colum_maioridade;

    @FXML
    private TableColumn<Aluno, String> colum_sexo;

    @FXML
    private TableColumn<Aluno, Aluno> tableColumEDIT;

    @FXML
    private TableColumn<Aluno, Aluno> tableColumREMOVE;

    @FXML
    private TableView<Aluno> table_view;

    public void setAlunoDAO(AlunoDAO alunoDAO) {
        this.alunoDAO = alunoDAO;
    }

    Alerts alerts = new Alerts();

    @FXML
    void onActionNew(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        createDialogorm(parentStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        collum_id.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        collum_name.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colum_maioridade.setCellValueFactory(new PropertyValueFactory<>("maioridade"));
        colum_curso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        colum_sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        Stage stage = (Stage) LoginController.getMainScene().getWindow();
        table_view.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView() {
        List<Aluno> list = alunoDAO.findAll();
        ObservableList<Aluno> obsList = FXCollections.observableArrayList(list);
        table_view.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
    }

    private void createDialogorm(Stage parentStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/cadastroAluno.fxml"));
            Pane pane = fxmlLoader.load();
            CadastroAlunoController controller = fxmlLoader.getController();
            controller.subscribeDataChangeListener(this);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Aluno");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            alerts.mostrarMensagemDeErro("Erro", "Erro load view!", e.getMessage());
        }
    }

    private void createDialogormUpdate(Aluno obj, Stage parentStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/updateAluno.fxml"));
            Pane pane = fxmlLoader.load();
            UpdateAlunoController controller2 = fxmlLoader.getController();
            controller2.setAluno(obj);
            controller2.updateFormData();
            controller2.subscribeDataChangeListener(this);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Update Aluno");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            alerts.mostrarMensagemDeErro("Erro", "Erro load view!", e.getMessage());
        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

    private void initEditButtons() {
        tableColumEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumEDIT.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Aluno obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> createDialogormUpdate(obj, Utils.currentStage(event)));
            }
        });
    }

    private void initRemoveButtons() {
        tableColumREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumREMOVE.setCellFactory(param -> new TableCell<>() {
            private final Button button = new Button("remove");
            @Override
            protected void updateItem(Aluno obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Aluno obj) {
        Optional<ButtonType> result = alerts.showConfirmation("Confirmation", "Você tem certeza que deseja deletar?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            alunoDAO.delete(obj);
            updateTableView();
        }
    }
}