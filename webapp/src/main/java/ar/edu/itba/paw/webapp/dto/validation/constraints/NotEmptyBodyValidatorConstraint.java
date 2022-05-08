package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.validation.annotations.NotEmptyBody;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyBodyValidatorConstraint implements ConstraintValidator<NotEmptyBody, Object> {

    public void initialize(NotEmptyBody constraint) {
    }

    public boolean isValid(Object object, ConstraintValidatorContext context) {
        return object != null;
    }

}
