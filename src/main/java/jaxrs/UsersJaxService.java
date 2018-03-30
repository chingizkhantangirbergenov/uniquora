package jaxrs;

import com.google.gson.Gson;
import jdbc.UserJdbc;
import model.User;
import utils.JWTUtils;
import utils.request.ThreeParamRequest;
import utils.response.RestErrorMessage;
import utils.response.RestMainResponse;
import utils.response.UserResponse;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
public class UsersJaxService {

    public UsersJaxService() {
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
                List<UserResponse> usersResponse = new ArrayList<>();
                List<User> users;
                if (threeParamRequest.name != null) {
                    users = UserJdbc.getUsersByName(threeParamRequest.name);
                } else {
                    users = UserJdbc.getAllUsers();
                }

                for (User userItem : users) {
                    UserResponse userResponse = new UserResponse();
                    userResponse.firstName = userItem.firstName;
                    userResponse.lastName = userItem.lastName;
                    userResponse.id = userItem.id;
                    // ToDo you have to get major from DB, but for now we do not have majors in DB
                    userResponse.major = "CS";
                    usersResponse.add(userResponse);
                }

                mainResponse.body = usersResponse;
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
