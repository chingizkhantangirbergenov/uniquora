package jaxrs;

import com.google.gson.Gson;
import jdbc.CoursesJdbc;
import jdbc.UserJdbc;
import model.Syllabus;
import model.User;
import utils.BCrypt;
import utils.JWTUtils;
import utils.request.AddCompletedCourseRequest;
import utils.request.AddSyllabusRequest;
import utils.request.LoginRequest;
import utils.request.SyllabusRequest;
import utils.response.RestLoginResponse;
import utils.response.RestMainResponse;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/syllabus")
public class SyllabusJaxService {

    public SyllabusJaxService() {}

    @POST
    @Produces("application/json")
    public Response getSyllabus(String body) {
        RestMainResponse mainResponse = new RestMainResponse();

        Gson gson = new Gson();
        SyllabusRequest syllabusRequest = gson.fromJson(body, SyllabusRequest.class);

        User user = UserJdbc.getUserById(syllabusRequest.userId);
        if (user != null) {
            if (JWTUtils.isValidToken(syllabusRequest.token, user.email)) {
                mainResponse.isSuccess = true;
                mainResponse.body = CoursesJdbc.getSyllabus(syllabusRequest.courseId);
            } else {
                return Response.notAcceptable(null).build();
            }
        } else {
            return Response.notAcceptable(null).build();
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).build();

    }

    @POST
    @Path("/add")
    @Produces("application/json")
    public Response addCompletedCourses(String body) {
        RestMainResponse mainResponse = new RestMainResponse();

        Gson gson = new Gson();
        AddSyllabusRequest request = gson.fromJson(body, AddSyllabusRequest.class);

        User user = UserJdbc.getUserById(request.userId);
        if (user != null) {
            if (JWTUtils.isValidToken(request.token, user.email)) {
                mainResponse.isSuccess = true;
                mainResponse.body = CoursesJdbc.addSyllabus(request);
            } else {
                return Response.notAcceptable(null).build();
            }
        } else {
            return Response.notAcceptable(null).build();
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).build();
    }


}
