package app.controllers;

import app.dao.GenericHibernateDao;
import app.entity.CarEntity;
import app.entity.TransactionEntity;
import app.entity.UserEntity;
import app.googleMaps.MyMarker;
import app.modelViews.CarView;
import app.service.CarService;
import app.service.TransactionService;
import app.util.CloseApplication;
import app.util.DialogUtils;
import app.util.HaversineFormula;
import com.jfoenix.controls.JFXButton;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;


import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.*;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;

import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import netscape.javascript.JSObject;


public class DashboardController implements MapComponentInitializedListener {

    //Key for Google Maps Api is saved as an environment variable
    private String API_KEY = System.getenv("GOOGLE_API");

    final String GREEN_MARKER = "/icons/green_marker.png";
    final String RED_MARKER = "/icons/red_marker.png";


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane dashboardPane;

    @FXML
    private GoogleMapView mapViewer;

    @FXML
    public JFXButton rentalStatusBtn;

    @FXML
    private JFXButton adminBtn;

    @FXML
    private JFXButton logoutBtn;

    @FXML
    private TableView<CarView> carTable;

    @FXML
    private TableColumn<CarView, String> nameColumn;

    @FXML
    private TableColumn<CarView, Integer> batteryLvlColumn;

    @FXML
    private TableColumn<CarView, String> addressColumn;

    @FXML
    private TableColumn<CarView, String> statusColumn;

    @FXML
    private TableColumn<CarView, Integer> batteryDistColumn;

    @FXML
    private JFXButton rentBtn;

    @FXML
    private JFXButton returnBtn;

    private GenericHibernateDao dao;

    private CarService carService;

    private TransactionService transactionService;

    private List<CarEntity> carList;
    private List<Marker> carMarkersList;

    private GoogleMap map;

    private UserEntity loggedUser;

    private GeocodingService geocodingService;

    @FXML
    void initialize() {
        assert dashboardPane != null : "fx:id=\"dashboardPane\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert adminBtn != null : "fx:id=\"adminBtn\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert logoutBtn != null : "fx:id=\"logoutBtn\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert carTable != null : "fx:id=\"carTable\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert rentBtn != null : "fx:id=\"rentBtn\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert returnBtn != null : "fx:id=\"returnBtn\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert mapViewer != null : "fx:id=\"mapViewer\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert nameColumn != null : "fx:id=\"nameColumn\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert batteryLvlColumn != null : "fx:id=\"batteryLvlColumn\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert addressColumn != null : "fx:id=\"addressColumn\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert statusColumn != null : "fx:id=\"statusColumn\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert batteryDistColumn != null : "fx:id=\"batteryDistColumn\" was not injected: check your FXML file 'dashboard.fxml'.";

        carMarkersList = new ArrayList<>();

        dao = new GenericHibernateDao();

        carService = new CarService();
        carService.setDao(dao);

        //Query all cars from database
        carList=carService.findAll();

        transactionService = new TransactionService();

        mapViewer.setKey(API_KEY);
        mapViewer.addMapInializedListener(this);


    }


    @FXML
    void logout(ActionEvent event) {
        map.clearMarkers();

        CloseApplication.cloeApplication();
    }

    @FXML
    void openAdminPanel(ActionEvent event) {
        // TODO
    }

    /**
     * Function that allows user to rent a chosen car. If user has already rented a car,he cannot rent another one.
     * In the process the chosen car is being check if it is ready to be rented and used.
     */
    @FXML
    void rentCar(ActionEvent event) {
        //Check if logged user currently rented a car.
        transactionService.setDao(dao);
        TransactionEntity transaction = transactionService.findByUser(loggedUser.getId());
        if(transaction == null) {
            //Check if car is available
            carService.setDao(dao);
            CarEntity chosenCar = carService.findOne(carTable.getSelectionModel().selectedItemProperty().get().getId());
            if (chosenCar.getStatus().equals("AVAILABLE") && chosenCar.getBatteryLvlPct() != 0 && !chosenCar.getStatus().equals("IS BEING CHARGED")) {
                transactionService.setDao(dao);
                TransactionEntity transactionEntity = new TransactionEntity();
                transactionEntity.setCarId((chosenCar.getId()));
                transactionEntity.setClientId(loggedUser.getId());
                transactionEntity.setCompleted("RENTED");
                transactionEntity.setDate(LocalDate.now());

                transactionService.create(transactionEntity);


                DialogUtils.popupWindow("Car Rented Successfully", 2);

            }
            else{
                if(!chosenCar.getStatus().equals("AVAILABLE") && !chosenCar.getStatus().equals("IS BEING CHARGED"))
                    DialogUtils.popupWindow("This car is taken",2);
                else if(chosenCar.getBatteryLvlPct() == 0)
                    DialogUtils.popupWindow("Battery Lvl is too low",2);
                else if(chosenCar.getStatus().equals("IS BEING CHARGED"))
                    DialogUtils.popupWindow("Car is being charged",2);
            }
        }
        else{

            DialogUtils.popupWindow("You already rented a car",2);
        }
    }

    /**
     * Function that allows user to return rented car. It calculates some necessary variables, like
     * for example traveled distance and profit for renting company. After calculations
     * car object and the marker associated with this car are being updated.
     */
    @FXML
    void returnCar(ActionEvent event) {
        transactionService.setDao(dao);
        TransactionEntity transaction = transactionService.findByUser(loggedUser.getId());
        if(transaction != null && !transaction.getCompleted().equals("COMPLETED")) {
            //It fires triggers that are implemented in mysql db schema
            carService.setDao(dao);
            CarEntity car = carService.findOne(transaction.getCarId());

            Random random = new Random();

            //Generate random latitude and longitude from a given range. It simulates real behavior of this system.
            double new_latitude = random.nextDouble()* (51.200568-51.050568)+51.050568;
            double new_longitde= random.nextDouble()* (17.155038-16.955038)+16.955038;

            //Calculate traveled distance with Haversine formula
            double travelDistance = HaversineFormula.haversin_distance(car.getLatitude(),car.getLongitude(),new_latitude,new_longitde);

            car.setLongitude(new_longitde);
            car.setLatitude(new_latitude);
            //Set new battery lvl
            final int battery =((car.getBatteryLvlPct()-(travelDistance*0.1))>=0)? (int) (car.getBatteryLvlPct()-(travelDistance*0.1)) : 0;

            car.setBatteryLvlPct(battery);
            car.setRangeKM((int) (car.getRangeKM()+travelDistance));

            car.setStatus("AVAILABLE");


            CarView carView = carTable.getItems().get((int) car.getId()-1);


            //Find new address value for a given latitude-longitude coordinates. It uses Google Maps Api Geocoding Service.
            geocodingService.reverseGeocode(new_latitude,new_longitde,(GeocodingResult[] results, GeocoderStatus status) -> {
                String result  = (results[0].getFormattedAddress());
                //Convert result address into utf-8 format.
                byte[] byteText = result.getBytes(Charset.forName("UTF-8"));
                String address = null;
                try {
                    address = new String(byteText,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                carView.setAddress(address);
                carView.setBatteryLvlPct(battery);
                carView.setStatus("AVAILABLE");
                carTable.refresh();

                car.setAddress(carView.getAddress());


                //Update car entity
                carService.update(car);

                //Update marker associated with returned car and its parameters
                Marker marker = findMarkerByName(String.valueOf(car.getId()));
                if(marker != null){

                    final LatLong lat = new LatLong(new_latitude,new_longitde);


                    InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                    infoWindowOptions.content(MyMarker.createWindowContent(car));

                    InfoWindow infoWindow = new InfoWindow(infoWindowOptions);

                    ((MyMarker) marker).setLat(lat);
                    ((MyMarker) marker).setInfoWindow(infoWindow);
                    marker.setPosition(lat);
                    carMarkersList.forEach(x->((MyMarker)x).getInfoWindow().close());
                    ((MyMarker)marker).getInfoWindow().open(map,marker);

            }});


            transactionService.setDao(dao);

            transactionService.delete(transaction);
        }
        else{
            DialogUtils.popupWindow("No car is rented",2);
        }
    }



    /**
     * Method that initialize google map view and creates markers that are put on the map.
     * These markers are made based on database data.
     * It also populates table view with data that is linked with map's marker.
     */
    @Override
    public void mapInitialized() {
        geocodingService = new GeocodingService();

        //Create default coordinates that stand for center of Wroclaw
        LatLong default_coord = new LatLong(51.110568,17.035038);

        //Initializing map options and configuration
        final  MapOptions mapOptions = new MapOptions();

        mapOptions.center(default_coord
        ).mapType(MapTypeIdEnum.ROADMAP)
        .overviewMapControl(false)
        .panControl(false)
        .scaleControl(false)
        .streetViewControl(false)
        .zoomControl(false)
        .zoom(12);

        //creating map object
        map = mapViewer.createMap(mapOptions);

        map.addMouseEventHandler(UIEventType.click,(GMapMouseEvent event)->
        {
            LatLong latLong = event.getLatLong();
            System.out.println("Latitude "+latLong.getLatitude());
            System.out.println("Longitude "+latLong.getLongitude());
        });

        //Creating markers for every car entity that is in database
        // the title of marker is car id so it's easier to find them
        for (CarEntity car:carList
        ) {
            final LatLong position = new LatLong(car.getLatitude(),car.getLongitude());
            final MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(position)
                    .visible(true)
                    .title(String.valueOf(car.getId()));



            //Creating info windows that would pop up after car's marker is clicked
            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
            infoWindowOptions.content(MyMarker.createWindowContent(car));

            InfoWindow infoWindow = new InfoWindow(infoWindowOptions);

            final Marker marker = new MyMarker(markerOptions,String.valueOf(car.getId()),position,infoWindow);

            //Adding marker to marker's list so its easy to store their references
            carMarkersList.add(marker);
            map.addMarker(marker);


            //Creating event handler that would fire after the marker is clicked.
            //It focuses table view on row that is associated with selected marker.
            //It also clears all of the other markers that were open.
            map.addUIEventHandler(marker,UIEventType.click,(JSObject obj)->{

                carMarkersList.forEach(x->((MyMarker)x).getInfoWindow().close());
                ((MyMarker) marker).getInfoWindow().open(map,marker);

                Platform.runLater(()->{
                    carTable.requestFocus();
                    carTable.getSelectionModel().select(Integer.valueOf(marker.getTitle())-1);
                    carTable.scrollTo(Integer.valueOf(marker.getTitle())-1);
                });
           });

            //Populating table view with car's data
            populateTable();

        }
    }

    private void populateTable(){

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        batteryLvlColumn.setCellValueFactory(new PropertyValueFactory<>("batteryLvlPct"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        batteryDistColumn.setCellValueFactory(new PropertyValueFactory<>("batteryDistance"));

        carTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        for (CarEntity car:carList
             ) {
            final CarView carView = new CarView((int) car.getId(),car.getName(),car.getBatteryLvlPct(),car.getAddress(),car.getStatus(),car.getBatteryDistance());
            carTable.getItems().add(carView);
        }
    }

    private Marker findMarkerByName(String name){
        for (final Marker marker:carMarkersList){
            if(((MyMarker) marker).isTitle(name)){
                return marker;
            }
        }
        return null;
    }

    public void passUser(UserEntity user){
        this.loggedUser = user;
    }

    @FXML
    public void showRentalStatus(ActionEvent actionEvent) {
        transactionService.setDao(dao);
        TransactionEntity transaction = transactionService.findByUser(loggedUser.getId());

        if(transaction !=null){

            try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/rentalStatusWindow.fxml"));
            Parent root = loader.load();

            carService.setDao(dao);
            CarEntity carEntity = carService.findOne(transaction.getCarId());

            RentalStatusController rentalStatusController = loader.getController();
            rentalStatusController.passCar(carEntity);
            setNewStage(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            DialogUtils.popupWindow("Unavailable ",2);
        }
    }

    static void setNewStage(Parent root) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
    }
}
