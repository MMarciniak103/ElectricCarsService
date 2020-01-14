package app.service;


import java.io.Serializable;
import java.util.List;

public interface ServiceInterface<T extends Serializable> {


   T findOne(long id);

    List findAll();

    T create(T entity);

    T update(T entity);

   void delete(T entity);

   void deleteById(long entityId);


}
