package app.dao;

import app.util.HibernateConfig;
import org.hibernate.Session;

import java.util.List;
import java.io.Serializable;

/**
 * Class that implements Data Access Object. It uses generic parameters so it can be easily reproduced and implemented.
 * It is the lowest layer of application architecture that is used to interact with database.
 * @param <T> Class of Dao's entity.
 */
public  class GenericHibernateDao<T extends Serializable> implements IGenericDao<T>{

    private Class<T> clazz;



    public void setClazz(Class<T> clazzToSet){
        this.clazz = clazzToSet;
    }

    /**
     * Creates connection with database and finds entity by given id.
     * @param id id of entity
     * @return Entity object.
     */
    public T findOne(long id){
        T entity;
        try(Session session =HibernateConfig.getSessionFactory().openSession()) {
            entity = session.get(clazz, id);
        }
        return entity;
    }

    /**
     * Creates connection with database and query for all rows from given table.
     * @return List of entities.
     */
    public List findAll(){
        List entities;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
         entities  = session.createQuery("FROM " + clazz.getName()).list();
        }
        return  entities; 
    }

    /**
     * Creates connection with database and inserts new Entity into table.
     * @param entity Reference to new entity.
     * @return created Entity.
     */
    public T create(T entity){
        try(Session session =HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
        }
        return entity;
    }

    /**
     * Creates connection with database and updates given record.
     * @param entity record that we want to update.
     * @return updated entity.
     */
    public void update(T entity){
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
        }
    }

    /**
     * Creates connection with database and deletes given record.
     * @param entity record that we want to delete.
     */
    public void delete(T entity){
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
    }

    /**
     * It invokes delete function with Id of record that we want to delete.
      * @param entityId record's id.
     */
    public void deleteById(long entityId){ ;
        T entity = findOne(entityId);
        delete(entity);
    }

    /**
     * It creates connection with database and creates query that returns single result.
     * @param query Query that we want to run.
     * @return entity that is result of given query.
     */
    public T queryForOne(String query){
        T result;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
                result = (T) session.createQuery(query).getSingleResult();
        }
        return result;
    }

    public Long queryForID(String query){
        Long result;
        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            result = (Long) session.createQuery(query).getSingleResult();
        }
        return result;
    }


    protected Session getCurrentSession(){
        return  HibernateConfig.getSessionFactory().getCurrentSession();
    }

}
