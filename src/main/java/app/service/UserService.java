package app.service;

import app.dao.GenericHibernateDao;
import app.entity.UserEntity;

import javax.persistence.NoResultException;
import java.util.List;

public class UserService implements ServiceInterface<UserEntity>{

    GenericHibernateDao<UserEntity> dao;

    @Override
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

    /**
     * Looks for an user with a given name.
     * @param name Name of user
     * @return User Entity
     */
    public UserEntity findByName(String name){
        UserEntity user = null;
        try{
            user = dao.queryForOne("from UserEntity where login = '"+name+"'");
        }catch (NoResultException ex){

        }
        return user;
    }


    /**
     * Methods that is used to authorize user. It looks in db for an user with given login and password.
     * The password is encrypted with SHA1.
     * @param login Login of user
     * @param psw Password of user.
     * @return User entity
     */
    public UserEntity findUser(String login,String psw){

        UserEntity user = null;
        try {

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
    public void update(UserEntity entity) {
         dao.update(entity);
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
