package br.com.ulkiorra.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CadastroController {

    @FXML
    private CheckBox bol_maioridade;

    @FXML
    private Button btn_cadastrar;

    @FXML
    private ComboBox<?> txt_curso;

    @FXML
    private RadioButton txt_feminino;

    @FXML
    private RadioButton txt_masculino;

    @FXML
    private TextField txt_nome;

    @FXML
    private AnchorPane window_cad;

}