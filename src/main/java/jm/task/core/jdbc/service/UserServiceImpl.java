package jm.task.core.jdbc.service;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import java.util.List;

public class UserServiceImpl implements UserService {

    UserDaoHibernateImpl userDaoHibernate;

    public UserServiceImpl() {
        userDaoHibernate = new UserDaoHibernateImpl();
    }

    @Override
    public void createUsersTable() {
        userDaoHibernate.createUsersTable();
        System.out.println("Таблица создана");
    }

    @Override
    public void dropUsersTable() {
        userDaoHibernate.dropUsersTable();
        System.out.println("Таблица удалена");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        userDaoHibernate.saveUser(name, lastName, age);
        System.out.printf("User с именем – %s добавлен в базу данных\n", name);
    }

    @Override
    public void removeUserById(long id) {
        userDaoHibernate.removeUserById(id);
        System.out.printf("User с ID – %d удален из базы данных", id);
    }

    @Override
    public List<User> getAllUsers() {
        System.out.println("Получены пользователи");
        return userDaoHibernate.getAllUsers();
    }

    @Override
    public void cleanUsersTable() {
        userDaoHibernate.cleanUsersTable();
        System.out.println("Таблица очищена");
    }
}
