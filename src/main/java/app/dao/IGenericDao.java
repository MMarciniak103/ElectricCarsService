package app.dao;

import java.util.List;
import java.io.Serializable;

public interface IGenericDao<T extends Serializable> {
    T findOne(long id);

    List<T> findAll();

    T create(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(long entityId);

    T queryForOne(String query);

}
