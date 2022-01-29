package ar.edu.itba.paw.webapp.exceptionMappers;

import ar.edu.itba.paw.webapp.dto.output.ErrorValidationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * https://www.baeldung.com/javax-validation
 * https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/bean-validation.html
 * https://stackoverflow.com/questions/43423036/custom-validationerror-class-in-jersey-to-send-only-string-message-of-error
 */
@Singleton
@Component
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConstraintViolationExceptionMapper.class);

    @Override
    public Response toResponse(ConstraintViolationException e) {
        List<ErrorValidationDto> errors = new ArrayList<>();

        e.getConstraintViolations().forEach(violation -> errors.add(ErrorValidationDto.fromValidationError(getViolationPropertyName(violation), violation.getMessage())));

        LOGGER.error("{}: {}",
                e.getClass().getName(),
                e.getConstraintViolations());

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new GenericEntity<Collection<ErrorValidationDto>>(errors) {})
                .build();
    }

    private String getViolationPropertyName(ConstraintViolation<?> violation) {
        final String propertyPath = violation.getPropertyPath().toString();
        return propertyPath.substring(propertyPath.lastIndexOf(".") + 1);
    }
}