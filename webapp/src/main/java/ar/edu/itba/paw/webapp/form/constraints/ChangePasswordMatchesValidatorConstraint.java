package ar.edu.itba.paw.webapp.form.constraints;

import ar.edu.itba.paw.webapp.form.PasswordForm;
import ar.edu.itba.paw.webapp.form.annotations.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChangePasswordMatchesValidatorConstraint implements ConstraintValidator<PasswordMatches, PasswordForm> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(PasswordForm value, ConstraintValidatorContext context) {
        return value.getNewPassword().equals(value.getRepeatPassword());
    }
}
