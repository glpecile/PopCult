package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.interfaces.TokenService;
import ar.edu.itba.paw.models.user.Token;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UsernameAlreadyExistsException;
import ar.edu.itba.paw.webapp.exceptions.TokenNotFoundException;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public ModelAndView registerForm(@ModelAttribute("registerForm") final UserForm form) {
        return new ModelAndView("registerForm");
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public ModelAndView register(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return registerForm(form);
        }
        try {
            userService.register(form.getEmail(),
                    form.getUsername(),
                    form.getPassword(),
                    form.getName());
        } catch (UsernameAlreadyExistsException e) {
            errors.rejectValue("username", "validation.username.alreadyExists");
            return registerForm(form);
        } catch (EmailAlreadyExistsException e) {
            errors.rejectValue("email", "validation.email.alreadyExists");
            return registerForm(form);
        }

        return new ModelAndView("sentEmail");
    }

    @RequestMapping(value = "/register/confirm")
    public ModelAndView confirmRegistration(@RequestParam("token") final String token) {
        Token verificationToken = tokenService.getToken(token).orElseThrow(TokenNotFoundException::new);
        //TODO
        if (userService.confirmRegister(verificationToken)) {
            return new ModelAndView("login").addObject("successfulConfirmation", true);

        }
        return new ModelAndView("redirect:/register/tokentimedout?token=" + token);
    }

    @RequestMapping(value = "/register/tokentimedout")
    public ModelAndView tokenTimedOut(@RequestParam("token") final String token) {
        ModelAndView mav = new ModelAndView("tokenTimedOut");
        mav.addObject("token", token);
        return mav;
    }

    @RequestMapping(value = "/register/resendemail")
    public ModelAndView resendEmail(@RequestParam("token") final String token) {
        ModelAndView mav = new ModelAndView("sentEmail");
        userService.resendVerificationEmail(token);
        return mav;
    }
}
