//package ar.edu.itba.paw.webapp.controller;
//
//import ar.edu.itba.paw.webapp.exceptions.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.authentication.LockedException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.text.ParseException;
//import java.util.Locale;
//
//@ControllerAdvice
//public class ExceptionHandlingController {
//
//    @Autowired
//    private MessageSource messageSource;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);
//
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler({
//            DataIntegrityViolationException.class,
//            GenreNotFoundException.class,
//            ListNotFoundException.class,
//            MediaNotFoundException.class,
//            StaffNotFoundException.class,
//            StudioNotFoundException.class,
//            UserNotFoundException.class,
//            ImageNotFoundException.class,
//            CommentNotFoundException.class,
//            ReportNotFoundException.class,
//            RequestNotFoundException.class,
//            IllegalArgumentException.class})
//    public ModelAndView notFoundException() {
//        ModelAndView mav = new ModelAndView("errors/error");
//        mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
//        mav.addObject("description", messageSource.getMessage("exception.notFound", null, Locale.getDefault()));
//        return mav;
//    }
//
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler({TokenNotFoundException.class})
//    public ModelAndView tokenNotFoundException() {
//        ModelAndView mav = new ModelAndView("errors/error");
//        mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
//        mav.addObject("description", messageSource.getMessage("exception.tokenNotFound", null, Locale.getDefault()));
//        return mav;
//    }
//
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler({NoUserLoggedException.class})
//    public ModelAndView noUserLoggedException() {
//        ModelAndView mav = new ModelAndView("errors/error");
//        mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
//        mav.addObject("description", messageSource.getMessage("exception.noUserLogged", null, Locale.getDefault()));
//        return mav;
//    }
//
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler({UnregisteredUserException.class, BadCredentialsException.class})
//    public ModelAndView unregisteredUserException() {
//        LOGGER.info("Handling UnregisteredUserException");
//        return new ModelAndView("login/login").addObject("error", messageSource.getMessage("login.incorrect", null, Locale.getDefault()));
//    }
//
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler({DisabledException.class})
//    public ModelAndView disabledUserException() {
//        LOGGER.info("Handling DisabledUserException");
//        return new ModelAndView("login/login").addObject("error", messageSource.getMessage("login.disabled", null, Locale.getDefault()));
//    }
//
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler({LockedException.class})
//    public ModelAndView lockedUserException() {
//        LOGGER.info("Handling LockedUserException");
//        return new ModelAndView("login/login").addObject("error", messageSource.getMessage("login.locked", null, Locale.getDefault()));
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler({InternalAuthenticationServiceException.class})
//    public ModelAndView internalAuthenticationServiceException() {
//        LOGGER.info("Handling InternalAuthenticationServiceException");
//        return new ModelAndView("login/login").addObject("error", messageSource.getMessage("login.internalError", null, Locale.getDefault()));
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler({ParseException.class})
//    public ModelAndView internalException() {
//        LOGGER.info("Handling ParseException");
//        ModelAndView mav = new ModelAndView("errors/error");
//        mav.addObject("title", messageSource.getMessage("exception", null, Locale.getDefault()));
//        mav.addObject("description", messageSource.getMessage("exception.internalException", null, Locale.getDefault()));
//        return mav;
//    }
//}
