package ar.edu.itba.paw.webapp.dto.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserAuthDto {

    @NotNull
    @Size(min = 1, max = 100)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String username;

    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
