package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.PasswordMatches;

import javax.validation.constraints.Size;

@PasswordMatches()
public class PasswordForm {
    @Size(min = 8, max = 100)
    private String currentPassword;

    @Size(min = 8, max = 100)
    private String newPassword;

    @Size(min = 8, max = 100)
    private String repeatPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

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

}
