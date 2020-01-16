package app.controllers;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

public class DashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane dashboardPane;

    @FXML
    private WebView webViewer;

    @FXML
    private JFXButton adminBtn;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private TableView<?> carTable;

    @FXML
    private JFXButton rentBtn;

    @FXML
    private JFXButton returnBtn;

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void openAdminPanel(ActionEvent event) {

    }

    @FXML
    void rentCar(ActionEvent event) {

    }

    @FXML
    void returnCar(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert dashboardPane != null : "fx:id=\"dashboardPane\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert webViewer != null : "fx:id=\"webViewer\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert adminBtn != null : "fx:id=\"adminBtn\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert logoutBtn != null : "fx:id=\"logoutBtn\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert carTable != null : "fx:id=\"carTable\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert rentBtn != null : "fx:id=\"rentBtn\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert returnBtn != null : "fx:id=\"returnBtn\" was not injected: check your FXML file 'dashboard.fxml'.";

    }
}
