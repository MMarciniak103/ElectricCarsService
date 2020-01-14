package app.dao;

import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public class GenericHibernateDao<T extends Serializable> extends AbstractHibernateDao<T> implements IGenericDao<T> {


    public GenericHibernateDao() {
        super();
    }

    @Override
    public void setClazz(Class<T> clazzToSet) {
        super.setClazz(clazzToSet);
    }

    @Override
    public T findOne(long id) {
        return super.findOne(id);
    }

    @Override
    public List findAll() {
        return super.findAll();
    }

    @Override
    public T create(T entity) {
        return super.create(entity);
    }

    @Override
    public T update(T entity) {
        return super.update(entity);
    }

    @Override
    public void delete(T entity) {
        super.delete(entity);
    }

    @Override
    public void deleteById(long entityId) {
        super.deleteById(entityId);
    }

    @Override
    public T queryForOne(String query) {
        return super.queryForOne(query);
    }

    @Override
    protected Session getCurrentSession() {
        return super.getCurrentSession();
    }
}
