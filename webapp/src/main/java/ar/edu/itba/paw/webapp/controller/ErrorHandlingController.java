package ar.edu.itba.paw.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class ErrorHandlingController {
    @Autowired
    MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandlingController.class);

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @RequestMapping("/403")
    public ModelAndView forbidden() {
        LOGGER.info("Error 403 found. User redirect to /403.");
        ModelAndView mav = new ModelAndView("errors/error");
        mav.addObject("title", messageSource.getMessage("error.403", null, Locale.getDefault()));
        mav.addObject("description", messageSource.getMessage("error.403.description", null, Locale.getDefault()));
        return mav;
    }
}
