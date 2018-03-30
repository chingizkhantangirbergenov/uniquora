package jaxrs;

import com.google.gson.Gson;
import jdbc.FakeData;
import jdbc.UserJdbc;
import model.User;
import utils.BCrypt;
import utils.JWTUtils;
import utils.RandomString;
import utils.request.RegisterRequest;
import utils.request.TwoParamRequest;
import utils.response.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;

@Path("/gpa")
public class GpaJaxService {

    public GpaJaxService() {
    }

    @POST
    @Produces("application/json")
    public Response getGpa(String body) {
        RestMainResponse mainResponse = new RestMainResponse();

        Gson gson = new Gson();
        TwoParamRequest twoParamRequest = gson.fromJson(body, TwoParamRequest.class);

        User user = UserJdbc.getUserById(twoParamRequest.userId);
        if (user != null) {
            if (JWTUtils.isValidToken(twoParamRequest.token, user.email)) {
                mainResponse.isSuccess = true;
                GpaResponse gpaResponse = new GpaResponse();
                // ToDo | here we have to get gpa of current user from REGISTRAR API, but for now I've used just a fake gpa
                gpaResponse.gpa = FakeData.getGpa();
                mainResponse.body = gpaResponse;
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