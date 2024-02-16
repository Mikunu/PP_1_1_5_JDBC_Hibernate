package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection;
    private final static String CREATE_USERS_QUERY = "CREATE TABLE IF NOT EXISTS users " +
            "(id BIGINT not NULL AUTO_INCREMENT, " +
            "name TEXT not NULL, " +
            "lastName TEXT not NULL, " +
            "age TINYINT not NULL, " +
            "PRIMARY KEY (id))";
    private static final String DROP_USERS_QUERY = "DROP TABLE IF EXISTS users";
    private static final String SAVE_USER_QUERY = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
    private static final String DELETE_USER_QUERY = "DELETE from users WHERE id = ";
    private static final String SELECT_USERS_QUERY = "SELECT id, name, lastName, age FROM users";
    private static final String CLEAN_USERS_QUERY = "TRUNCATE TABLE users";

    public UserDaoJDBCImpl() {
        connection = new Util().getConnection();
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_USERS_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_USERS_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection.prepareStatement(SAVE_USER_QUERY)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_USER_QUERY + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_USERS_QUERY);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CLEAN_USERS_QUERY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if ((connection != null) && (!connection.isClosed())) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
