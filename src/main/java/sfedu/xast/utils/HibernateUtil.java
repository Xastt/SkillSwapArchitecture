package sfedu.xast.utils;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import sfedu.xast.models.Address;
import sfedu.xast.models.TestEntity;

import java.io.File;

import static sfedu.xast.utils.Constants.HBN_CFG;

public class HibernateUtil {

    @Getter
    private static SessionFactory sessionFactory;

    static {
        try {
            File file = new File(HBN_CFG);
            Configuration configuration = new Configuration()
                    .configure(file);

            ServiceRegistry serviceRegistry
                    = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            metadataSources.addAnnotatedClass(TestEntity.class);
            metadataSources.addAnnotatedClass(Address.class);
            sessionFactory = metadataSources.buildMetadata().buildSessionFactory();

        } catch (Exception e) {
            System.err.println("Ошибка при инициализации SessionFactory: " + e);
            throw new ExceptionInInitializerError(e);
        }

    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}