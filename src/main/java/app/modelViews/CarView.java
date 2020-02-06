package app.modelViews;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Model of CarView that is used to represent car entity as an JavaFx Object.
 */
public class CarView {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty batteryLvlPct;
    private SimpleStringProperty address;
    private SimpleStringProperty status;
    private SimpleIntegerProperty batteryDistance;

    public CarView(Integer id,String name, Integer batteryLvlPct, String address, String  status, Integer batteryDistance) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.batteryLvlPct = new SimpleIntegerProperty(batteryLvlPct);
        this.address = new SimpleStringProperty(address);
        this.status = new SimpleStringProperty(status);
        this.batteryDistance = new SimpleIntegerProperty(batteryDistance);
    }

    public long getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getBatteryLvlPct() {
        return batteryLvlPct.get();
    }

    public SimpleIntegerProperty batteryLvlPctProperty() {
        return batteryLvlPct;
    }

    public void setBatteryLvlPct(int batteryLvlPct) {
        this.batteryLvlPct.set(batteryLvlPct);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public int getBatteryDistance() {
        return batteryDistance.get();
    }

    public SimpleIntegerProperty batteryDistanceProperty() {
        return batteryDistance;
    }

    public void setBatteryDistance(int batteryDistance) {
        this.batteryDistance.set(batteryDistance);
    }
}

