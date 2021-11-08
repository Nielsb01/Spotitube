package nl.han.ica.oose.dea.niels.resourcelayer.dtos;

public class LoginResponseDTO {
    private String user;
    private String token;

    public LoginResponseDTO(String username, String token) {
        this.user = username;
        this.token = token;
    }

    public LoginResponseDTO() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
