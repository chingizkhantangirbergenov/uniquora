package jaxrs;

import com.google.gson.Gson;
import jdbc.UserJdbc;
import model.User;
import utils.BCrypt;
import utils.JWTUtils;
import utils.request.LoginRequest;
import utils.response.RestErrorMessage;
import utils.response.RestLoginResponse;
import utils.response.RestMainResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginJaxService {

    public LoginJaxService() {
        super();
    }

    @POST
    @Produces("application/json")
    public Response getUser(String body) {
        RestMainResponse mainResponse = new RestMainResponse();

        Gson gson = new Gson();
        LoginRequest loginRequest = gson.fromJson(body, LoginRequest.class);

        User user = UserJdbc.getUserByEmail(loginRequest.email);
        if (user != null) {
            if (BCrypt.checkpw(loginRequest.password, user.hashedPassword)) {
                RestLoginResponse loginResponse = new RestLoginResponse();
                loginResponse.id = user.id;
                loginResponse.firstName = user.firstName;
                loginResponse.lastname = user.lastName;
                loginResponse.email = user.email;
                mainResponse.isSuccess = true;
                mainResponse.body = loginResponse;
                mainResponse.token = JWTUtils.generateToken(user.email);
            } else {
                // ToDo for this case return error code not ok
                RestErrorMessage errorMessage = new RestErrorMessage();
                errorMessage.message = "incorrect password";
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