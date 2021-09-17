package ar.edu.itba.paw.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class ErrorController {
    @Autowired
    MessageSource messageSource;

    @RequestMapping("/403")
    public ModelAndView forbidden() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("title", messageSource.getMessage("error.403", null, Locale.getDefault()));
        mav.addObject("description", messageSource.getMessage("error.403.description", null, Locale.getDefault()));
        return mav;
    }
}
