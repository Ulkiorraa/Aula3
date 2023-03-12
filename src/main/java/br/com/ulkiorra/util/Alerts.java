package br.com.ulkiorra.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
}
