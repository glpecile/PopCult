package ar.edu.itba.paw.webapp.exceptionMappers;

import ar.edu.itba.paw.interfaces.exceptions.CustomException;
import ar.edu.itba.paw.webapp.dto.output.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Locale;

@Singleton
@Component
@Provider
public class CustomExceptionMapper implements ExceptionMapper<CustomException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionMapper.class);

    @Autowired
    private MessageSource messageSource;

    @Override
    public Response toResponse(CustomException exception) {
        LOGGER.error("{}: {} Status code: {}",
                exception.getClass().getName(),
                messageSource.getMessage(exception.getMessageCode(), null, Locale.ENGLISH),
                exception.getStatusCode());

        return Response
                .status(exception.getStatusCode())
                .entity(ErrorDto.fromErrorMsg(messageSource.getMessage(exception.getMessageCode(), null, LocaleContextHolder.getLocale())))
                .build();
    }
}
