package ar.edu.itba.paw.webapp.exceptionMappers;

import ar.edu.itba.paw.webapp.dto.output.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Singleton
@Component
@Provider
public class NotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotAllowedExceptionMapper.class);

    @Override
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response toResponse(NotAllowedException exception) {
        LOGGER.error("{}: {}", exception.getClass().getName(), exception.getMessage());

        return Response
                .status(Response.Status.METHOD_NOT_ALLOWED)
                .entity(ErrorDto.fromErrorMsg(exception.getMessage()))
                .build();
    }
}
