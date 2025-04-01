package sfedu.xast.api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class HibernateDataProvider {

    private final SessionFactory sessionFactory;

    public HibernateDataProvider(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Метод для получения списка таблиц в базе данных
    public List<String> getTableNames() {
        try (Session session = sessionFactory.openSession()) {
            NativeQuery<String> query = session.createNativeQuery(
                    "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'", String.class);
            return query.getResultList();
        }
    }

    // Метод для получения размера базы данных
    public String getDatabaseSize() {
        try (Session session = sessionFactory.openSession()) {
            NativeQuery<String> query = session.createNativeQuery(
                    "SELECT pg_size_pretty(pg_database_size(current_database()))", String.class);
            return query.getSingleResult();
        }
    }

    // Метод для получения списка пользователей
    public List<String> getUsers() {
        try (Session session = sessionFactory.openSession()) {
            NativeQuery<String> query = session.createNativeQuery(
                    "SELECT usename FROM pg_user", String.class);
            return query.getResultList();
        }
    }

    // Метод для получения типов данных таблиц
    public List<String> getColumnTypes(String tableName) {
        try (Session session = sessionFactory.openSession()) {
            NativeQuery<String> query = session.createNativeQuery(
                    "SELECT data_type FROM information_schema.columns WHERE table_name = :tableName", String.class);
            query.setParameter("tableName", tableName);
            return query.getResultList();
        }
    }

}
