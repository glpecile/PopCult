package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.EmailNotExistsException;
import ar.edu.itba.paw.interfaces.exceptions.ModRequestAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UserAlreadyIsModException;
import ar.edu.itba.paw.webapp.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.Locale;

@ControllerAdvice
public class ExceptionHandlingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({
            DataIntegrityViolationException.class,
            ListNotFoundException.class,
            MediaNotFoundException.class,
            StaffNotFoundException.class,
            StudioNotFoundException.class,
            UserNotFoundException.class,
            ImageNotFoundException.class,
            CommentNotFoundException.class,
            IllegalArgumentException.class})// TODO Cambiar por una excepcion un poco mas especifica
    public ModelAndView notFoundException() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
        mav.addObject("description", messageSource.getMessage("exception.notFound", null, Locale.getDefault()));
        return mav;
    }

    @ExceptionHandler({TokenNotFoundException.class})
    public ModelAndView tokenNotFoundException() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
        mav.addObject("description", messageSource.getMessage("exception.tokenNotFound", null, Locale.getDefault()));
        return mav;
    }

    @ExceptionHandler({NoUserLoggedException.class})
    public ModelAndView noUserLoggedException() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
        mav.addObject("description", messageSource.getMessage("exception.noUserLogged", null, Locale.getDefault()));
        return mav;
    }

    @ExceptionHandler({UnregisteredUserException.class, BadCredentialsException.class})
    public ModelAndView unregisteredUserException() {
        LOGGER.info("Handling UnregisteredUserException");
        return new ModelAndView("/login").addObject("error", messageSource.getMessage("login.incorrect", null, Locale.getDefault()));
    }

    @ExceptionHandler({DisabledException.class})
    public ModelAndView disabledUserException() {
        LOGGER.info("Handling DisabledUserException");
        return new ModelAndView("/login").addObject("error", messageSource.getMessage("login.disabled", null, Locale.getDefault()));
    }

    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public ModelAndView internalAuthenticationServiceException() {
        LOGGER.info("Handling InternalAuthenticationServiceException");
        return new ModelAndView("/login").addObject("error", messageSource.getMessage("login.internalError", null, Locale.getDefault()));
    }

    @ExceptionHandler({UserAlreadyIsModException.class})
    public ModelAndView UserAlreadyIsMod() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
        mav.addObject("description", messageSource.getMessage("exception.userAlreadyIsMod", null, Locale.getDefault()));
        return mav;
    }

    @ExceptionHandler({ModRequestAlreadyExistsException.class})
    public ModelAndView ModRequestAlreadyExists() {
        LOGGER.info("Handling ModRequestAlreadyExistsException");
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
        mav.addObject("description", messageSource.getMessage("exception.modRequestAlreadyExists", null, Locale.getDefault()));
        return mav;
    }

    @ExceptionHandler({ParseException.class})
    public ModelAndView internalException() {
        LOGGER.info("Handling ParseException");
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
        mav.addObject("description", messageSource.getMessage("exception.internalException", null, Locale.getDefault()));
        return mav;
    }


}
