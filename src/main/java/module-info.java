module br.com.ulkiorra {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens br.com.ulkiorra to javafx.fxml;
    exports br.com.ulkiorra;
    exports br.com.ulkiorra.controllers;
    opens br.com.ulkiorra.controllers to javafx.fxml;
    exports br.com.ulkiorra.DAO;
    exports br.com.ulkiorra.model;
    exports br.com.ulkiorra.util;
    exports br.com.ulkiorra.listeners;
}