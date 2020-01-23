package app.service;

import app.dao.GenericHibernateDao;
import app.entity.TransactionEntity;


import javax.persistence.NoResultException;
import java.util.List;

public class TransactionService implements ServiceInterface<TransactionEntity> {

    private GenericHibernateDao<TransactionEntity> dao;

    @Override
    public void setDao(GenericHibernateDao dao) {
        this.dao = dao;
        dao.setClazz(TransactionEntity.class);
    }

    @Override
    public TransactionEntity findOne(long id) {

        return dao.findOne(id);
    }

    @Override
    public List findAll() {
        return dao.findAll();
    }


    @Override
    public TransactionEntity create(TransactionEntity entity) {
        return dao.create(entity);
    }

    @Override
    public void update(TransactionEntity entity) {
        dao.update(entity);
    }

    @Override
    public void delete(TransactionEntity entity) {
        dao.delete(entity);
    }

    public TransactionEntity findSpecificTransaction(long carId,long userId){
        TransactionEntity transactionEntity = null;
        try{
            transactionEntity = dao.queryForOne("from TransactionEntity where carId = "+carId+" and clientId = "+userId);

        }catch (NoResultException ex){}
        return transactionEntity;
    }

    @Override
    public void deleteById(long entityId) {
        dao.deleteById(entityId);
    }

    public TransactionEntity findByUser(long userId){
        TransactionEntity transactionEntity = null;
        try{
            transactionEntity = dao.queryForOne("from TransactionEntity where clientId = "+userId);
        }catch (NoResultException ex){
        }
        return transactionEntity;
    }


    public Long countByUserRented(long userId){
            Long result = dao.queryForCount("select count(clientId) from TransactionEntity where clientId = " + userId + " and completed = 'RENTED'");
            return result;
    }



    public TransactionEntity findByUserIncompleted(long userId){
        TransactionEntity transactionEntity = null;
        try{
            transactionEntity = dao.queryForOne("from TransactionEntity where clientId = "+userId+" and completed = 'RENTED' ");
        }catch (NoResultException ex){
        }
        return transactionEntity;
    }


}
