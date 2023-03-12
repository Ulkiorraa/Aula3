package br.com.ulkiorra.controllers;

import br.com.ulkiorra.model.Cursos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CursoController implements Initializable {

    @FXML
    private Button btn_new;

    @FXML
    private TableColumn<Cursos, Integer> collum_id;

    @FXML
    private TableColumn<Cursos, String> collum_name;

    @FXML
    private TableView<Cursos> table_view;

    @FXML
    void onActionNew() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeNodes();
    }

    private void initializeNodes() {
        collum_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        collum_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Stage stage = (Stage) LoginController.getMainScene().getWindow();
        table_view.prefHeightProperty().bind(stage.heightProperty());
    }
}