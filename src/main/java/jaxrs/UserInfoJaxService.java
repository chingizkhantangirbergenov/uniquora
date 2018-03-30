package jaxrs;

import com.google.gson.Gson;
import jdbc.FakeData;
import jdbc.UserJdbc;
import model.User;
import utils.JWTUtils;
import utils.request.UserInfoRequest;
import utils.response.RestErrorMessage;
import utils.response.RestMainResponse;
import utils.response.UserInfoResponse;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserInfoJaxService {

    public UserInfoJaxService() {
    }

    @POST
    @Produces("application/json")
    public Response getCourses(String body) {
        RestMainResponse mainResponse = new RestMainResponse();

        Gson gson = new Gson();
        UserInfoRequest userInfoRequest = gson.fromJson(body, UserInfoRequest.class);

        User user = UserJdbc.getUserById(userInfoRequest.userId);
        if (user != null) {
            if (JWTUtils.isValidToken(userInfoRequest.token, user.email)) {
                User userInfo = UserJdbc.getUserById(userInfoRequest.id);
                if (userInfo != null) {
                    mainResponse.isSuccess = true;
                    UserInfoResponse userInfoResponse = new UserInfoResponse();
                    userInfoResponse.firstName = userInfo.firstName;
                    userInfoResponse.lastName = userInfo.lastName;
                    // ToDo here you have to get current courses of the user from REGISTRAR, for now just a fake
                    userInfoResponse.currentCourses = FakeData.getCurrentCourses();
                    mainResponse.body = userInfoResponse;
                } else {
                    // ToDo for this case return error code not ok
                    RestErrorMessage errorMessage = new RestErrorMessage();
                    errorMessage.message = "no such user";
                    mainResponse.isSuccess = false;
                    mainResponse.body = errorMessage;
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
