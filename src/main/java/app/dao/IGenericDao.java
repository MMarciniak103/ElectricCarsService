package app.dao;

import java.util.List;
import java.io.Serializable;

/**
 * Interface that provides abstraction for Generic Dao Functionality.
 * @param <T> Entity's class (needs to extends Serializable class)
 */
public interface IGenericDao<T extends Serializable> {
    T findOne(long id);

    List<T> findAll();

    T create(T entity);

    void update(T entity);

    void delete(T entity);

    void deleteById(long entityId);

    T queryForOne(String query);

}
