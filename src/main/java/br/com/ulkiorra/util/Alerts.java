package br.com.ulkiorra.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerts {
    public void mostrarMensagem(String title, String header, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public void mostrarMensagemDeErro(String title, String header, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public Optional<ButtonType> showConfirmation(String title, String content){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait();
    }
}
