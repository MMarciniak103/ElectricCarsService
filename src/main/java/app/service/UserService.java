package app.service;

import app.dao.GenericHibernateDao;
import app.entity.UserEntity;

import javax.persistence.NoResultException;
import java.util.List;

public class UserService implements ServiceInterface<UserEntity>{

    GenericHibernateDao<UserEntity> dao;

    public void setDao(GenericHibernateDao<UserEntity> daoToSet){
        this.dao = daoToSet;
        this.dao.setClazz(UserEntity.class);
    }


    @Override
    public UserEntity findOne(long id) {
        UserEntity user = null;
        try {
            user = dao.findOne(id);
        }catch (NoResultException ex){

        }
        return user;
    }

    public UserEntity findByName(String name){
        UserEntity user = null;
        try{
            user = dao.queryForOne("from UserEntity where login = '"+name+"'");
        }catch (NoResultException ex){

        }
        return user;
    }

    public UserEntity findUser(String login,String psw){

        UserEntity user = null;

        try{
            user = dao.queryForOne("from UserEntity where login = '" + login + "' and password = SHA1('" + psw + "')");
        }catch (NoResultException ex){
        }
        return user;
    }
    @Override
    public List findAll() {
        return dao.findAll();
    }

    @Override
    public UserEntity create(UserEntity entity) {
        return dao.create(entity);
    }

    @Override
    public UserEntity update(UserEntity entity) {
        return dao.update(entity);
    }

    @Override
    public void delete(UserEntity entity) {
        dao.delete(entity);
    }

    @Override
    public void deleteById(long entityId) {
        dao.deleteById(entityId);
    }


}
