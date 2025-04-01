package sfedu.xast.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.File;

import static sfedu.xast.utils.Constants.LAB1_HBN_CFG;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    /**
     * Создание фабрики
     *
     */

    static {
        try {
            File file = new File(LAB1_HBN_CFG);
            Configuration configuration = new Configuration().configure(file).addPackage("sfedu.xast.models") ;

            ServiceRegistry serviceRegistry
                    = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            //metadataSources.addResource("named-queries.hbm.xml");
            sessionFactory = metadataSources.buildMetadata().buildSessionFactory();

        } catch (Exception e) {
            System.err.println("Ошибка при инициализации SessionFactory: " + e);
            throw new ExceptionInInitializerError(e);
        }

    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}