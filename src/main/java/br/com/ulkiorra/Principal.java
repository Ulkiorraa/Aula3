package br.com.ulkiorra;

import br.com.ulkiorra.util.Alerts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Principal extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/loginView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            Alerts alerts = new Alerts();
            alerts.mostrarMensagemDeErro("Erro ao Carregar tela!", "Tela de Login não carregada!", e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}