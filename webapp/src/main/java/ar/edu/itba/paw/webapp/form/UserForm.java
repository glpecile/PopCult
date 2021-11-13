package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.PasswordMatches;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@PasswordMatches()
public class UserForm implements PasswordMatchesForm {
    @Email()
    @Size(min = 6, max = 100)
    private String email;

    @Size(min = 1, max = 100)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String username;

    @Size(min = 8, max = 100)
    private String password;

    @Size(min = 8, max = 100)
    private String repeatPassword;

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

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
