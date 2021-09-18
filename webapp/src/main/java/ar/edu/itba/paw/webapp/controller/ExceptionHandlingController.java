package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@ControllerAdvice
public class ExceptionHandlingController {
    @Autowired
    MessageSource messageSource;

    @ExceptionHandler({ListNotFoundException.class,
            MediaNotFoundException.class,
            StaffNotFoundException.class,
            StudioNotFoundException.class,
            UserNotFoundException.class})
    ModelAndView notFoundException() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
        mav.addObject("description", messageSource.getMessage("exception.notFound", null, Locale.getDefault()));
        return mav;
    }

    @ExceptionHandler({VerificationTokenNotFoundException.class})
    ModelAndView tokenNotFoundException() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
        mav.addObject("description", messageSource.getMessage("exception.tokenNotFound", null, Locale.getDefault()));
        return mav;
    }

    @ExceptionHandler({NoUserLoggedException.class})
    ModelAndView noUserLoggedException() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
        mav.addObject("description", messageSource.getMessage("exception.noUserLogged", null, Locale.getDefault()));
        return mav;
    }
}
