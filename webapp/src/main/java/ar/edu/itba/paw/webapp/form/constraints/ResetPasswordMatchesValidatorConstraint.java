package ar.edu.itba.paw.webapp.form.constraints;

import ar.edu.itba.paw.webapp.form.ResetPasswordForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import ar.edu.itba.paw.webapp.form.annotations.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ResetPasswordMatchesValidatorConstraint implements ConstraintValidator<PasswordMatches, ResetPasswordForm> {

    @Override
    public void initialize(PasswordMatches passwordMatches) {

    }

    @Override
    public boolean isValid(ResetPasswordForm value, ConstraintValidatorContext constraintValidatorContext) {
        return value.getNewPassword().equals(value.getRepeatPassword());
    }
}

