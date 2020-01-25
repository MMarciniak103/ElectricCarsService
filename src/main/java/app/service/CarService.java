package app.service;

import app.dao.GenericHibernateDao;
import app.entity.CarEntity;
import org.hibernate.SQLQuery;

import javax.persistence.NoResultException;
import java.util.List;

public class CarService implements ServiceInterface<CarEntity> {

    GenericHibernateDao<CarEntity> dao;

    @Override
    public void setDao(GenericHibernateDao<CarEntity> daoToSet){
        this.dao = daoToSet;
        this.dao.setClazz(CarEntity.class);
    }


    @Override
    public CarEntity findOne(long id) {
        CarEntity carEntity = null;
        try {
            carEntity = dao.findOne(id);
        }catch (NoResultException ex){

        }
        return carEntity;
    }

    public List<CarEntity> findInGarage(){
        List<CarEntity >cars = null;
        cars = dao.nativeQuery("select * from vehicles where id in (select car_id from charging_cars);",CarEntity.class);
        return cars;
    }

    @Override
    public List findAll() {
        return dao.findAll();
    }

    @Override
    public CarEntity create(CarEntity entity) {
        return dao.create(entity);
    }

    @Override
    public void update(CarEntity entity) {
        dao.update(entity);
    }

    @Override
    public void delete(CarEntity entity) {
        dao.delete(entity);
    }

    @Override
    public void deleteById(long entityId) {
        dao.deleteById(entityId);
    }
}
