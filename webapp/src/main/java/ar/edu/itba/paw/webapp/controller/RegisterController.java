//package ar.edu.itba.paw.webapp.controller;
//
//import ar.edu.itba.paw.interfaces.UserService;
//import ar.edu.itba.paw.interfaces.TokenService;
//import ar.edu.itba.paw.interfaces.exceptions.InvalidTokenException;
//import ar.edu.itba.paw.models.user.Token;
//import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
//import ar.edu.itba.paw.interfaces.exceptions.UsernameAlreadyExistsException;
//import ar.edu.itba.paw.webapp.exceptions.TokenNotFoundException;
//import ar.edu.itba.paw.webapp.form.UserForm;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.validation.Valid;
//@Controller
//public class RegisterController {
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private TokenService tokenService;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);
//    @RequestMapping(value = "/register", method = {RequestMethod.GET})
//    public ModelAndView registerForm(@ModelAttribute("registerForm") final UserForm form) {
//        return new ModelAndView("login/registerForm");
//    }
//
//    @RequestMapping(value = "/register", method = {RequestMethod.POST})
//    public ModelAndView register(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
//        if (errors.hasErrors()) {
//            LOGGER.warn(" /register: User sent a register form with errors.");
//            return registerForm(form);
//        }
//        try {
//            userService.register(form.getEmail(),
//                    form.getUsername(),
//                    form.getPassword(),
//                    form.getName());
//            LOGGER.info("/register: User {} registered.", form.getUsername());
//        } catch (UsernameAlreadyExistsException e) {
//            errors.rejectValue("username", "validation.username.alreadyExists");
//            return registerForm(form);
//        } catch (EmailAlreadyExistsException e) {
//            errors.rejectValue("email", "validation.email.alreadyExists");
//            return registerForm(form);
//        }
//
//        return new ModelAndView("login/sentEmail");
//    }
//
//    @RequestMapping(value = "/register/confirm")
//    public ModelAndView confirmRegistration(@RequestParam("token") final String token) throws InvalidTokenException {
//        Token verificationToken = tokenService.getToken(token).orElseThrow(TokenNotFoundException::new);
//
//        if (userService.confirmRegister(verificationToken)) {
//            LOGGER.info("Register process completed.");
//            return new ModelAndView("redirect:/");
//
//        }
//        return new ModelAndView("redirect:/tokenTimedOut?token=" + token);
//    }
//}
