package sfedu.xast.utils;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import sfedu.xast.models.*;

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
            metadataSources.addAnnotatedClass(PersInf.class);
            metadataSources.addAnnotatedClass(ProfInf.class);
            metadataSources.addAnnotatedClass(SkillExchange.class);
            metadataSources.addAnnotatedClass(Review.class);
            metadataSources.addAnnotatedClass(Transaction.class);

            //TASK WITH STRATEGIES : (
            //PERSON : ()
            metadataSources.addAnnotatedClass(sfedu.xast.srtategyTask.JoinedTable.Person.class);
            metadataSources.addAnnotatedClass(sfedu.xast.srtategyTask.MappedSuperclass.Person.class);
            metadataSources.addAnnotatedClass(sfedu.xast.srtategyTask.SingleTable.Person.class);
            metadataSources.addAnnotatedClass(sfedu.xast.srtategyTask.TablePerClass.Person.class);
            //WITH_KADASTR : )
            metadataSources.addAnnotatedClass(sfedu.xast.srtategyTask.JoinedTable.PersInf.class);
            metadataSources.addAnnotatedClass(sfedu.xast.srtategyTask.MappedSuperclass.PersInf.class);
            metadataSources.addAnnotatedClass(sfedu.xast.srtategyTask.SingleTable.PersInf.class);
            metadataSources.addAnnotatedClass(sfedu.xast.srtategyTask.TablePerClass.PersInf.class);
            //DEBTOR : /
            metadataSources.addAnnotatedClass(sfedu.xast.srtategyTask.JoinedTable.ProfInf.class);
            metadataSources.addAnnotatedClass(sfedu.xast.srtategyTask.MappedSuperclass.ProfInf.class);
            metadataSources.addAnnotatedClass(sfedu.xast.srtategyTask.SingleTable.ProfInf.class);
            metadataSources.addAnnotatedClass(sfedu.xast.srtategyTask.TablePerClass.ProfInf.class);

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