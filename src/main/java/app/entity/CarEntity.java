package app.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "vehicles")
public class CarEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "plates_number")
    private String platesNumber;

    @Column(name = "range_km")
    private int rangeKM;

    @Column(name = "battery_lvl_pct")
    private int batteryLvlPct;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "status")
    private String status;

    @Column(name = "insurance_period")
    private LocalDate insurancePeriod;

    @Column(name = "battery_distance")
    private int batteryDistance;

    @Column(name = "damage_description")
    private String damageDescription;



    public CarEntity() {
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatesNumber() {
        return platesNumber;
    }

    public void setPlatesNumber(String platesNumber) {
        this.platesNumber = platesNumber;
    }

    public int getRangeKM() {
        return rangeKM;
    }

    public void setRangeKM(int rangeKM) {
        this.rangeKM = rangeKM;
    }

    public int getBatteryLvlPct() {
        return batteryLvlPct;
    }

    public void setBatteryLvlPct(int batteryLvlPct) {
        this.batteryLvlPct = batteryLvlPct;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getInsurancePeriod() {
        return insurancePeriod;
    }

    public void setInsurancePeriod(LocalDate insurancePeriod) {
        this.insurancePeriod = insurancePeriod;
    }

    public int getBatteryDistance() {
        return batteryDistance;
    }

    public void setBatteryDistance(int batteryDistance) {
        this.batteryDistance = batteryDistance;
    }

    public String getDamageDescription() {
        return damageDescription;
    }

    public void setDamageDescription(String damageDescription) {
        this.damageDescription = damageDescription;
    }


    @Override
    public String toString() {
        return "CarEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", platesNumber='" + platesNumber + '\'' +
                ", rangeKM=" + rangeKM +
                ", batteryLvlPct=" + batteryLvlPct +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", status='" + status + '\'' +
                ", insurancePeriod=" + insurancePeriod +
                ", batteryDistance=" + batteryDistance +
                ", damageDescription='" + damageDescription + '\'' +
                '}';
    }
}
