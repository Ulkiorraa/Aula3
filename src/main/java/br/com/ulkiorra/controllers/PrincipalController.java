package br.com.ulkiorra.controllers;

import br.com.ulkiorra.DAO.CursoDAO;
import br.com.ulkiorra.Principal;
import br.com.ulkiorra.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class PrincipalController implements Initializable {

    @FXML
    void onMenuItemAlunoAction() { loadView("view/alunoView.fxml", x -> {}); }

    @FXML
    void onMenuItemCursoAction() {
        loadView("view/cursosView.fxml", (CursoController controller) -> {
            controller.setCursoDAO(new CursoDAO());
            controller.updateTableView();
        });
    }

    @FXML
    void onMenuItemAboutAction() {
        loadView("view/aboutView.fxml", x -> {});
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private synchronized <T> void loadView(String absolutName, Consumer<T> initializeAction) {
        try {
            FXMLLoader loader = new FXMLLoader(Principal.class.getResource(absolutName));
            VBox newVbox = loader.load();
            Scene mainScene = LoginController.getMainScene();
            VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
            Node mainMenu = mainVbox.getChildren().get(0);
            mainVbox.getChildren().clear();
            mainVbox.getChildren().add(mainMenu);
            mainVbox.getChildren().addAll(newVbox.getChildren());
            T controller = loader.getController();
            initializeAction.accept(controller);
        } catch (IOException e) {
            Alerts alerts = new Alerts();
            alerts.mostrarMensagemDeErro("Erro ao carregar tela!", "Erro ao carregar tela About", e.getMessage());
        }
    }
}
