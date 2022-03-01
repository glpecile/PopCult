package ar.edu.itba.paw.webapp.dto.validation.annotations;

import ar.edu.itba.paw.webapp.dto.validation.constraints.ImageValidatorConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ImageValidatorConstraint.class)
@Target({ TYPE, FIELD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface Image {

    String message() default "Invalid image";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}