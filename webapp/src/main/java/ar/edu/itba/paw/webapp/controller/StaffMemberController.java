package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.interfaces.StaffService;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.RoleType;
import ar.edu.itba.paw.models.staff.StaffMember;
import ar.edu.itba.paw.webapp.exceptions.StaffNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView staffMemberProfile(@PathVariable("staffMemberId") final int staffMemberId,
                                           @RequestParam(value = "page", defaultValue = "1") final int page){
        final ModelAndView mav = new ModelAndView("staffMemberProfile");
        final StaffMember member = staffService.getById(staffMemberId).orElseThrow(StaffNotFoundException::new);
        final List<Integer> mediaIds = staffService.getMedia(staffMemberId, page-1, itemsPerPage);
        final List<Media> media = mediaService.getById(mediaIds);
        final int mediaCount = staffService.getMediaCount(staffMemberId).orElse(0);
        mav.addObject("member", member);
        mav.addObject("media", media);
        mav.addObject("currentPage", page);
        mav.addObject("mediaPages",mediaCount/itemsPerPage + 1);
        mav.addObject("urlBase", "/staff/"+staffMemberId+"/");
        return mav;
    }
    @RequestMapping("/staff/{staffMemberId}/{roleType}")
    public ModelAndView memberSeries(@PathVariable("staffMemberId") final int staffMemberId,
                                     @PathVariable("roleType") final String roleType,
                                     @RequestParam(value = "page", defaultValue = "1") final int page){

        final ModelAndView mav = new ModelAndView("staffMemberProfile");
        final StaffMember member = staffService.getById(staffMemberId).orElseThrow(StaffNotFoundException::new);
        final RoleType normalizerRole = RoleType.valueOf(roleType.toUpperCase());
        final List<Integer> mediaIdsJob = staffService.getMediaByRoleType(staffMemberId, page-1, itemsPerPage, normalizerRole.ordinal());
        final List<Media> media = mediaService.getById(mediaIdsJob);
        final int mediaCount = staffService.getMediaCountByRoleType(staffMemberId,normalizerRole.ordinal()).orElse(0);
        mav.addObject("roleType", roleType);
        mav.addObject("member", member);
        mav.addObject("media", media);
        mav.addObject("mediaPages", mediaCount/itemsPerPage + 1);
        mav.addObject("currentPage", page);
        mav.addObject("urlBase", "/staff/"+staffMemberId+"/"+normalizerRole.getRoleType()+"/");
        return mav;
    }

}
