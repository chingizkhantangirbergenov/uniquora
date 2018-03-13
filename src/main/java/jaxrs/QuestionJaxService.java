package jaxrs;

import com.google.gson.Gson;
import utils.request.AskRequest;
import utils.response.RestMainResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/question")
public class QuestionJaxService {

    public QuestionJaxService() {
        super();
    }

    @POST
    @Path("/ask")
    @Produces("application/json")
    public Response addQuestion(String body) {
        RestMainResponse mainResponse = new RestMainResponse();

        Gson gson = new Gson();
        AskRequest askRequest = gson.fromJson(body, AskRequest.class);

        return Response.ok(gson.toJson(mainResponse), MediaType.APPLICATION_JSON).build();
    }
}