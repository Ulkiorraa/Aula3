package br.com.ulkiorra.controllers;

import br.com.ulkiorra.Principal;
import br.com.ulkiorra.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    @FXML
    private MenuItem menuItemAluno;

    @FXML
    private MenuItem menuItemCurso;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    void onMenuItemAlunoAction() {
        System.out.println("teste");

    }

    @FXML
    void onMenuItemCursoAction() {
        loadView("view/cursosView.fxml");
    }

    @FXML
    void onMenuItemAboutAction() {
        loadView("view/aboutView.fxml");
    }

    @Override
    public  void initialize(URL uri, ResourceBundle rb){

    }

    private synchronized void loadView(String absolutName){
        try {
            FXMLLoader loader = new FXMLLoader(Principal.class.getResource(absolutName));
            VBox newVbox = loader.load();
            Scene mainScene = LoginController.getMainScene();
            VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            Node mainMenu = mainVbox.getChildren().get(0);
            mainVbox.getChildren().clear();
            mainVbox.getChildren().add(mainMenu);
            mainVbox.getChildren().addAll(newVbox.getChildren());
        }catch (IOException e){
            Alerts alerts =new Alerts();
            alerts.mostrarMensagemDeErro("Erro ao carregar tela!", "Erro ao carregar tela About", e.getMessage());
        }
    }
}
