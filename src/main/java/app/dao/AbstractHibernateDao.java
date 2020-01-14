package app.dao;

import app.util.HibernateConfig;
import org.hibernate.Session;

import java.util.List;
import java.io.Serializable;

public abstract class AbstractHibernateDao<T extends Serializable> {

    private Class<T> clazz;

    public void setClazz(Class<T> clazzToSet){
        this.clazz = clazzToSet;
    }

    public T findOne(long id){
        T entity;
        try(Session session =HibernateConfig.getSessionFactory().openSession()) {
            entity = session.get(clazz, id);
        }
        return entity;
    }

    public List findAll(){
        List entities;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
         entities  = session.createQuery("FROM " + clazz.getName()).list();
        }
        return  entities; 
    }

    public T create(T entity){
        try(Session session =HibernateConfig.getSessionFactory().openSession()) {
            session.saveOrUpdate(entity);
        }
        return entity;
    }

    public T update(T entity){
        T result;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            result = (T) session.merge(entity);
        }
        return result;
    }

    public void delete(T entity){
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.delete(entity);
        }    }

    public void deleteById(long entityId){
        T entity = findOne(entityId);
        delete(entity);
    }

    public T queryForOne(String query){
        T result;
        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
                result = (T) session.createQuery(query).getSingleResult();
        }
        return result;
    }


    protected Session getCurrentSession(){
        return  HibernateConfig.getSessionFactory().getCurrentSession();
    }

}
