package br.com.ulkiorra;

import br.com.ulkiorra.dao.AlunoDAO;
import br.com.ulkiorra.model.Aluno;
import br.com.ulkiorra.model.Cursos;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Principal extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        AlunoDAO alunoDAO = new AlunoDAO();
        Aluno aluno = new Aluno("Goten dos Santos", false, Cursos.OUTROS, "masculino");
        alunoDAO.create(aluno);
        launch();
    }
}