package utils;

import jdbc.CoursesJdbc;
import jdbc.UserJdbc;
import model.CompletedCourse;
import model.User;
import org.omg.CORBA.Current;
import utils.response.CompletedCourseResponse;
import utils.response.CurrentCourseResponse;

import javax.validation.constraints.Null;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Utils {

    public static String calculateGpa(Integer userId) {
        // ToDo calculated gpa from courses
        double sum1 = 0;
        double sum2 = 0;
        double cGpa;


        List<CompletedCourseResponse> courseResponses =
                CoursesJdbc.getCompletedCoursesByUserId(userId);
        double[] courseGpa = new double[courseResponses.size()];
        double[] credit = new double[courseResponses.size()];
        int i = 0;
        for (CompletedCourseResponse completedCourse: courseResponses) {
            if(completedCourse.grade.equals("A")){
                courseGpa[i] = 4;
            }
            else if(completedCourse.grade.equals("A-")){
                courseGpa[i] = 3.67;
            }
            else if(completedCourse.grade.equals("B+")){
                courseGpa[i] = 3.33;
            }
            else if(completedCourse.grade.equals("B")){
                courseGpa[i] = 3;
            }
            else if(completedCourse.grade.equals("B-")){
                courseGpa[i] = 2.67;
            }
            else if(completedCourse.grade.equals("C+")){
                courseGpa[i] = 2.33;
            }
            else if(completedCourse.grade.equals("C")){
                courseGpa[i] = 2;
            }
            else if(completedCourse.grade.equals("C-")){
                courseGpa[i] = 1.67;
            }
            else if(completedCourse.grade.equals("D+")){
                courseGpa[i] = 1.33;
            }
            else if(completedCourse.grade.equals("D")){
                courseGpa[i] = 1;
            }
            else if(completedCourse.grade.equals("F")){
                courseGpa[i] = 0;
            }
            credit[i] = completedCourse.credit;
            i++;
        }
        for(i = 0; i < courseResponses.size(); i++){
            sum1 += credit[i]*courseGpa[i];
            sum2 += credit[i];
        }
        cGpa = sum1/sum2;
        String stringGpa = String.valueOf(cGpa);
        return stringGpa;
    }

    public static String calculateExpectedGpa(Integer userId) {
        // ToDo calculated gpa from courses
        double completeGpa = parseDouble(calculateGpa(userId));
        double sum1 = 0;
        double sum2 = 0;
        double cGpa;



        List<CurrentCourseResponse> currentCourseResponses =
                CoursesJdbc.getCurrentCoursesByUserId(userId);
        List<CompletedCourseResponse> completedCourseResponses =
                CoursesJdbc.getCompletedCoursesByUserId(userId);
        double[] courseGpa = new double[currentCourseResponses.size()];
        double[] credit = new double[currentCourseResponses.size()];
        int i = 0;
        for (CurrentCourseResponse currentCourseResponse: currentCourseResponses) {
           if(currentCourseResponse.expectedGrade != null){
               if(currentCourseResponse.expectedGrade.equals("A")){
                   courseGpa[i] = 4;
               }
               if(currentCourseResponse.expectedGrade.equals("A-")){
                   courseGpa[i] = 3.67;
               }
               if(currentCourseResponse.expectedGrade.equals("B+")){
                   courseGpa[i] = 3.33;
               }
               if(currentCourseResponse.expectedGrade.equals("B")){
                   courseGpa[i] = 3;
               }
               if(currentCourseResponse.expectedGrade.equals("B-")){
                   courseGpa[i] = 2.67;
               }
               if(currentCourseResponse.expectedGrade.equals("C+")){
                   courseGpa[i] = 2.33;
               }
               if(currentCourseResponse.expectedGrade.equals("C")){
                   courseGpa[i] = 2;
               }
               if(currentCourseResponse.expectedGrade.equals("C-")){
                   courseGpa[i] = 1.67;
               }
               if(currentCourseResponse.expectedGrade.equals("D+")){
                   courseGpa[i] = 1.33;
               }
               if(currentCourseResponse.expectedGrade.equals("D")){
                   courseGpa[i] = 1;
               }
               if(currentCourseResponse.expectedGrade.equals("F")){
                   courseGpa[i] = 0;
               }
               credit[i] = currentCourseResponse.credit;
               i++;

        }

        }


        for(i = 0; i < currentCourseResponses.size(); i++){
            sum1 += credit[i]*courseGpa[i];
            sum2 += credit[i];
        }
            cGpa = (completeGpa*completedCourseResponses.size() + sum1)/(completedCourseResponses.size() + sum2);
        String stringGpa = String.valueOf(cGpa);
        return stringGpa;
    }
}
