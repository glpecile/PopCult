package ar.edu.itba.paw.webapp.form.constraints;

import ar.edu.itba.paw.webapp.form.UserForm;
import ar.edu.itba.paw.webapp.form.annotations.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidatorConstraint implements ConstraintValidator<PasswordMatches, UserForm> {

    @Override
    public void initialize(PasswordMatches passwordMatches) {

    }

    @Override
    public boolean isValid(UserForm value, ConstraintValidatorContext constraintValidatorContext) {
        return value.getPassword().equals(value.getRepeatPassword());
    }
}
