package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.interfaces.StaffService;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.StaffMember;
import ar.edu.itba.paw.webapp.exceptions.StaffNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class StaffMemberController {
    @Autowired
    private MediaService mediaService;
    @Autowired
    private StaffService staffService;

    private static final int itemsPerPage = 4;

    @RequestMapping("/staff/{staffMemberId}")
    public ModelAndView staffMemberProfile(@PathVariable("staffMemberId") final int staffMemberId){
        final ModelAndView mav = new ModelAndView("staffMemberProfile");
        final StaffMember member = staffService.getById(staffMemberId).orElseThrow(StaffNotFoundException::new);
        final List<Integer> mediaIdsActing = staffService.getMediaByActor(staffMemberId,0,itemsPerPage);
        final List<Media> mediaActing = mediaService.getById(mediaIdsActing);
        final List<Integer> mediaIdsDirecting = staffService.getMediaByDirector(staffMemberId,0,itemsPerPage);
        final List<Media> mediaDirecting = mediaService.getById(mediaIdsDirecting);
        mav.addObject("member", member);
        mav.addObject("mediaActing", mediaActing);
        mav.addObject("mediaDirecting", mediaDirecting);
        return mav;
    }

}
