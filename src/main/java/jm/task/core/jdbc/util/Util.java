package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();

        configuration.setProperty(Environment.DRIVER, DB_DRIVER);
        configuration.setProperty(Environment.URL, URL);
        configuration.setProperty(Environment.USER, USERNAME);
        configuration.setProperty(Environment.PASS, PASSWORD);
        configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty(Environment.SHOW_SQL, "false");
        configuration.setProperty(Environment.FORMAT_SQL, "true");

        return configuration;
    }

    public static SessionFactory getSessionFactory() {
        Configuration configuration = getConfiguration();

        configuration.addAnnotatedClass(User.class);

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        return configuration.buildSessionFactory(serviceRegistry);
    }
}
