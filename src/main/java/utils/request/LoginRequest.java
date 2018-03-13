package utils.request;

public class LoginRequest {

    public String email;
    public String password;

    @Override
    public String toString() {
        return "Login request: " + email + " " + password;
    }

}
