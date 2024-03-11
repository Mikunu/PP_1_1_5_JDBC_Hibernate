package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_USERS_QUERY = "CREATE TABLE IF NOT EXISTS users " +
            "(id BIGINT not NULL AUTO_INCREMENT, " +
            "name TEXT not NULL, " +
            "lastName TEXT not NULL, " +
            "age TINYINT not NULL, " +
            "PRIMARY KEY (id))";
    private static final String DROP_USERS_QUERY = "DROP TABLE IF EXISTS users";
    private static final String SELECT_USERS_QUERY = "SELECT u FROM User u";
    private static final String CLEAN_USERS_QUERY = "TRUNCATE TABLE users";

    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        this.sessionFactory = Util.getSessionFactory();
    }

    private void sqlQuery(String sql) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUsersTable() {
        sqlQuery(CREATE_USERS_QUERY);
    }

    @Override
    public void dropUsersTable() {
        sqlQuery(DROP_USERS_QUERY);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = null;
        try (Session session = sessionFactory.openSession()) {
            usersList = session.createQuery(SELECT_USERS_QUERY, User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        sqlQuery(CLEAN_USERS_QUERY);
    }
}
