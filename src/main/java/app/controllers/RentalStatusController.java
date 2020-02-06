package app.controllers;

import app.entity.CarEntity;
import com.jfoenix.controls.JFXButton;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller for rental status info view
 */
public class RentalStatusController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rentalStatusWindow;

    @FXML
    private Label nameLabel;

    @FXML
    private Label latitudeLabel;

    @FXML
    private Label longitudeLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label insuranceLabel;

    @FXML
    private Label rangeKmLabel;

    @FXML
    private JFXButton closeBtn;

    private CarEntity car;

    @FXML
    void initialize() {
        assert rentalStatusWindow != null : "fx:id=\"rentalStatusWindow\" was not injected: check your FXML file 'rentalStatusWindow.fxml'.";
        assert nameLabel != null : "fx:id=\"nameLabel\" was not injected: check your FXML file 'rentalStatusWindow.fxml'.";
        assert latitudeLabel != null : "fx:id=\"latitudeLabel\" was not injected: check your FXML file 'rentalStatusWindow.fxml'.";
        assert longitudeLabel != null : "fx:id=\"longitudeLabel\" was not injected: check your FXML file 'rentalStatusWindow.fxml'.";
        assert addressLabel != null : "fx:id=\"addressLabel\" was not injected: check your FXML file 'rentalStatusWindow.fxml'.";
        assert insuranceLabel != null : "fx:id=\"insuranceLabel\" was not injected: check your FXML file 'rentalStatusWindow.fxml'.";
        assert rangeKmLabel != null : "fx:id=\"rangeKmLabel\" was not injected: check your FXML file 'rentalStatusWindow.fxml'.";
        assert closeBtn != null : "fx:id=\"closeBtn\" was not injected: check your FXML file 'rentalStatusWindow.fxml'.";

    }

    /**
     * Method that is used to pass information about rented car from parent's view controller to this one.
     * @param carEntity car entity representing rented car.
     */
    public void passCar(CarEntity carEntity){
        this.car = carEntity;

        nameLabel.setText("Name: "+carEntity.getPlatesNumber());
        latitudeLabel.setText("Latitude: "+carEntity.getLatitude());
        longitudeLabel.setText("Longitude: "+carEntity.getLongitude());
        addressLabel.setText("Adress: "+carEntity.getAddress());
        insuranceLabel.setText("Insurance: "+carEntity.getInsurancePeriod());
        rangeKmLabel.setText("Range Km: "+carEntity.getRangeKM());
    }

    @FXML
    void close(ActionEvent event) {


        Stage window = (Stage) rentalStatusWindow.getScene().getWindow();
        window.hide();
    }

}

