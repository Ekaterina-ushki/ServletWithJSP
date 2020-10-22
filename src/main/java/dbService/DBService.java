package dbService;

import dbService.dao.UserDAO;
import dbService.data.UserData;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService {
    private final Connection connection;

    public DBService() {
        this.connection = getMysqlConnection();
        System.out.println("Соединение с СУБД выполнено.");
    }

    public UserData getUser(String login) throws DBException {
        try {
            return (new UserDAO(connection).getUser(login));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public boolean checkUserExists(String login) throws DBException {
        try {
            return (new UserDAO(connection).checkUserExists(login));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void addUser(UserData userData) throws DBException {
        try {
            connection.setAutoCommit(false);
            UserDAO dao = new UserDAO(connection);
            dao.createTable();
            dao.insertUser(userData.getLogin(), userData.getPassword(), userData.getEmail());
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }


    public void cleanUp() throws DBException {
        UserDAO dao = new UserDAO(connection);
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        //db type
                    append("localhost:").           //host name
                    append("3306/").                //port
                    append("jdbc?").          //db name
                    append("user=root&").          //login
                    append("password=root&").   //password
                    append("serverTimezone=UTC");

            System.out.println("URL: " + url + "\n");

            return DriverManager.getConnection(url.toString());
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

