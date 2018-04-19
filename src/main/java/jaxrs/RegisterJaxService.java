package jaxrs;

import com.google.gson.Gson;
import jdbc.UserJdbc;
import model.User;
import utils.BCrypt;
import utils.RandomString;
import utils.request.RegisterRequest;
import utils.response.RestErrorMessage;
import utils.response.RestMainResponse;
import utils.response.RestRegisterResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;

@Path("/register")
public class RegisterJaxService {

    public RegisterJaxService() {
        super();
    }

    @POST
    @Produces("application/json")
    public Response addUser(String body) {
        RestMainResponse mainResponse = new RestMainResponse();

        Gson gson = new Gson();
        RegisterRequest registerRequest = gson.fromJson(body, RegisterRequest.class);

//        if (!UserJdbc.checkUserByEmail(registerRequest.email)) {
//            return Response.notAcceptable(null).build();
//        }

        User user = new User();
        user.firstName = registerRequest.firstName;
        user.lastName = registerRequest.lastName;
        user.email = registerRequest.email;
        String easy = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
        RandomString randomString = new RandomString(10, new SecureRandom(), easy);
        String password = randomString.nextString();
        user.hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());;

        if (UserJdbc.addUser(user)) {
            RestRegisterResponse registerResponse = new RestRegisterResponse();
            registerResponse.password = password;
            mainResponse.isSuccess = true;
            mainResponse.body = registerResponse;
        } else {
            return Response.notAcceptable(null).build();
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).build();
    }
}