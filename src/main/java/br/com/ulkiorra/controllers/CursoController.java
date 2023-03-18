package br.com.ulkiorra.controllers;

import br.com.ulkiorra.DAO.CursoDAO;
import br.com.ulkiorra.Principal;
import br.com.ulkiorra.listeners.DataChangeListener;
import br.com.ulkiorra.model.Areas;
import br.com.ulkiorra.model.Curso;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CursoController implements Initializable, DataChangeListener {

    private CursoDAO cursoDAO;

    @FXML
    private TableColumn<Curso, Areas> collum_area;

    @FXML
    private TableColumn<Curso, Long> collum_id;

    @FXML
    private TableColumn<Curso, String> collum_name;

    @FXML
    private TableColumn<Curso, String> collum_sigla;

    @FXML
    private TableView<Curso> table_view;

    @FXML
    private TableColumn<Curso, Curso> tableColumEDIT;

    Alerts alerts = new Alerts();

    public void setCursoDAO(CursoDAO cursoDAO) {
        this.cursoDAO = cursoDAO;
    }

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
        collum_id.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        collum_name.setCellValueFactory(new PropertyValueFactory<>("nome"));
        collum_sigla.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        collum_area.setCellValueFactory(new PropertyValueFactory<>("area"));
        Stage stage = (Stage) LoginController.getMainScene().getWindow();
        table_view.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView() {
        List<Curso> list = cursoDAO.findAll();
        ObservableList<Curso> obsList = FXCollections.observableArrayList(list);
        table_view.setItems(obsList);
        initEditButtons();
    }

    private void createDialogorm(Stage parentStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/cadastroCurso.fxml"));
            Pane pane = fxmlLoader.load();
            CadastroCursoController controller = fxmlLoader.getController();
            controller.subscribeDataChangeListener(this);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Curso");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
            alerts.mostrarMensagemDeErro("Erro", "Erro load view!", e.getMessage());
        }
    }

    private void createDialogormUpdate(Curso obj, Stage parentStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/updateCurso.fxml"));
            Pane pane = fxmlLoader.load();
            UpdateCursoController controller2 = fxmlLoader.getController();
            controller2.setCurso(obj);
            controller2.updateFormData();
            controller2.subscribeDataChangeListener(this);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Curso");
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
            protected void updateItem(Curso obj, boolean empty) {
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
}