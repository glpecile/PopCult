package ar.edu.itba.paw.webapp.dto.input;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserEmailDto {

    @NotNull
    @Email
    @Size(min = 6, max = 100)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
