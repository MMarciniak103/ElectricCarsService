package app.controllers;

import app.dao.GenericHibernateDao;
import app.entity.CarEntity;
import app.entity.ProfitEntity;
import app.modelViews.CarView;
import app.service.CarService;
import app.service.ProfitService;
import app.util.CloseApplication;
import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller for Admin Panel View
 */
public class AdminController {

    @FXML
    public JFXButton logoutBtn;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane adminPane;

    @FXML
    private TableView<CarView> garageTable;

    @FXML
    private TableColumn<CarView, String> nameColumn;

    @FXML
    public TableColumn<CarView, CarView> rechargeColumn;

    @FXML
    private TableColumn<CarView, Integer> batteryLvlColumn;

    @FXML
    private TableColumn<CarView, String> addressColumn;

    @FXML
    private TableColumn<CarView, String> statusColumn;

    @FXML
    private TableColumn<CarView, Integer> batteryDistColumn;

    @FXML
    private JFXButton backBtn;

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private CategoryAxis xAxisLC;

    @FXML
    private NumberAxis yAxisLC;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxisBC;

    @FXML
    private NumberAxis yAxisBC;


    private GenericHibernateDao dao;
    private ProfitService profitService;
    private CarService carService;

    private HashMap<String, Number> profitsMap;
    private List<CarEntity> carsList;

    @FXML
    void initialize() {
        assert adminPane != null : "fx:id=\"adminPane\" was not injected: check your FXML file 'adminPanel.fxml'.";
        assert garageTable != null : "fx:id=\"garageTable\" was not injected: check your FXML file 'adminPanel.fxml'.";
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'adminPanel.fxml'.";
        assert lineChart != null : "fx:id=\"lineChart\" was not injected: check your FXML file 'adminPanel.fxml'.";
        assert xAxisLC != null : "fx:id=\"xAxisLC\" was not injected: check your FXML file 'adminPanel.fxml'.";
        assert yAxisLC != null : "fx:id=\"yAxisLC\" was not injected: check your FXML file 'adminPanel.fxml'.";
        assert barChart != null : "fx:id=\"barChart\" was not injected: check your FXML file 'adminPanel.fxml'.";
        assert xAxisBC != null : "fx:id=\"xAxisBC\" was not injected: check your FXML file 'adminPanel.fxml'.";
        assert yAxisBC != null : "fx:id=\"yAxisBC\" was not injected: check your FXML file 'adminPanel.fxml'.";


        dao = new GenericHibernateDao();

        profitService = new ProfitService();
        profitService.setDao(dao);

        List<ProfitEntity> profits = profitService.findAll();

        profitsMap = mapProfits(profits);

        XYChart.Series<String, Number> series = prepareChartSeries();

        initializeChart(lineChart, series);
        initializeChart(barChart, series);

        carService = new CarService();
        carService.setDao(dao);


        carsList = carService.findInGarage();


        populateTable();
    }

    /**
     * Maps profits from database to hashmap
     * @param profits list of profit's records
     * @return hashmap with mapped profits.
     */
    private HashMap<String, Number> mapProfits(List<ProfitEntity> profits) {
        HashMap<String, Number> profitsMap = new HashMap<>();
        //Iterate over profit list and increment profit for a given day
        for (ProfitEntity profit : profits
        ) {
            if (profitsMap.containsKey(String.valueOf(profit.getDate()))) {
                Double value = (Double) profitsMap.get(String.valueOf(profit.getDate()));
                value += profit.getProfit();
                profitsMap.put(String.valueOf(profit.getDate()), value);
            } else {
                profitsMap.put(String.valueOf(profit.getDate()), profit.getProfit());
            }
        }

        return profitsMap;
    }

    /**
     * Prepares series based on hashmap values. It sorts it by keys, that are Strings and represents date.
     * @return prepared chart series.
     */
    private XYChart.Series<String, Number> prepareChartSeries() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        // add every record in map to series object
        for (String key : profitsMap.keySet()) {
            series.getData().add(new XYChart.Data<>(key, profitsMap.get(key)));
        }
        // sort series data by x value - that is string that represents date
        series.getData().sort(Comparator.comparing(XYChart.Data::getXValue));
        return series;
    }


    private void initializeChart(XYChart chart, XYChart.Series<String, Number> series) {
        chart.getData().add(series);
        chart.legendVisibleProperty().setValue(false);
        chart.getXAxis().setLabel("Date");
        chart.getYAxis().setLabel("Total Profit $");
    }


    private void populateTable() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        batteryLvlColumn.setCellValueFactory(new PropertyValueFactory<>("batteryLvlPct"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        batteryDistColumn.setCellValueFactory(new PropertyValueFactory<>("batteryDistance"));

        // prepare new column that contains button. This button allows admin to recharge car that has low battery.
//        garageTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        rechargeColumn.setCellFactory(param -> {
            return new TableCell<CarView, CarView>() {
                //create new button with deaful icon
                JFXButton button = createButton("/icons/car-battery.png");

                @Override
                protected void updateItem(CarView item, boolean empty) {
                    super.updateItem(item, empty);

                    // bind button with selection mode
                    button.disableProperty().bind(Bindings.isNull(garageTable.getSelectionModel().selectedItemProperty()));

                    if (empty) {
                        setGraphic(null);
                        return;
                    } else {
                        setGraphic(button);
                        button.setOnAction(event -> {
                            //get id of selected car
                            long carId = garageTable.getSelectionModel().getSelectedItem().getId();
                            carService.setDao(dao);
                            // query for car entity in db with given id
                            CarEntity car = carService.findOne(carId);
                            // recharge selected car
                            car.setBatteryLvlPct(100);
                            garageTable.getSelectionModel().getSelectedItem().setBatteryLvlPct(100);
                            // update car in database
                            carService.update(car);
                            // remove updated car from table view
                            garageTable.getItems().remove(garageTable.getSelectionModel().getSelectedItem());

                            garageTable.refresh();
                        });
                    }
                }
            };
        });

        for (CarEntity car : carsList
        ) {
            final CarView carView = new CarView((int) car.getId(), car.getName(), car.getBatteryLvlPct(), car.getAddress(), car.getStatus(), car.getBatteryDistance());
            garageTable.getItems().add(carView);
        }

    }

    @FXML
    void goBack(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
        try {
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Logout(ActionEvent actionEvent) {
        CloseApplication.cloeApplication();

    }

    private JFXButton createButton(String path) {
        JFXButton button = new JFXButton();
        Image image = new Image(this.getClass().getResource(path).toString());
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
        return button;
    }
}
