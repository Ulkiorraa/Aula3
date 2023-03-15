package br.com.ulkiorra.controllers;

import br.com.ulkiorra.Principal;
import br.com.ulkiorra.model.Aluno;
import br.com.ulkiorra.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AlunoController implements Initializable {

    @FXML
    private Button btn_new;

    @FXML
    private TableColumn<Aluno, Integer> collum_id;

    @FXML
    private TableColumn<Aluno, String> collum_name;

    @FXML
    private TableView<Aluno> table_view;

    @FXML
    void onActionNew() {
        Alerts alerts = new Alerts();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/cadastroAluno.fxml"));
            Scene newScene = new Scene(fxmlLoader.load());
            Stage newStage = new Stage();
            newStage.setResizable(false);
            newStage.setTitle("Cadastro de Aluno");
            newStage.setScene(newScene);
            newStage.show();
        } catch (IOException e) {
            alerts.mostrarMensagemDeErro("Erro ao Carregar tela!", "Tela de cadastro de aluno não carregada!", e.getMessage());
        }
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