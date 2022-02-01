package ar.edu.itba.paw.webapp.dto.input;

import javax.validation.constraints.NotNull;

public class UserVerificationDto {

    @NotNull
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
