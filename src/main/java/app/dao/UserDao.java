package app.dao;

import app.entity.UserEntity;
import app.util.HibernateConfig;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public class UserDao implements DaoInteface {


    @Override
    public  List getEntities() {

        List users = null;

        try(Session session = HibernateConfig.getSessionFactory().openSession()) {
            Query<UserEntity> theQuery = session.createQuery("from UserEntity", UserEntity.class);

            users = theQuery.getResultList();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public void saveEntity(Object user) {

    }

    @Override
    public UserEntity getEntity(long theId) {
        return null;
    }

    @Override
    public void deleteEntity(int theId) {

    }

    public UserEntity findUser(String login,String psw){

        UserEntity user = null;

        try(Session session = HibernateConfig.getSessionFactory().openSession()){
            Query<UserEntity> theQuery = session.createQuery("from UserEntity where login = :logintxt and password = SHA1(:passwordtxt)");
            theQuery.setParameter("logintxt",login);
            theQuery.setParameter("passwordtxt",psw);

            user = theQuery.getSingleResult();
        }catch (NoResultException ex){
        }
        return user;
    }
}
