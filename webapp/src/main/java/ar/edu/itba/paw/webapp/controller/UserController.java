package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/profile")
    public ModelAndView userProfile() {
        ModelAndView mav = new ModelAndView("userProfile");
        User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        mav.addObject(user);
        return mav;
    }

}
