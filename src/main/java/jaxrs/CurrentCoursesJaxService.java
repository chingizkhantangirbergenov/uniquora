package jaxrs;

import com.google.gson.Gson;
import jdbc.FakeData;
import jdbc.UserJdbc;
import model.User;
import utils.JWTUtils;
import utils.request.TwoParamRequest;
import utils.response.CompletedCourseResponse;
import utils.response.CurrentCourseResponse;
import utils.response.RestErrorMessage;
import utils.response.RestMainResponse;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.security.KeyException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Path("/current_courses")
public class CurrentCoursesJaxService {

    public CurrentCoursesJaxService() {

    }

//    @POST
//    @Produces("application/json")
//    public Response getCurrentCourses(String body) {
//        RestMainResponse mainResponse = new RestMainResponse();
//
//        Gson gson = new Gson();
//        TwoParamRequest twoParamRequest = gson.fromJson(body, TwoParamRequest.class);
//
//        User user = UserJdbc.getUserById(twoParamRequest.userId);
//        if (user != null) {
//            if (JWTUtils.isValidToken(twoParamRequest.token, user.email)) {
//                mainResponse.isSuccess = true;
//                // ToDo | here we have to get current courses of current user from REGISTRAR API, but for now I've used just a fake gpa
//                // the fake current courses are as follows
//                mainResponse.body = FakeData.getCurrentCourses();
//            } else {
//                // ToDo for this case return error code not ok
//                RestErrorMessage errorMessage = new RestErrorMessage();
//                errorMessage.message = "not valid token";
//                mainResponse.isSuccess = false;
//                mainResponse.body = errorMessage;
//            }
//        } else {
//            // ToDo for this case return error code not ok
//            RestErrorMessage errorMessage = new RestErrorMessage();
//            errorMessage.message = "no such user";
//            mainResponse.isSuccess = false;
//            mainResponse.body = errorMessage;
//        }
//
//        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).build();
//    }

//    @POST
//    @Path("/add")
//    @Produces("application/json")
//    public Response addCurrentCourses(String body) {
//        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).build();
//    }

}
