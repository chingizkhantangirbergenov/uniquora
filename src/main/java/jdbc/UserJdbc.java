package jdbc;

import model.User;

import java.sql.*;

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

    public static boolean addUser(User user) {
        // ToDo check for whether the user already exists or not, if exists then return false
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

    public static User getUserByEmail(String email) {
        User user = null;
        if (validate()) {
            String selectSQL = "select * from users u where u.email = '" + email + "'";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery(selectSQL);
                if (rs.next()) {
                    user = new User();
                    user.id = rs.getInt("id");
                    user.firstName = rs.getString("firstName");
                    user.lastName = rs.getString("lastName");
                    user.email = rs.getString("email");
                    user.hashedPassword = rs.getString("hashedPassword");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
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
