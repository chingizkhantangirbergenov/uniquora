package jdbc;

import model.AllUsers;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserJdbc {

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/uniquora";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection connection = null;

    public static boolean validate() {
        connection = getDBConnection();
        return connection != null;
    }

    public static boolean checkUserByEmail(String email) {
        if (validate()) {
            String query = "select * from all_users u where u.email_ = '" + email + "';";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(query);
                ResultSet rs = preparedStatement.executeQuery(query);
                if (rs.next()) {
                    System.out.println("YES");
                    return true;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("NO");

        return false;
    }

    public static AllUsers getUserFromAllUsersByEmail(String email) {
        AllUsers user = null;
        if (validate()) {
            String query = "select * from all_users u where u.email_ = '" + email + "';";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(query);
                ResultSet rs = preparedStatement.executeQuery(query);
                if (rs.next()) {
                    user = new AllUsers();
                    user.id = rs.getInt("id");
                    user.fullName = rs.getString("fullname_");
                    user.email = rs.getString("email_");
                    user.idNumber = rs.getString("id_number_");
                    user.school = rs.getString("school_");

                    return user;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("NO");

        return user;
    }

    public static boolean addUser(User user) {
        if (validate()) {
            String query = "insert into users(firstName, lastName, email, hashedPassword, userCode) values (?, ?, ?, ?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, user.firstName);
                preparedStatement.setString(2, user.lastName);
                preparedStatement.setString(3, user.email);
                preparedStatement.setString(4, user.hashedPassword);
                preparedStatement.setString(5, user.userCode);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    public static List<User> getUsersByName(String name) {
        List<User> allUsers = null;
        List<User> users = new ArrayList<>();
        if (validate()) {
            // ToDo get users from sql, not from java code
            String selectSQL = "select * from users";
            allUsers = getUsers(selectSQL);
            for (User user : allUsers) {
                if (user.firstName != null && user.lastName != null) {
                    if (user.firstName.contains(name) || user.lastName.contains(name)) {
                        users.add(user);
                    }
                }
            }
        }

        return users;
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        if (validate()) {
            String selectSQL = "select * from users";
            users = getUsers(selectSQL);
        }

        return users;
    }

    public static User getUserById(Integer id) {
        User user = null;
        if (validate()) {
            String selectSQL = "select * from users u where u.id = '" + id + "'";
            user = getUser(user, selectSQL);
        }

        return user;
    }

    public static User getUserByEmail(String email) {
        User user = null;
        if (validate()) {
            String selectSQL = "select * from users u where u.email = '" + email + "'";
            user = getUser(user, selectSQL);
        }

        return user;
    }

    private static User getUser(User user, String selectSQL) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery(selectSQL);
            if (rs.next()) {
                user = new User();
                user.id = rs.getInt("id");
                user.firstName = rs.getString("firstName");
                user.lastName = rs.getString("lastName");
                user.email = rs.getString("email");
                user.major = rs.getString("major");
                user.hashedPassword = rs.getString("hashedPassword");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private static List<User> getUsers(String selectSQL) {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery(selectSQL);
            while (rs.next()) {
                User user = new User();
                user.id = rs.getInt("id");
                user.firstName = rs.getString("firstName");
                user.lastName = rs.getString("lastName");
                user.email = rs.getString("email");
                user.hashedPassword = rs.getString("hashedPassword");
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dbConnection;
    }

}
