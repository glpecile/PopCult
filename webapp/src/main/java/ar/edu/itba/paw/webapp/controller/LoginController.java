//package ar.edu.itba.paw.webapp.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.WebAttributes;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//public class LoginController {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
//
//    @RequestMapping("/login")
//    public ModelAndView login() {
//        LOGGER.debug("Accessing login page.");
//        return new ModelAndView("login/login");
//    }
//
//    @RequestMapping("/loginFailed")
//    public void loginFailed(HttpServletRequest request) {
//        AuthenticationException authenticationException = (AuthenticationException) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//        LOGGER.warn("Someone failed to login.");
//        if (authenticationException != null) {
//            if(authenticationException.getCause() != null) {
//                throw (AuthenticationException) authenticationException.getCause();
//            } else {
//                throw authenticationException;
//            }
//        }
//    }
//}