package app.dao;

import java.util.List;

public interface DaoInteface<T> {

    List<T> getEntities();

    void saveEntity(T object);

    T getEntity(long theId);

    void deleteEntity(int theId);

}
