package app.controllers;

import app.util.DialogUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller for Damage Description Panel View
 */
public class DamageDescriptionController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane descriptionPane;

    @FXML
    private JFXTextArea descriptionText;

    @FXML
    private JFXButton sendBtn;

    private StringProperty damageDescription;

    private DashboardController dashboardController;

    @FXML
    void initialize() {
        assert descriptionPane != null : "fx:id=\"descriptionPane\" was not injected: check your FXML file 'damageDescriptionPane.fxml'.";
        assert descriptionText != null : "fx:id=\"descriptionText\" was not injected: check your FXML file 'damageDescriptionPane.fxml'.";
        assert sendBtn != null : "fx:id=\"sendBtn\" was not injected: check your FXML file 'damageDescriptionPane.fxml'.";

        damageDescription = new SimpleStringProperty();
        damageDescription.bind(descriptionText.textProperty());

    }


    /**
     * Method that takes user's input and sends it back to main controller
     */
    @FXML
    void sendInfo(ActionEvent event) {

        if(damageDescription.equals("")){
            DialogUtils.popupWindow("Insert description",2);
        }
        else{
            dashboardController.passDescription(damageDescription.get());
            Stage window = (Stage) descriptionText.getScene().getWindow();
            window.hide();
        }
    }

    public void setController(DashboardController controller){
        this.dashboardController = controller;
    }

}
