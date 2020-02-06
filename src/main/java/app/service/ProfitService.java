package app.service;

import app.dao.GenericHibernateDao;
import app.entity.ProfitEntity;

import java.util.List;

public class ProfitService implements ServiceInterface<ProfitEntity> {

    private GenericHibernateDao<ProfitEntity> dao;

    @Override
    public void setDao(GenericHibernateDao<ProfitEntity> dao) {
        this.dao = dao;
        this.dao.setClazz(ProfitEntity.class);
    }

    @Override
    public ProfitEntity findOne(long id) {
        return dao.findOne(id);
    }

    @Override
    public List findAll() {
        return dao.findAll();
    }

    @Override
    public ProfitEntity create(ProfitEntity entity) {
        return dao.create(entity);
    }

    @Override
    public void update(ProfitEntity entity) {
        dao.update(entity);
    }

    @Override
    public void delete(ProfitEntity entity) {
        dao.delete(entity);
    }

    @Override
    public void deleteById(long entityId) {
        dao.deleteById(entityId);
    }
}
