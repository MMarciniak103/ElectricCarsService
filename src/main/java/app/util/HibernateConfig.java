package app.util;


import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Class that provides configuration of Hibernate library for this project.
 * It creates session factory and implements method that allows to use it in other parts of application.
 */
public class HibernateConfig {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            try{
                registry = new StandardServiceRegistryBuilder().configure().build();

                MetadataSources sources = new MetadataSources(registry);

                Metadata metadata = sources.getMetadataBuilder().build();

                sessionFactory = metadata.getSessionFactoryBuilder().build();
//                sessionFactory = new Configuration()
//                        .configure("hibernate.cfg.xml")
//                        .addAnnotatedClass(CarEntity.class)
//                        .addAnnotatedClass(UserEntity.class)
//                        .addAnnotatedClass(TransactionEntity.class)
//                        .buildSessionFactory();
            }
            catch (Exception ex){
                ex.printStackTrace();
                if(registry != null){
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown(){
        if(registry != null){
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
