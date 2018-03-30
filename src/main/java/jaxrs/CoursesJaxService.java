package jaxrs;

import com.google.gson.Gson;
import jdbc.FakeData;
import jdbc.UserJdbc;
import model.User;
import utils.JWTUtils;
import utils.request.ThreeParamRequest;
import utils.response.CourseResponse;
import utils.response.RestErrorMessage;
import utils.response.RestMainResponse;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/courses")
public class CoursesJaxService {

    public CoursesJaxService() {
    }

    @POST
    @Produces("application/json")
    public Response getCourses(String body) {
        RestMainResponse mainResponse = new RestMainResponse();

        Gson gson = new Gson();
        ThreeParamRequest threeParamRequest = gson.fromJson(body, ThreeParamRequest.class);

        User user = UserJdbc.getUserById(threeParamRequest.userId);
        if (user != null) {
            if (JWTUtils.isValidToken(threeParamRequest.token, user.email)) {
                mainResponse.isSuccess = true;
                // ToDo | here we have to get courses from REGISTRAR API, but for now I've used just a fake gpa
                // the fake courses are as follows
                List<CourseResponse> coursesResponse = FakeData.getCourses();
                if (threeParamRequest.name != null) {
                    mainResponse.body = new ArrayList<CourseResponse>();
                    for (CourseResponse courseResponse : coursesResponse) {
                        if (courseResponse.name.contains(threeParamRequest.name)) {
                            ((ArrayList) mainResponse.body).add(courseResponse);
                        }
                    }
                } else {
                    mainResponse.body = coursesResponse;
                }
            } else {
                // ToDo for this case return error code not ok
                RestErrorMessage errorMessage = new RestErrorMessage();
                errorMessage.message = "not valid token";
                mainResponse.isSuccess = false;
                mainResponse.body = errorMessage;
            }
        } else {
            // ToDo for this case return error code not ok
            RestErrorMessage errorMessage = new RestErrorMessage();
            errorMessage.message = "no such user";
            mainResponse.isSuccess = false;
            mainResponse.body = errorMessage;
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).build();
    }

}
