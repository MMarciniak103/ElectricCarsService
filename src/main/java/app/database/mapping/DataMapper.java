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

public class DataMapper {

    private GenericHibernateDao dao;
    private CarService carService;

    public DataMapper() {
        this.dao = new GenericHibernateDao();
        this.dao.setClazz(CarEntity.class);

        carService = new CarService();
        carService.setDao(this.dao);
    }

    public VehicleBase getVehiclesList(String path){
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
            tempCar.setBatteryDistance(random.nextInt(80)+80);
            carService.create(tempCar);
        }
    }
}
