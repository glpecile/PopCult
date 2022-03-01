package ar.edu.itba.paw.webapp.exceptionMappers;

import ar.edu.itba.paw.webapp.dto.output.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Singleton
@Component
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebApplicationExceptionMapper.class);

    @Override
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response toResponse(WebApplicationException exception) {
        LOGGER.error("{}: {}", exception.getClass().getName(), exception.getMessage());

        return Response
                .status(exception.getResponse().getStatus())
                .entity(ErrorDto.fromErrorMsg(exception.getMessage()))
                .build();
    }
}
