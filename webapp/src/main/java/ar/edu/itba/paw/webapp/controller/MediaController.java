package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @RequestMapping("/")
    public ModelAndView mainPage() {
        final ModelAndView mav = new ModelAndView("index");
        //TODO get media list
        return mav;
    }
}
