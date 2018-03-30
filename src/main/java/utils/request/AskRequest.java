package utils.request;

public class AskRequest {

    public String question;
    public String description;
    public Integer userId;
    public String token;

    @Override
    public String toString() {
        return "AskRequest{" +
                "question='" + question + '\'' +
                ", description='" + description + '\'' +
                ", id=" + userId +
                ", token='" + token + '\'' +
                '}';
    }
}
