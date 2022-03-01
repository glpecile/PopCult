package ar.edu.itba.paw.webapp.exceptionMappers;

import ar.edu.itba.paw.webapp.dto.output.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Singleton
@Component
@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationExceptionMapper.class);

    @Override
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response toResponse(AuthenticationException exception) {
        LOGGER.error("{}: {}", exception.getClass().getName(), exception.getMessage());

        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(ErrorDto.fromErrorMsg(exception.getMessage()))
                .build();
    }
}
