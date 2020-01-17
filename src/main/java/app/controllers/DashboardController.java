package app.controllers;

import app.dao.GenericHibernateDao;
import app.entity.CarEntity;
import app.googleMaps.MyMaker;
import app.service.CarService;
import app.util.CloseApplication;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import netscape.javascript.JSObject;

public class DashboardController implements MapComponentInitializedListener {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane dashboardPane;

    @FXML
    private GoogleMapView mapViewer;


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

    private GenericHibernateDao dao;
    private CarService carService;

    private List<CarEntity> carList;
    private List<Marker> carMarkersList;

    private GoogleMap map;


    //Key for Google Maps Api is saved as an environment variable
    private String API_KEY = System.getenv("GOOGLE_API");

    @FXML
    void initialize() {
        assert dashboardPane != null : "fx:id=\"dashboardPane\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert adminBtn != null : "fx:id=\"adminBtn\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert logoutBtn != null : "fx:id=\"logoutBtn\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert carTable != null : "fx:id=\"carTable\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert rentBtn != null : "fx:id=\"rentBtn\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert returnBtn != null : "fx:id=\"returnBtn\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert mapViewer != null : "fx:id=\"mapViewer\" was not injected: check your FXML file 'dashboard.fxml'.";

        carMarkersList = new ArrayList<Marker>();

        dao = new GenericHibernateDao();
        dao.setClazz(CarEntity.class);

        carService = new CarService();
        carService.setDao(dao);

        //Query all cars from database
        new Thread(new Runnable() {
            @Override
            public void run() {
               carList  = carService.findAll();
            }
        }).start();



        mapViewer.setKey(API_KEY);
        mapViewer.addMapInializedListener(this);


    }


    @FXML
    void logout(ActionEvent event) {
        CloseApplication.cloeApplication();
    }

    @FXML
    void openAdminPanel(ActionEvent event) {
        // TODO
    }

    @FXML
    void rentCar(ActionEvent event) {
    }

    @FXML
    void returnCar(ActionEvent event) {

    }

    @Override
    public void mapInitialized() {
        //
        LatLong default_coord = new LatLong(51.110568,17.035038);
       final  MapOptions mapOptions = new MapOptions();

        mapOptions.center(default_coord
        ).mapType(MapTypeIdEnum.ROADMAP)
        .overviewMapControl(false)
        .panControl(false)
        .scaleControl(false)
        .streetViewControl(false)
        .zoomControl(false)
        .zoom(12);

        map = mapViewer.createMap(mapOptions);

//        map.addMouseEventHandler(UIEventType.click,(GMapMouseEvent event)->
//        {
//            LatLong latLong = event.getLatLong();
//            System.out.println("Latitude "+latLong.getLatitude());
//            System.out.println("Longitude "+latLong.getLongitude());
//        });


        for (CarEntity car:carList
        ) {
            final LatLong position = new LatLong(car.getLatitude(),car.getLongitude());
            final MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(position)
                    .visible(true)
                    .title(String.valueOf(car.getId()));

            final Marker marker = new MyMaker(markerOptions,String.valueOf(car.getId()),position);
            carMarkersList.add(marker);
            map.addMarker(marker);

            final InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
            infoWindowOptions.content("<h2>Address "+car.getAddress()+"</h2>"
                    +"Name: "+car.getName()
                    +"<br>Battery Lvl: "+car.getBatteryLvlPct()
                    +"<br>Status: "+car.getStatus());

            final InfoWindow infoWindow = new InfoWindow(infoWindowOptions);

            map.addUIEventHandler(marker,UIEventType.click,(JSObject obj)->{

                infoWindow.open(map,marker);

            });

        }

    }


}
