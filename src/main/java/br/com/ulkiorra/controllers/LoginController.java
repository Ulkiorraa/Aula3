package br.com.ulkiorra.controllers;

import br.com.ulkiorra.Principal;
import br.com.ulkiorra.config.ConnectionFactory;
import br.com.ulkiorra.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Button btn_log;

    @FXML
    private ScrollPane tela_login;

    @FXML
    private TextField txt_login;

    @FXML
    private PasswordField txt_pass;

    public void initialize() {
        btn_log.setOnAction(event -> sysLogin());
    }

    private static Scene newScene;

    private void sysLogin() {
        Alerts alerts = new Alerts();
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "SELECT *FROM usuario WHERE nome_usuario = ? AND senha_usuario = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, txt_login.getText());
            statement.setString(2, txt_pass.getText());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/principalPainel.fxml"));
                    ScrollPane scrollPane = fxmlLoader.load();
                    scrollPane.setFitToHeight(true);
                    scrollPane.setFitToWidth(true);
                    newScene = new Scene(scrollPane);
                    Stage newStage = new Stage();
                    newStage.setTitle("Sistema de cadastros");
                    newStage.setScene(newScene);
                    newStage.show();
                    Stage Login_window = (Stage) tela_login.getScene().getWindow();
                    Login_window.close();
                } catch (IOException e) {
                    alerts.mostrarMensagemDeErro("Erro ao Carregar tela!", "Tela Principal não carregada!", e.getMessage());
                }
            } else {
                alerts.mostrarMensagemDeErro("Erro ao Carregar tela!", null, "Usuário ou Senha incorretos!");
            }
        } catch (SQLException e) {
            alerts.mostrarMensagemDeErro("Erro recupera login BD", "Erro ao acessar BD para pegar informações de login", e.getMessage());
        }
    }

    public static Scene getMainScene(){
        return  newScene;
    }
}
