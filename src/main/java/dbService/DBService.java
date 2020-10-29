package dbService;

import dbService.dao.UserDAO;
import dbService.data.UserData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class DBService {
    private static SessionFactory sessionFactory;

    public DBService() {
        Configuration configuration = getConfiguration();
        sessionFactory = createSessionFactory(configuration);
        System.out.println("The connection to the DBMS is complete.");
    }

    public UserData getUser(String login) {
        Session session = sessionFactory.openSession();
        UserData userData = new UserDAO(session).getUser(login);
        session.close();
        return userData;
    }

    public void addUser(UserData userData)  {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserDAO userDAO = new UserDAO(session);
        userDAO.insertUser(userData.getLogin(), userData.getPassword(), userData.getEmail());
        transaction.commit();
        session.close();
    }

    public static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserData.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/jdbc?serverTimezone=UTC");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

}

