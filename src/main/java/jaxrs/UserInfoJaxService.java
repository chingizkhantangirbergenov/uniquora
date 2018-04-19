package jaxrs;

import com.google.gson.Gson;
import jdbc.FakeData;
import jdbc.UserJdbc;
import model.AllUsers;
import model.User;
import utils.JWTUtils;
import utils.Utils;
import utils.request.UserInfoRequest;
import utils.response.RestErrorMessage;
import utils.response.RestMainResponse;
import utils.response.UserInfoResponse;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/userInfo")
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
                mainResponse.isSuccess = true;
                UserInfoResponse userInfoResponse = new UserInfoResponse();
                AllUsers userfromAll = UserJdbc.getUserFromAllUsersByEmail(user.email);
                System.out.println(userfromAll.idNumber);
                userInfoResponse.firstName = user.firstName;
                userInfoResponse.lastName = user.lastName;
                userInfoResponse.major = user.major;
                userInfoResponse.identityNumber = userfromAll.idNumber;
                userInfoResponse.school = userfromAll.school;
                userInfoResponse.gpa = Utils.calculateGpa(user.id);
                userInfoResponse.expectedGpa = Utils.calculateExpectedGpa(user.id);
                mainResponse.body = userInfoResponse;
            } else {
                return Response.notAcceptable(null).build();
            }
        } else {
            return Response.notAcceptable(null).build();
        }

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).build();
    }

}
