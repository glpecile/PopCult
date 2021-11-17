package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.TokenService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.webapp.exceptions.TokenNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TokenController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenController.class);

    @RequestMapping(value = "/tokenTimedOut")
    public ModelAndView tokenTimedOut(@RequestParam("token") final String token) {
        LOGGER.warn("Token timed out.");
        ModelAndView mav = new ModelAndView("login/tokenTimedOut");
        mav.addObject("token", token);
        return mav;
    }

    @RequestMapping(value = "/resendToken")
    public ModelAndView resendToken(@RequestParam("token") final String token) {
        ModelAndView mav = new ModelAndView("login/sentEmail");
        Token tkn = tokenService.getToken(token).orElseThrow(TokenNotFoundException::new);
        userService.resendToken(tkn);
        LOGGER.info("Token was resent");
        return  mav;
    }
}
