package jaxrs;

import com.google.gson.Gson;
import jdbc.FeedbackJdbc;
import jdbc.UserJdbc;
import model.Feedback;
import model.User;
import utils.JWTUtils;
import utils.request.FeedbackRequest;
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

@Path("/feedback")
public class FeedbackJaxService {

    public FeedbackJaxService() {
    }

    @POST
    @Path("/edit")
    @Produces("application/json")
    public Response getCourses(String body) {
        RestMainResponse mainResponse = new RestMainResponse();

        Gson gson = new Gson();
        FeedbackRequest feedbackRequest = gson.fromJson(body, FeedbackRequest.class);

        User user = UserJdbc.getUserById(feedbackRequest.userId);
        if (user != null) {
            if (JWTUtils.isValidToken(feedbackRequest.token, user.email)) {
                mainResponse.isSuccess = true;

                if (feedbackRequest.id != null) {
                    // ToDo here you have to write edit
                } else {
                    Feedback feedback = new Feedback();
                    feedback.courseId = feedbackRequest.courseId;
                    feedback.feedback = feedbackRequest.feedback;
                    feedback.userId = feedbackRequest.userId;
                    FeedbackJdbc.addFeedback(feedback);
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
