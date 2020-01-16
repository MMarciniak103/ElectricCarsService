package app.database.mapping;

import java.util.Arrays;

public class VehicleBase {
    private Vehicle[] vehicles;

    public VehicleBase() {
    }

    public Vehicle[] getVehicles() {
        return vehicles;
    }

    @Override
    public String toString() {
        return "VehicleBase{" +
                "vehicles=" + Arrays.toString(vehicles) +
                '}';
    }
}
