package app.googleMaps;

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

public class MyMaker extends Marker {

    public LatLong lat;
    private String myTitle;

    /**
     * Contructs a new map Marker with the specified options
     *
     * @param markerOptions The options to use when constructing this marker.

     */
    private MyMaker(final MarkerOptions markerOptions) {
        super(markerOptions);
    }

    public MyMaker(final MarkerOptions markerOptions,final String title,final LatLong lat){
        this(markerOptions);
        this.myTitle = title;
        this.lat = lat;
    }

    public boolean isTitle(final String title){
        return  myTitle.equals(title);
    }
}
