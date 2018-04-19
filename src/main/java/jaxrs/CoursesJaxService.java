package jaxrs;

import com.google.gson.Gson;
import jdbc.CoursesJdbc;
import jdbc.UserJdbc;
import model.User;
import utils.JWTUtils;
import utils.request.AddCompletedCourseRequest;
import utils.request.AddCurrentCourseRequest;
import utils.request.TwoParamRequest;
import utils.response.RestMainResponse;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/courses")
public class CoursesJaxService {

    public CoursesJaxService() {
    }

    @POST
    @Produces("application/json")
    public Response getCourses(String body) {
        RestMainResponse mainResponse = new RestMainResponse();

        Gson gson = new Gson();
        TwoParamRequest twoParamRequest = gson.fromJson(body, TwoParamRequest.class);

        User user = UserJdbc.getUserById(twoParamRequest.userId);
        if (user != null) {
            if (JWTUtils.isValidToken(twoParamRequest.token, user.email)) {
                mainResponse.isSuccess = true;
                mainResponse.body = CoursesJdbc.getAllCourses();
            } else {
                return Response.notAcceptable(null).build();
            }
        } else {
            return Response.notAcceptable(null).build();
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/current")
    @Produces("application/json")
    public Response getCurrentCourses(String body) {
        RestMainResponse mainResponse = new RestMainResponse();

        Gson gson = new Gson();
        TwoParamRequest twoParamRequest = gson.fromJson(body, TwoParamRequest.class);

        User user = UserJdbc.getUserById(twoParamRequest.userId);
        if (user != null) {
            if (JWTUtils.isValidToken(twoParamRequest.token, user.email)) {
                mainResponse.isSuccess = true;
                mainResponse.body = CoursesJdbc.getCurrentCoursesByUserId(user.id);
            } else {
                return Response.notAcceptable(null).build();
            }
        } else {
            return Response.notAcceptable(null).build();
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/current/add")
    @Produces("application/json")
    public Response addCurrentCourses(String body) {
        RestMainResponse mainResponse = new RestMainResponse();

        Gson gson = new Gson();
        AddCurrentCourseRequest request = gson.fromJson(body, AddCurrentCourseRequest.class);

        User user = UserJdbc.getUserById(request.userId);
        if (user != null) {
            if (JWTUtils.isValidToken(request.token, user.email)) {
                mainResponse.isSuccess = true;
                mainResponse.body = CoursesJdbc.addCurrentCourse(request.id, request.userId);
            } else {
                return Response.notAcceptable(null).build();
            }
        } else {
            return Response.notAcceptable(null).build();
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/completed")
    @Produces("application/json")
    public Response getCompletedCourses(String body) {
        RestMainResponse mainResponse = new RestMainResponse();

        Gson gson = new Gson();
        TwoParamRequest twoParamRequest = gson.fromJson(body, TwoParamRequest.class);

        User user = UserJdbc.getUserById(twoParamRequest.userId);
        if (user != null) {
            if (JWTUtils.isValidToken(twoParamRequest.token, user.email)) {
                mainResponse.isSuccess = true;
                mainResponse.body = CoursesJdbc.getCompletedCoursesByUserId(user.id);
            } else {
                return Response.notAcceptable(null).build();
            }
        } else {
            return Response.notAcceptable(null).build();
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/completed/add")
    @Produces("application/json")
    public Response addCompletedCourses(String body) {
        RestMainResponse mainResponse = new RestMainResponse();

        Gson gson = new Gson();
        AddCompletedCourseRequest request = gson.fromJson(body, AddCompletedCourseRequest.class);

        User user = UserJdbc.getUserById(request.userId);
        if (user != null) {
            if (JWTUtils.isValidToken(request.token, user.email)) {
                mainResponse.isSuccess = true;
                mainResponse.body = CoursesJdbc.addCompletedCourse(
                        request.id,
                        request.userId,
                        request.credit,
                        request.grade);
            } else {
                return Response.notAcceptable(null).build();
            }
        } else {
            return Response.notAcceptable(null).build();
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).build();
    }

}
