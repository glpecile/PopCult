package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.TokenService;
import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TokenController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @RequestMapping(value = "/tokenTimedOut")
    public ModelAndView tokenTimedOut(@RequestParam("token") final String token) {
        ModelAndView mav = new ModelAndView("tokenTimedOut");
        mav.addObject("token", token);
        return mav;
    }

    @RequestMapping(value = "/resendToken")
    public ModelAndView resendToken(@RequestParam("token") final String token) {
        ModelAndView mav = new ModelAndView("sentEmail");
        userService.resendToken(token);
        return  mav;
    }
}
