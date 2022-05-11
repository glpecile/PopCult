package ar.edu.itba.paw.webapp.dto.validation.annotations;

import ar.edu.itba.paw.webapp.dto.validation.constraints.NotEmptyBodyValidatorConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = NotEmptyBodyValidatorConstraint.class)
@Target({ TYPE, FIELD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface NotEmptyBody {

    String message() default "{custom.validation.NotEmptyBody.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
