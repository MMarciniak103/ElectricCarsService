package app.googleMaps;

import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

public class MyMarker extends Marker {

    private LatLong lat;
    private String myTitle;
    private InfoWindow infoWindow;
    private MarkerOptions markerOptions;

    /**
     * Contructs a new map Marker with the specified options
     *
     * @param markerOptions The options to use when constructing this marker.

     */
    private MyMarker(MarkerOptions markerOptions) {
        super(markerOptions);
    }

    /**
     * Constructs a new map Marker with the specified options
     * @param markerOptions The options to use when constructing this marker.
     * @param title  The title of created marker.
     * @param lat The Longitude-Latitude coordinates of marker.
     */

    public MyMarker(MarkerOptions markerOptions, final String title, final LatLong lat,InfoWindow infoWindow){
        this(markerOptions);
        this.myTitle = title;
        this.lat = lat;
        this.infoWindow = infoWindow;
        this.markerOptions = markerOptions;
    }

    public LatLong getLat() {
        return lat;
    }

    public void setLat(final LatLong lat) {
        this.lat = lat;
        this.markerOptions.position(lat);
    }

    public void setContent(final String conent){
        this.infoWindow.setContent(conent);
    }

    public void setInfoWindow(InfoWindow infoWindow){
        this.infoWindow = infoWindow;
    }

    public boolean isTitle(final String title){
        return  myTitle.equals(title);
    }

    public InfoWindow getInfoWindow() {
        return infoWindow;
    }

    public MarkerOptions getMarkerOptions() {
        return markerOptions;
    }

    @Override
    public String toString() {
        return "MyMarker{" +
                "lat=" + lat +
                ", myTitle='" + myTitle + '\'' +
                ", infoWindow=" + infoWindow +
                '}';
    }
}

