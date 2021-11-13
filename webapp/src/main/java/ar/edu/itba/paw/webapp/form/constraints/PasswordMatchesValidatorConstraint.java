package ar.edu.itba.paw.webapp.form.constraints;

import ar.edu.itba.paw.webapp.form.PasswordMatchesForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import ar.edu.itba.paw.webapp.form.annotations.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidatorConstraint implements ConstraintValidator<PasswordMatches, PasswordMatchesForm> {

    @Override
    public void initialize(PasswordMatches passwordMatches) {

    }

    @Override
    public boolean isValid(PasswordMatchesForm value, ConstraintValidatorContext constraintValidatorContext) {
        return value.getPassword().equals(value.getRepeatPassword());
    }
}
