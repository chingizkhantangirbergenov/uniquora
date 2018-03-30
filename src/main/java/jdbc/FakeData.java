package jdbc;

import utils.response.CompletedCourseResponse;
import utils.response.CourseResponse;
import utils.response.CurrentCourseResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FakeData {

    public static List<CurrentCourseResponse> getCurrentCourses() {
        List<CurrentCourseResponse> currentCoursesResponse = new ArrayList<>();
        // ToDo | here we have to get completed courses of current user from REGISTRAR API, but for now I've used just a fake gpa
        // the fake completed courses are as follows
        for (int i = 0; i < 4; i++)  {
            CurrentCourseResponse currentCourseResponse = new CurrentCourseResponse();
            currentCourseResponse.name = "Kazakh Etiquette";
            currentCourseResponse.abbr = "KAZ 256";
            currentCoursesResponse.add(currentCourseResponse);
        }

        return currentCoursesResponse;
    }

    public static List<CompletedCourseResponse> getCompletedCourses() {
        List<CompletedCourseResponse> completedCoursesResponse = new ArrayList<>();
        // ToDo | here we have to get completed courses of current user from REGISTRAR API, but for now I've used just a fake gpa
        // the fake completed courses are as follows
        for (int i = 0; i < 6; i++)  {
            CompletedCourseResponse completedCourseResponse = new CompletedCourseResponse();
            completedCourseResponse.credit = 3;
            completedCourseResponse.grade = "C+";
            completedCourseResponse.name = "Business Etiquette";
            completedCourseResponse.abbr = "KAZ 255";
            completedCoursesResponse.add(completedCourseResponse);
        }

        return completedCoursesResponse;
    }

    public static List<CourseResponse> getCourses() {
        List<CourseResponse> coursesResponse = new ArrayList<>();
        for (int i = 0; i < 6; i++)  {
            CourseResponse courseResponse = new CourseResponse();
            courseResponse.name = "Calculus " + i;
            courseResponse.id = i;
            courseResponse.abbr = "Math16" + i;
            coursesResponse.add(courseResponse);
        }

        return coursesResponse;
    }

    public static Double getGpa() {
        return 3.75;
    }
}
