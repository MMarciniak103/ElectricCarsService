package app.database.mapping;

import app.dao.GenericHibernateDao;
import app.entity.CarEntity;
import app.service.CarService;
import com.google.gson.Gson;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Class that provides functionality to parse json objects into POJO and populate given database with them.
 */
public class DataMapper {

    private GenericHibernateDao dao;
    private CarService carService;

    public DataMapper() {
        this.dao = new GenericHibernateDao();
        this.dao.setClazz(CarEntity.class);

        carService = new CarService();
        carService.setDao(this.dao);
    }

    /**
     * Method accepts path to json file and then converts its elements into VehicleBase Object.
     * @param path path to json file.
     * @return List of vehicles entities.
     */
    private VehicleBase getVehiclesList(String path){
        Gson gson = new Gson();
        StringBuffer response = new StringBuffer();
        VehicleBase vehicleBase = new VehicleBase();
        try(BufferedReader fileReader = new BufferedReader(new FileReader(new File(path)))){

            String inputLine;
            while((inputLine = fileReader.readLine()) != null){
                response.append(inputLine);
            }

            vehicleBase = gson.fromJson(response.toString(),VehicleBase.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  vehicleBase;
    }


    /**
     * Methods that would be called if we want to populate out database with parsed objects (vehicles)
     * @param path path to json file.
     */
    public void populateDatabase(String path){
        VehicleBase vehicleBase = getVehiclesList(path);
        Vehicle[] vehicles = vehicleBase.getVehicles();
        List<Vehicle> vehicleList = Arrays.asList(vehicles);
        Random random = new Random();

        for(Vehicle vehicle:vehicleList){
            CarEntity tempCar = new CarEntity();
            tempCar.setName(vehicle.getName());
            tempCar.setPlatesNumber(vehicle.getPlatesNumber());
            tempCar.setRangeKM(vehicle.getRangeKm());
            tempCar.setBatteryLvlPct(vehicle.getBatteryLevelPct());
            tempCar.setAddress(vehicle.getAddress());
            tempCar.setLatitude(vehicle.getLocation().getLatitude());
            tempCar.setLongitude(vehicle.getLocation().getLongitude());
            tempCar.setStatus(vehicle.getStatus());
            tempCar.setInsurancePeriod(LocalDate.now().plusYears(random.nextInt(7)+1));
            tempCar.setBatteryDistance(random.nextInt(20)+80);
            carService.create(tempCar);
        }
    }
}
