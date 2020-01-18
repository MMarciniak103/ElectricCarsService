package app.service;


import app.dao.GenericHibernateDao;

import java.io.Serializable;
import java.util.List;

public interface ServiceInterface<T extends Serializable> {

    void setDao(GenericHibernateDao<T> dao);

   T findOne(long id);

    List findAll();

    T create(T entity);

    void update(T entity);

   void delete(T entity);

   void deleteById(long entityId);


}
