package app.database.mapping.vehicleComponents;

public class MapColor {
    private String rgb;
    private double alpha;

    public MapColor() {
    }

    public String getRgb() {
        return rgb;
    }

    public double getAlpha() {
        return alpha;
    }

    @Override
    public String toString() {
        return "MapColor{" +
                "rgb='" + rgb + '\'' +
                ", alpha=" + alpha +
                '}';
    }
}
