package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.PasswordMatches;

import javax.validation.constraints.Size;

@PasswordMatches()
public class ResetPasswordForm implements PasswordMatchesForm {

    @Size(min = 8, max = 100)
    private String newPassword;

    @Size(min = 8, max = 100)
    private String repeatPassword;

    private String token;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getPassword() {
        return newPassword;
    }
}