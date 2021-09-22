package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.MediaService;
import ar.edu.itba.paw.interfaces.StaffService;
import ar.edu.itba.paw.models.PageContainer;
import ar.edu.itba.paw.models.media.Media;
import ar.edu.itba.paw.models.staff.RoleType;
import ar.edu.itba.paw.models.staff.StaffMember;
import ar.edu.itba.paw.webapp.exceptions.StaffNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StaffMemberController {
    @Autowired
    private MediaService mediaService;
    @Autowired
    private StaffService staffService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StaffMemberController.class);
    private static final int itemsPerPage = 4;

    @RequestMapping("/staff/{staffMemberId}")
    public ModelAndView staffMemberProfile(@PathVariable("staffMemberId") final int staffMemberId,
                                           @RequestParam(value = "page", defaultValue = "1") final int page){
        final ModelAndView mav = new ModelAndView("staffMemberProfile");
        final StaffMember member = staffService.getById(staffMemberId).orElseThrow(StaffNotFoundException::new);
        final PageContainer<Media> media = staffService.getMedia(staffMemberId, page-1, itemsPerPage);
        mav.addObject("member", member);
        mav.addObject("mediaContainer", media);
        Map<String,Integer> map = new HashMap<>();
        map.put("staffMemberId",staffMemberId);
        String urlBase = UriComponentsBuilder.newInstance().path("/staff/{staffMemberId}").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }
    @RequestMapping("/staff/{staffMemberId}/{roleType}")
    public ModelAndView memberSeries(@PathVariable("staffMemberId") final int staffMemberId,
                                     @PathVariable("roleType") final String roleType,
                                     @RequestParam(value = "page", defaultValue = "1") final int page){

        final ModelAndView mav = new ModelAndView("staffMemberProfile");
        final StaffMember member = staffService.getById(staffMemberId).orElseThrow(StaffNotFoundException::new);
        final RoleType normalizerRole = RoleType.valueOf(roleType.toUpperCase());
        final PageContainer<Media> media = staffService.
                getMediaByRoleType(staffMemberId, page-1, itemsPerPage, normalizerRole.ordinal());
        mav.addObject("roleType", roleType);
        mav.addObject("member", member);
        mav.addObject("mediaContainer", media);
        Map<String,String> map = new HashMap<>();
        map.put("staffMemberId",Integer.toString(staffMemberId));
        map.put("roleType", normalizerRole.getRoleType());
        String urlBase = UriComponentsBuilder.newInstance().path("/staff/{staffMemberId}/{roleType}").buildAndExpand(map).toUriString();
        mav.addObject("urlBase", urlBase);
        return mav;
    }

}
