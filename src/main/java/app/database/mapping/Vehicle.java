package app.database.mapping;

import app.database.mapping.vehicleComponents.Location;
import app.database.mapping.vehicleComponents.MapColor;
import app.database.mapping.vehicleComponents.Picture;

/**
 * Model of vehicle object. Used to map json objects into POJO.
 */
public class Vehicle {
    private String discriminator;
    private String platesNumber;
    private String sideNumber;
    private String color;
    private String type;
    private Picture picture;
    private int rangeKm;
    private int batteryLevelPct;
    private String reservationEnd;
    private String reservation;
    private String status;
    private String locationDescription;
    private String address;
    private MapColor mapColor;
    private String promotion;
    private String id;
    private String name;
    private String description;
    private Location location;
    private String metaData;


    public Vehicle() {
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "discriminator='" + discriminator + '\'' +
                ", platesNumber='" + platesNumber + '\'' +
                ", sideNumber='" + sideNumber + '\'' +
                ", color='" + color + '\'' +
                ", type='" + type + '\'' +
                ", picture=" + picture +
                ", rangeKm=" + rangeKm +
                ", batteryLevelPct=" + batteryLevelPct +
                ", reservationEnd='" + reservationEnd + '\'' +
                ", reservation='" + reservation + '\'' +
                ", status='" + status + '\'' +
                ", locationDescription='" + locationDescription + '\'' +
                ", address='" + address + '\'' +
                ", mapColor=" + mapColor +
                ", promotion='" + promotion + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location=" + location +
                ", metaData='" + metaData + '\'' +
                '}';
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public String getPlatesNumber() {
        return platesNumber;
    }

    public String getSideNumber() {
        return sideNumber;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public Picture getPicture() {
        return picture;
    }

    public int getRangeKm() {
        return rangeKm;
    }


    public int getBatteryLevelPct() {
        return batteryLevelPct;
    }

    public String getReservationEnd() {
        return reservationEnd;
    }

    public String getReservation() {
        return reservation;
    }

    public String getStatus() {
        return status;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public String getAddress() {
        return address;
    }

    public MapColor getMapColor() {
        return mapColor;
    }

    public String getPromotion() {
        return promotion;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Location getLocation() {
        return location;
    }

    public String getMetaData() {
        return metaData;
    }
}

