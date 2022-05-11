package ar.edu.itba.paw.webapp.dto.input;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserCreateDto {

    @NotNull
    @Email()
    @Size(min = 6, max = 100)
    private String email;

    @NotNull
    @Size(min = 1, max = 100)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String username;

    @NotNull
    @Size(min = 8, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 100)
    @Pattern(regexp = "[a-zA-Z0-9\\s]+")
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
