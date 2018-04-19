package jdbc;

import model.*;
import utils.Utils;
import utils.request.AddCurrentCourseRequest;
import utils.request.AddSyllabusRequest;
import utils.response.CompletedCourseResponse;
import utils.response.CourseResponse;
import utils.response.CurrentCourseResponse;
import utils.response.SyllabusResponse;

import javax.rmi.CORBA.Util;
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

    public static void addCurrentCourseExpectedGrade(Integer courseId, String grade) {
        String query = "insert into current_courses(expected_grade) values (?) where id = " + courseId + ";";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, grade);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static CurrentCourseResponse addCurrentCourse(Integer courseId, Integer userId, Integer credit) {
        CurrentCourseResponse res = new CurrentCourseResponse();
        AllCourses course = getCourseById(courseId);
        String query = "insert into current_courses(user_id, course_id, credit_) values (?, ?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.setInt(3, credit);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        CurrentCourse currentCourse = getLastCurrentCourseById();

        res.expectedGrade = Utils.calculateExpectedGpa(userId);
        res.abbr = course.abbr;
        res.credit = currentCourse.credit;
        res.id = currentCourse.id;
        res.name = course.name;

        return res;
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

    public static List<CurrentCourseResponse> getCurrentCoursesByUserId(Integer userId) {
        List<CurrentCourseResponse> res = new ArrayList<>();
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
                    course.credit = rs.getInt("credit_");
                    course.expectedGrade = rs.getString("expected_grade");
                    currentCourses.add(course);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

        for (CurrentCourse currentCourse : currentCourses) {
            AllCourses courseFromAll = getCourseById(currentCourse.courseId);
            CurrentCourseResponse currentCourseResponse = new CurrentCourseResponse();
            currentCourseResponse.id = currentCourse.id;
            currentCourseResponse.name = courseFromAll.name;
            currentCourseResponse.abbr = courseFromAll.abbr;
            currentCourseResponse.expectedGrade = Utils.calculateExpectedGpa(userId);
            currentCourseResponse.credit = currentCourse.credit;

            res.add(currentCourseResponse);
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

    public static List<SyllabusResponse> getSyllabus(Integer currentCourseId) {
        List<SyllabusResponse> res = new ArrayList<>();

        List<Syllabus> syllabi = new ArrayList<>();

        if (validate()) {
            String query = "select * from syllabus s where s.current_course_id = " + currentCourseId + ";";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = connection.prepareStatement(query);
                ResultSet rs = preparedStatement.executeQuery(query);
                while (rs.next()) {
                    Syllabus syllabus = new Syllabus();
                    syllabus.id = rs.getInt("id");
                    syllabus.contribution = rs.getInt("contribution");
                    syllabus.title = rs.getString("title");
                    syllabus.weight = rs.getInt("weight");
                    syllabus.grade = rs.getInt("grade");
                    syllabi.add(syllabus);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

        for (Syllabus syllabus : syllabi) {
            SyllabusResponse syllabusResponse = new SyllabusResponse();
            syllabusResponse.title = syllabus.title;
            syllabusResponse.weight = syllabus.weight;
            syllabusResponse.grade = syllabus.grade;
            syllabusResponse.contribution = syllabus.contribution;

            res.add(syllabusResponse);
        }

        return res;
    }

    public static SyllabusResponse addSyllabus(AddSyllabusRequest request) {
        SyllabusResponse syllabusResponse = new SyllabusResponse();
//        CurrentCourse course = getCurrentCourseById(request.courseId);
        String query = "insert into syllabus(current_course_id, title, weight, grade, contribution) " +
                "values (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, request.courseId);
            preparedStatement.setString(2, request.title);
            preparedStatement.setInt(3, request.weight);
            preparedStatement.setInt(4, request.grade);
            preparedStatement.setInt(5, request.contribution);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        syllabusResponse.title = request.title;
        syllabusResponse.weight = request.weight;
        syllabusResponse.grade = request.grade;
        syllabusResponse.contribution = request.contribution;

        return syllabusResponse;

    }

    private static CurrentCourse getLastCurrentCourseById() {
        CurrentCourse course = new CurrentCourse();
        if (validate()) {
            String selectSQL = "select * from current_courses c order by c.id desc;";
            getCurrentCourse(course, selectSQL);
        }

        return course;
    }

    private static CurrentCourse getCurrentCourseById(Integer courseId) {
        CurrentCourse course = new CurrentCourse();
        if (validate()) {
            String selectSQL = "select * from current_courses c where c.id = '" + courseId + "'";
            getCurrentCourse(course, selectSQL);
        }

        return course;
    }

    private static void getCurrentCourse(CurrentCourse res, String query) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery(query);
            if (rs.next()) {
                res.id = rs.getInt("id");
                res.userId = rs.getInt("user_id");
                res.courseId = rs.getInt("course_id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
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
