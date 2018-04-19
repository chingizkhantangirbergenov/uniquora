package jdbc;

import model.AllCourses;
import model.AllUsers;
import model.CompletedCourse;
import model.CurrentCourse;
import utils.request.AddCurrentCourseRequest;
import utils.response.CompletedCourseResponse;
import utils.response.CourseResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursesJdbc {

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/uniquora";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection connection = null;

    public static boolean validate() {
        connection = getDBConnection();
        return connection != null;
    }

    public static AllCourses addCurrentCourse(Integer courseId, Integer userId) {
        AllCourses course = getCourseById(courseId);
        String query = "insert into current_courses(user_id, course_id) values (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return course;
    }

    public static CompletedCourseResponse addCompletedCourse(Integer courseId,
                                                             Integer userId,
                                                             Integer credit,
                                                             String grade) {
        CompletedCourseResponse completedCourseResponse = new CompletedCourseResponse();
        AllCourses course = getCourseById(courseId);
        String query = "insert into completed_courses(user_id, course_id, credit_, grade_) values (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.setInt(3, credit);
            preparedStatement.setString(4, grade);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        completedCourseResponse.credit = credit;
        completedCourseResponse.grade = grade;
        completedCourseResponse.abbr = course.abbr;
        completedCourseResponse.name = course.name;
        completedCourseResponse.id = course.id;

        return completedCourseResponse;
    }

    public static List<AllCourses> getAllCourses() {
        List<AllCourses> res = new ArrayList<>();

        if (validate()) {
            String query = "select * from all_courses;";
            getCourses(res, query);
        }

        return res;
    }

    public static List<AllCourses> getCurrentCoursesByUserId(Integer userId) {
        List<AllCourses> res = new ArrayList<>();
        List<CurrentCourse> currentCourses = new ArrayList<>();

        if (validate()) {
            String query = "select * from current_courses c where c.user_id = " + userId + ";";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement(query);
                ResultSet rs = preparedStatement.executeQuery(query);
                while (rs.next()) {
                    CurrentCourse course = new CurrentCourse();
                    course.id = rs.getInt("id");
                    course.userId = rs.getInt("user_id");
                    course.courseId = rs.getInt("course_id");
                    currentCourses.add(course);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

        for (CurrentCourse currentCourse : currentCourses) {
            res.add(getCourseById(currentCourse.courseId));
        }

        return res;
    }

    public static List<CompletedCourseResponse> getCompletedCoursesByUserId(Integer userId) {
        List<CompletedCourseResponse> res = new ArrayList<>();

        List<CompletedCourse> completedCourses = new ArrayList<>();

        if (validate()) {
            String query = "select * from completed_courses c where c.user_id = " + userId + ";";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement(query);
                ResultSet rs = preparedStatement.executeQuery(query);
                while (rs.next()) {
                    CompletedCourse course = new CompletedCourse();
                    course.id = rs.getInt("id");
                    course.userId = rs.getInt("user_id");
                    course.courseId = rs.getInt("course_id");
                    course.credit = rs.getInt("credit_");
                    course.grade = rs.getString("grade_");
                    completedCourses.add(course);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

        for (CompletedCourse completedCourse : completedCourses) {
            AllCourses courseFromAll = getCourseById(completedCourse.courseId);
            CompletedCourseResponse completedCourseResponse = new CompletedCourseResponse();
            completedCourseResponse.id = courseFromAll.id;
            completedCourseResponse.name = courseFromAll.name;
            completedCourseResponse.abbr = courseFromAll.abbr;
            completedCourseResponse.grade = completedCourse.grade;
            completedCourseResponse.credit = completedCourse.credit;

            res.add(completedCourseResponse);
        }

        return res;
    }

    public static AllCourses getCourseById(Integer courseId) {
        AllCourses course = new AllCourses();
        if (validate()) {
            String selectSQL = "select * from all_courses c where c.id = '" + courseId + "'";
            getCourse(course, selectSQL);
        }

        return course;

    }

    private static void getCourse(AllCourses res, String query) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery(query);
            if (rs.next()) {
                res.id = rs.getInt("id");
                res.abbr = rs.getString("abbr_");
                res.name = rs.getString("name_");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void getCourses(List<AllCourses> res, String query) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery(query);
            while (rs.next()) {
                AllCourses course = new AllCourses();
                course.id = rs.getInt("id");
                course.abbr = rs.getString("abbr_");
                course.name = rs.getString("name_");
                res.add(course);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
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
